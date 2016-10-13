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
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.source.ProductDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductInCategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class ProductLocalDataSource implements ProductDataSource {

    private static ProductLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private ProductLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static ProductLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new ProductLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    @Override
    public void getXProductsInCategory(@NonNull String categoryId, @NonNull String nutritionGradeValue, @NonNull Integer skipProducts, @NonNull GetXProductsInCategoryCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(nutritionGradeValue);
        checkNotNull(skipProducts);
        checkNotNull(callback);
    }

    //TODO DELETE
    @Override
    public void getProducts(@NonNull String categoryId, @NonNull GetProductsCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(callback);
        int nbExpected;
        int nbInDatabase;

        Uri uri = CategoryPersistenceContract.CategoryEntry.buildCategoryUriWith(categoryId);

        Cursor cursor = mContentResolver.query(uri, null, null, null, null);

        if (cursor.moveToLast()) {
            nbExpected = cursor.getInt(cursor.getColumnIndex(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_SUM_OF_PRODUCTS));
        } else {
            callback.onErrorCategoryNotAvailable();
            return;
        }
        cursor.close();

        uri = ProductInCategoryPersistenceContract.ProductInCategoryEntry.buildProductInCategoryUri();

        cursor = mContentResolver.query(
                uri,
                null,
                ProductInCategoryPersistenceContract.ProductInCategoryEntry.COLUMN_NAME_CATEGORY_ID + " = ?",
                new String[]{categoryId},
                null);

        if (cursor.moveToLast()) {
            nbInDatabase = cursor.getCount();
        } else {
            callback.onDataNotAvailable();
            return;
        }
        cursor.close();

        if (nbExpected == nbInDatabase) {
            callback.onProductsLoaded();
        } else {
            callback.onDataNotAvailable();
        }
    }

    //TODO DELETE
    @Override
    public void getProduct(@NonNull String productId, @NonNull GetProductCallback callback) {
        checkNotNull(productId);
        checkNotNull(callback);

        Uri uri = ProductPersistenceContract.ProductEntry.buildProductUriWith(productId);

        Cursor cursor = mContentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToLast()) {
            callback.onProductLoaded();
        } else {
            callback.onDataNotAvailable();
        }
        cursor.close();
    }

}
