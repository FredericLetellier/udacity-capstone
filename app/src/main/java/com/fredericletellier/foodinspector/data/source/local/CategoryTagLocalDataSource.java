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

import com.fredericletellier.foodinspector.data.CategoryTag;
import com.fredericletellier.foodinspector.data.source.CategoryTagDataSource;
import com.fredericletellier.foodinspector.data.source.CategoryTagValues;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryTagPersistenceContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CategoryTagLocalDataSource implements CategoryTagDataSource {

    private static CategoryTagLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private CategoryTagLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static CategoryTagLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryTagLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getCategoryTags(@NonNull String barcode,
                                @NonNull GetCategoryTagsCallback getCategoryTagsCallback) {

        Cursor cursor = mContentResolver.query(
                CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUri(),
                null,
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode},
                null);

        if (cursor.moveToFirst()) {

            List<String> categoriesKey = new ArrayList<String>();
            do {
                CategoryTag categoryTag = CategoryTag.from(cursor);
                categoriesKey.add(categoryTag.getCategoryKey());
            } while (cursor.moveToNext());

            getCategoryTagsCallback.onCategoryTagsLoaded(categoriesKey);
        } else {
            getCategoryTagsCallback.onCategoryTagsNotExist();
        }
        cursor.close();
    }

    @Override
    public void checkExistCategoryTag(@NonNull String barcode, @NonNull String categoryKey,
                                      @NonNull CheckExistCategoryTagCallback checkExistCategoryTagCallback) {

        Cursor cursor = mContentResolver.query(
                CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUri(),
                null,
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE + " = ? AND " +
                        CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_CATEGORY_KEY + " = ?",
                new String[]{barcode, categoryKey},
                null);

        if (cursor.moveToLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(CategoryTagPersistenceContract.CategoryTagEntry._ID));
            checkExistCategoryTagCallback.onCategoryTagExisted(_id);
        } else {
            checkExistCategoryTagCallback.onCategoryTagNotExisted();
        }
        cursor.close();
    }

    @Override
    public void addCategoryTag(@NonNull CategoryTag categoryTag, @NonNull AddCategoryTagCallback addCategoryTagCallback) {
        checkNotNull(categoryTag);

        ContentValues values = CategoryTagValues.from(categoryTag);
        Uri uri = mContentResolver.insert(CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUri(), values);

        if (uri != null) {
            addCategoryTagCallback.onCategoryTagAdded();
        } else {
            addCategoryTagCallback.onError();
        }
    }

    @Override
    public void updateCategoryTag(@NonNull CategoryTag categoryTag, @NonNull UpdateCategoryTagCallback updateCategoryTagCallback) {
        checkNotNull(categoryTag);

        ContentValues values = CategoryTagValues.from(categoryTag);

        String selection = CategoryTagPersistenceContract.CategoryTagEntry._ID + " LIKE ?";
        String[] selectionArgs = {categoryTag.getAsStringId()};

        int rows = mContentResolver.update(CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUri(), values, selection, selectionArgs);

        if (rows != 0) {
            updateCategoryTagCallback.onCategoryTagUpdated();
        } else {
            updateCategoryTagCallback.onError();
        }
    }

    @Override
    public void saveCategoryTag(@NonNull final CategoryTag categoryTag, @NonNull final SaveCategoryTagCallback saveCategoryTagCallback) {
        checkNotNull(categoryTag);

        String barcode = categoryTag.getBarcode();
        String categoryKey = categoryTag.getCategoryKey();

        checkExistCategoryTag(barcode, categoryKey, new CheckExistCategoryTagCallback() {

            @Override
            public void onCategoryTagExisted(long id) {
                categoryTag.setId(id);
                updateCategoryTag(categoryTag, new UpdateCategoryTagCallback() {
                    @Override
                    public void onCategoryTagUpdated() {
                        saveCategoryTagCallback.onCategoryTagSaved();
                    }

                    @Override
                    public void onError() {
                        saveCategoryTagCallback.onError();
                    }
                });
            }

            @Override
            public void onCategoryTagNotExisted() {
                addCategoryTag(categoryTag, new AddCategoryTagCallback() {
                    @Override
                    public void onCategoryTagAdded() {
                        saveCategoryTagCallback.onCategoryTagSaved();
                    }

                    @Override
                    public void onError() {
                        saveCategoryTagCallback.onError();
                    }
                });
            }
        });
    }
}
