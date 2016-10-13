/*
 *     Food Inspector - Choose well to eat better
 *     Copyright (C) 2016  Frédéric Letellier
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fredericletellier.foodinspector.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.source.CategoryDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.CategoriesInProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class CategoryLocalDataSource implements CategoryDataSource {

    private static CategoryLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private CategoryLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static CategoryLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    /**
     * Check the product exist
     * Check if categories of this product are parsed
     * If not, parse it
     * Get all categories associated with this product
     * Check if these categories exist in country categories
     * If not exist, {@link GetCategoriesCallback#onCategoriesNotAvailable(ArrayList)}
     * is called with a list of categories
     */
    @Override
    public void getCategories(@NonNull String productId, @Nullable ArrayList<String> categories, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);

        Uri uriProduct = ProductPersistenceContract.ProductEntry.buildProductUriWith(productId);

        //Search product
        Cursor cursorProduct = mContentResolver.query(
                uriProduct,
                null,
                null,
                null,
                null);

        if (cursorProduct == null || !cursorProduct.moveToFirst()) {
            cursorProduct.close();
            return;
        }

        Product product = Product.from(cursorProduct);
        cursorProduct.close();
        String parsableCategories = product.getParsableCategories();

        //If categories of product are not parsed, parse it
        if (!parsableCategories.equals(Product.PARSING_DONE)){
            String[] parsableCategoriesArray = parsableCategories.split(",");
            int i = 0;

            for (String parsableCategory : parsableCategoriesArray)
            {
                Uri uriCategoriesInProduct = CategoriesInProductPersistenceContract.CategoriesInProductEntry.buildProductsInCategoryUri();

                Cursor cursorCategoriesInProduct = mContentResolver.query(
                        uriCategoriesInProduct,
                        null,
                        CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_CATEGORY_ID + " = ? AND" +
                                CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                        new String[]{parsableCategory, product.getId()},
                        null);

                //If category not exist, insert in database
                if (!cursorCategoriesInProduct.moveToLast()) {
                    String categoryName = parsableCategory.replace('-',' ');
                    categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);

                    ContentValues values = new ContentValues();
                    values.put(CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_CATEGORY_ID, parsableCategory);
                    values.put(CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_PRODUCT_ID, product.getId());
                    values.put(CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_RANK, i);
                    values.put(CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_CATEGORY_NAME, categoryName);

                    mContentResolver.insert(CategoriesInProductPersistenceContract.CategoriesInProductEntry.buildProductsInCategoryUri(), values);
                }
                cursorCategoriesInProduct.close();
                i++;
            }

            //Mark product than its categories are parsed
            ContentValues values = new ContentValues();
            values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSABLE_CATEGORIES, Product.PARSING_DONE);

            mContentResolver.update(
                    ProductPersistenceContract.ProductEntry.buildProductUri(),
                    values,
                    ProductPersistenceContract.ProductEntry._ID + " LIKE ?",
                    new String[]{product.getId()});
        }

        //Search all categories associated with this product
        Uri uriCategoriesInProduct2 = CategoriesInProductPersistenceContract.CategoriesInProductEntry.buildProductsInCategoryUri();

        Cursor cursorCategoriesInProduct2 = mContentResolver.query(
                uriCategoriesInProduct2,
                null,
                CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                new String[]{product.getId()},
                null);

        categories = new ArrayList<String>();

        //For each categories find, search the associated country category
        if (cursorCategoriesInProduct2 != null && cursorCategoriesInProduct2.moveToFirst()) {
            do {
                String worldCategoryId = cursorCategoriesInProduct2.getString(
                        cursorCategoriesInProduct2.getColumnIndex(
                                CategoriesInProductPersistenceContract.CategoriesInProductEntry.COLUMN_NAME_CATEGORY_ID));

                Uri uriCategory = CategoryPersistenceContract.CategoryEntry.buildCategoryUri();

                Cursor cursorCategory = mContentResolver.query(
                        uriCategory,
                        null,
                        CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_WORLD_CATEGORY_ID + " = ?  AND " +
                        CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_COUNTRY_ID + " = ?",
                        new String[]{worldCategoryId, countryCode},
                        null);

                //If country category not exist, add to the list
                if (!cursorCategory.moveToLast()) {
                    categories.add(worldCategoryId);
                }
                cursorCategory.close();

            } while (cursorCategoriesInProduct2.moveToNext());
        }
        cursorCategoriesInProduct2.close();

        if (categories.size() > 0){
            callback.onCategoriesNotAvailable(categories);
        }

    }
}
