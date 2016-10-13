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

import com.fredericletellier.foodinspector.data.source.CategoryDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;

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

    //TODO COMPLETE
    @Override
    public void getCategories(@NonNull String productId, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);
    }


    //TODO DELETE
    @Override
    public void getCategories(@NonNull String productId, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);

        String categoriesConcatened;

        Uri uri = ProductPersistenceContract.ProductEntry.buildProductUriWith(productId);

        Cursor cursor = mContentResolver.query(
                uri,
                null,
                ProductPersistenceContract.ProductEntry._ID + " = ?",
                new String[]{productId},
                null);

        if (cursor.moveToLast()) {
            categoriesConcatened = cursor.getString(cursor.getColumnIndex(ProductPersistenceContract.ProductEntry.COLUMN_NAME_CATEGORIES));
        } else {
            callback.onErrorProductNotAvailable();
            return;
        }
        cursor.close();

        uri = CategoryPersistenceContract.CategoryEntry.buildCategoryUri();
        String[] categories = categoriesConcatened.split(";");
        for (String category : categories)
        {
            cursor = mContentResolver.query(
                    uri,
                    null,
                    CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_WORLD_ID + " = ? AND" +
                            CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_COUNTRY_ID + " = ?",
                    new String[]{category, countryCode},
                    null);

            if (!cursor.moveToLast()) {
                callback.onDataNotAvailable();
                return;
            }
            cursor.close();
        }
        callback.onCategoriesLoaded();
    }

}
