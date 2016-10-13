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

package com.fredericletellier.foodinspector.data;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Immutable model class for a Category.
 */
public final class Category {

    @NonNull
    private final String mId;

    @Nullable
    private final String mCountryId;

    @Nullable
    private final String mWorldCategoryId;

    @Nullable
    private final Boolean mHaveData;

    /**
     * Use this constructor to create a new Product.
     * @param id
     * @param countryId
     * @param worldCategoryId
     * @param haveData
     */
    public Category(String id, @Nullable String countryId, @Nullable String worldCategoryId,
                   @Nullable Boolean haveData) {
        mId = id;
        mCountryId = countryId;
        mWorldCategoryId = worldCategoryId;
        mHaveData = haveData;
    }

    /**
     * Use this constructor to return a Task from a Cursor
     *
     * @return
     */
    public static Category from(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndexOrThrow(
                CategoryPersistenceContract.CategoryEntry._ID));
        String countryId = cursor.getString(cursor.getColumnIndexOrThrow(
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_COUNTRY_ID));
        String worldCategoryId = cursor.getString(cursor.getColumnIndexOrThrow(
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_WORLD_CATEGORY_ID));
        Boolean haveData = cursor.getInt(cursor.getColumnIndexOrThrow(
                CategoryPersistenceContract.CategoryEntry.COLUMN_HAVE_DATA)) == 1;
        return new Category(id, countryId, worldCategoryId, haveData);
    }

    //TODO public static Category from(ContentValues values)


    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getCountry() {
        return mCountryId;
    }

    @Nullable
    public String getWorldId() {
        return mWorldCategoryId;
    }

    @Nullable
    public Boolean getHaveData() {
        return mHaveData;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mCountryId) &&
                Strings.isNullOrEmpty(mWorldCategoryId) &&
                (mHaveData == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equal(mId, category.mId) &&
                Objects.equal(mCountryId, category.mCountryId) &&
                Objects.equal(mWorldCategoryId, category.mWorldCategoryId) &&
                Objects.equal(mHaveData, category.mHaveData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mCountryId, mWorldCategoryId, mHaveData);
    }

    @Override
    public String toString() {
        return "Category with title " + getCountry() + getWorldId();
    }
}