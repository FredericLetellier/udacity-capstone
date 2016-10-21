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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CountryCategory {

    private long id;
    private String categoryKey;
    private String countryKey;
    private int sumOfProducts;

    /**
     * No args constructor for use in serialization
     *
     */
    public CountryCategory() {
    }

    /**
     *
     * @param id
     * @param categoryKey
     * @param sumOfProducts
     * @param countryKey
     */
    public CountryCategory(long id, String categoryKey, String countryKey, int sumOfProducts) {
        this.id = id;
        this.categoryKey = categoryKey;
        this.countryKey = countryKey;
        this.sumOfProducts = sumOfProducts;
    }

    //TODO public static CountryCategory from(Cursor cursor)

    //TODO public static CountryCategory from(ContentValues values)

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    public CountryCategory withId(long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The categoryKey
     */
    public String getCategoryKey() {
        return categoryKey;
    }

    /**
     *
     * @param categoryKey
     * The category-key
     */
    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public CountryCategory withCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
        return this;
    }

    /**
     *
     * @return
     * The countryKey
     */
    public String getCountryKey() {
        return countryKey;
    }

    /**
     *
     * @param countryKey
     * The country-key
     */
    public void setCountryKey(String countryKey) {
        this.countryKey = countryKey;
    }

    public CountryCategory withCountryKey(String countryKey) {
        this.countryKey = countryKey;
        return this;
    }

    /**
     *
     * @return
     * The sumOfProducts
     */
    public long getSumOfProducts() {
        return sumOfProducts;
    }

    /**
     *
     * @param sumOfProducts
     * The sum-of-products
     */
    public void setSumOfProducts(int sumOfProducts) {
        this.sumOfProducts = sumOfProducts;
    }

    public CountryCategory withSumOfProducts(int sumOfProducts) {
        this.sumOfProducts = sumOfProducts;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(categoryKey).append(countryKey).append(sumOfProducts).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(categoryKey, rhs.categoryKey).append(countryKey, rhs.countryKey).append(sumOfProducts, rhs.sumOfProducts).isEquals();
    }

}