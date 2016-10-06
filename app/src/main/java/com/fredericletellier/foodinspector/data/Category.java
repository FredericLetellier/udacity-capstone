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
    private final String mCountryId;

    @Nullable
    private final String mName;

    @Nullable
    private final Integer mSumOfProducts;

    @Nullable
    private final String mUrl;

    /**
     * Use this constructor to create a new Product.
     * @param id
     * @param country
     * @param worldId
     * @param countryId
     * @param name
     * @param sumOfProducts
     * @param url
     */
    public Category(String id, @Nullable String country, @Nullable String worldId,
                   @Nullable String countryId, @Nullable String name,
                   @Nullable Integer sumOfProducts, @Nullable String url) {
        mId = id;
        mCountry = country;
        mWorldId = worldId;
        mCountryId = countryId;
        mName = name;
        mSumOfProducts = sumOfProducts;
        mUrl = url;
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
    public String getCountryId() {
        return mCountryId;
    }

    @Nullable
    public String getName() {
        return mName;
    }

    @Nullable
    public Integer getSumOfProducts() {
        return mSumOfProducts;
    }

    @Nullable
    public String getUrl() {
        return mUrl;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mCountry) &&
                Strings.isNullOrEmpty(mWorldId) &&
                Strings.isNullOrEmpty(mCountryId) &&
                Strings.isNullOrEmpty(mName) &&
                (mSumOfProducts == null) &&
                Strings.isNullOrEmpty(mUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equal(mId, category.mId) &&
                Objects.equal(mCountry, category.mCountry) &&
                Objects.equal(mWorldId, category.mWorldId) &&
                Objects.equal(mCountryId, category.mCountryId) &&
                Objects.equal(mName, category.mName) &&
                Objects.equal(mSumOfProducts, category.mSumOfProducts) &&
                Objects.equal(mUrl, category.mUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mCountry, mWorldId, mCountryId, mName, mSumOfProducts, mUrl);
    }

    @Override
    public String toString() {
        return "Category with title " + getName();
    }
}