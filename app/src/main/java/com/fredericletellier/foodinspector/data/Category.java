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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Immutable model class for a Category.
 */
public final class Category {

    @NonNull
    private final String mId;

    @Nullable
    private final String mCountry;

    @Nullable
    private final String mWorldId;

    @Nullable
    private final Boolean mHaveData;

    /**
     * Use this constructor to create a new Product.
     * @param id
     * @param country
     * @param worldId
     * @param haveData
     */
    public Category(String id, @Nullable String country, @Nullable String worldId,
                   @Nullable Boolean haveData) {
        mId = id;
        mCountry = country;
        mWorldId = worldId;
        mHaveData = haveData;
    }

    /**
     * Use this constructor to return a Task from a Cursor
     *
     * @return
     */

    //TODO public static Category from(Cursor cursor)

    //TODO public static Category from(ContentValues values)


    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getCountry() {
        return mCountry;
    }

    @Nullable
    public String getWorldId() {
        return mWorldId;
    }

    @Nullable
    public Boolean getHaveData() {
        return mHaveData;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mCountry) &&
                Strings.isNullOrEmpty(mWorldId) &&
                (mHaveData == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equal(mId, category.mId) &&
                Objects.equal(mCountry, category.mCountry) &&
                Objects.equal(mWorldId, category.mWorldId) &&
                Objects.equal(mHaveData, category.mHaveData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mCountry, mWorldId, mHaveData);
    }

    @Override
    public String toString() {
        return "Category with title " + getCountry() + getWorldId();
    }
}