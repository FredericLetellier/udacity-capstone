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

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.source.CategoryDataSource;
import com.fredericletellier.foodinspector.data.source.CategoryValues;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;

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

    @Override
    public void getCategoryId(@NonNull String categoryKey, @NonNull CategoryDataSource.GetCategoryIdCallback getCategoryIdCallback) {
        Cursor cursor = mContentResolver.query(
                CategoryPersistenceContract.CategoryEntry.buildCategoryUri(),
                new String[]{CategoryPersistenceContract.CategoryEntry._ID},
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_CATEGORY_KEY + " = ?",
                new String[]{categoryKey},
                null);

        if (cursor.moveToLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(CategoryPersistenceContract.CategoryEntry._ID));
            getCategoryIdCallback.onCategoryIdLoaded(_id);
        } else {
            getCategoryIdCallback.onCategoryNotExist();
        }
        cursor.close();
    }

    @Override
    public void addCategory(@NonNull Category Category, @NonNull CategoryDataSource.AddCategoryCallback addCategoryCallback) {
        checkNotNull(Category);

        ContentValues values = CategoryValues.from(Category);
        Uri uri = mContentResolver.insert(CategoryPersistenceContract.CategoryEntry.buildCategoryUri(), values);

        if (uri != null) {
            addCategoryCallback.onCategoryAdded();
        } else {
            addCategoryCallback.onError();
        }
    }

    @Override
    public void updateCategory(@NonNull Category Category, @NonNull CategoryDataSource.UpdateCategoryCallback updateCategoryCallback) {
        checkNotNull(Category);

        ContentValues values = CategoryValues.from(Category);

        String selection = CategoryPersistenceContract.CategoryEntry._ID + " LIKE ?";
        String[] selectionArgs = {Category.getAsStringId()};

        int rows = mContentResolver.update(CategoryPersistenceContract.CategoryEntry.buildCategoryUri(), values, selection, selectionArgs);

        if (rows != 0) {
            updateCategoryCallback.onCategoryUpdated();
        } else {
            updateCategoryCallback.onError();
        }
    }

    @Override
    public void saveCategory(@NonNull final Category Category, @NonNull final CategoryDataSource.SaveCategoryCallback saveCategoryCallback) {
        checkNotNull(Category);

        String categoryKey = Category.getCategoryKey();

        getCategoryId(categoryKey, new CategoryDataSource.GetCategoryIdCallback() {

            @Override
            public void onCategoryIdLoaded(long id) {
                Category.setId(id);
                updateCategory(Category, new CategoryDataSource.UpdateCategoryCallback() {
                    @Override
                    public void onCategoryUpdated() {
                        saveCategoryCallback.onCategorySaved();
                    }

                    @Override
                    public void onError() {
                        saveCategoryCallback.onError();
                    }
                });
            }

            @Override
            public void onCategoryNotExist() {
                addCategory(Category, new CategoryDataSource.AddCategoryCallback() {
                    @Override
                    public void onCategoryAdded() {
                        saveCategoryCallback.onCategorySaved();
                    }

                    @Override
                    public void onError() {
                        saveCategoryCallback.onError();
                    }
                });
            }
        });
    }

    @Override
    public void getCategoryOfProduct(@NonNull String barcode, @NonNull GetCategoryOfProductCallback getCategoryOfProductCallback) {
        // TODO
    }
}
