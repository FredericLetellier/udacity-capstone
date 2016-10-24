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
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.CategoryTag;
import com.fredericletellier.foodinspector.data.source.CategoryTagDataSource;
import com.fredericletellier.foodinspector.data.source.CategoryTagValues;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryTagPersistenceContract;

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
    public void getCategoryTag(@NonNull CategoryTag categoryTag, @NonNull GetCategoryTagCallback getCategoryTagCallback) {

    }

    @Override
    public void addCategoryTag(@NonNull CategoryTag categoryTag, @NonNull AddCategoryTagCallback addCategoryTagCallback) {
        checkNotNull(categoryTag);

        ContentValues values = CategoryTagValues.from(categoryTag);
        mContentResolver.insert(CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUri(), values);
    }

    @Override
    public void updateCategoryTag(@NonNull CategoryTag categoryTag, @NonNull UpdateCategoryTagCallback updateCategoryTagCallback) {
        checkNotNull(categoryTag);

        ContentValues values = CategoryTagValues.from(categoryTag);

        String selection = CategoryTagPersistenceContract.CategoryTagEntry._ID + " LIKE ?";
        String[] selectionArgs = {categoryTag.getAsStringId()};

        int rows = mContentResolver.update(CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUri(), values, selection, selectionArgs);

        checkNotNull(rows);
    }

    @Override
    public void saveCategoryTag(@NonNull CategoryTag categoryTag, @NonNull SaveCategoryTagCallback saveCategoryTagCallback) {

    }
}
