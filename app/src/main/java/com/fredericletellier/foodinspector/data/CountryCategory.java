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

import android.content.ContentValues;
import android.database.Cursor;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CountryCategory {

    private long mId;
    private String mCategoryKey;
    private String mCountryKey;
    private int mSumOfProducts;

    /**
     * No args constructor for use in serialization
     *
     */
    public CountryCategory() {
    }

    /**
     *
     * @param categoryKey
     * @param sumOfProducts
     * @param countryKey
     */
    public CountryCategory(String categoryKey, String countryKey, int sumOfProducts) {
        this.mCategoryKey = categoryKey;
        this.mCountryKey = countryKey;
        this.mSumOfProducts = sumOfProducts;
    }

    /**
     *
     * @param id
     * @param categoryKey
     * @param sumOfProducts
     * @param countryKey
     */
    public CountryCategory(long id, String categoryKey, String countryKey, int sumOfProducts) {
        this.mId = id;
        this.mCategoryKey = categoryKey;
        this.mCountryKey = countryKey;
        this.mSumOfProducts = sumOfProducts;
    }

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return mId;
    }

    /**
     *
     * @return
     * The id
     */
    public String getAsStringId() {
        return String.valueOf(mId);
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.mId = id;
    }

    public CountryCategory withId(long id) {
        this.mId = id;
        return this;
    }

    /**
     *
     * @return
     * The categoryKey
     */
    public String getCategoryKey() {
        return mCategoryKey;
    }

    /**
     *
     * @param categoryKey
     * The category-key
     */
    public void setCategoryKey(String categoryKey) {
        this.mCategoryKey = categoryKey;
    }

    public CountryCategory withCategoryKey(String categoryKey) {
        this.mCategoryKey = categoryKey;
        return this;
    }

    /**
     *
     * @return
     * The countryKey
     */
    public String getCountryKey() {
        return mCountryKey;
    }

    /**
     *
     * @param countryKey
     * The country-key
     */
    public void setCountryKey(String countryKey) {
        this.mCountryKey = countryKey;
    }

    public CountryCategory withCountryKey(String countryKey) {
        this.mCountryKey = countryKey;
        return this;
    }

    /**
     *
     * @return
     * The sumOfProducts
     */
    public long getSumOfProducts() {
        return mSumOfProducts;
    }

    /**
     *
     * @param sumOfProducts
     * The sum-of-products
     */
    public void setSumOfProducts(int sumOfProducts) {
        this.mSumOfProducts = sumOfProducts;
    }

    public CountryCategory withSumOfProducts(int sumOfProducts) {
        this.mSumOfProducts = sumOfProducts;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mId).append(mCategoryKey).append(mCountryKey).append(mSumOfProducts).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CountryCategory) == false) {
            return false;
        }
        CountryCategory rhs = ((CountryCategory) other);
        return new EqualsBuilder().append(mId, rhs.mId).append(mCategoryKey, rhs.mCategoryKey).append(mCountryKey, rhs.mCountryKey).append(mSumOfProducts, rhs.mSumOfProducts).isEquals();
    }

}