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

public class CategoryTag {

    private long id;
    private String barcode;
    private String categoryKey;
    private int rank;

    /**
     * No args constructor for use in serialization
     *
     */
    public CategoryTag() {
    }

    /**
     *
     * @param id
     * @param rank
     * @param categoryKey
     * @param barcode
     */
    public CategoryTag(long id, String barcode, String categoryKey, int rank) {
        this.id = id;
        this.barcode = barcode;
        this.categoryKey = categoryKey;
        this.rank = rank;
    }


    //TODO public static CategoryTag from(Cursor cursor)

    //TODO public static CategoryTag from(ContentValues values)

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

    public CategoryTag withId(long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     *
     * @param barcode
     * The barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public CategoryTag withBarcode(String barcode) {
        this.barcode = barcode;
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

    public CategoryTag withCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
        return this;
    }

    /**
     *
     * @return
     * The rank
     */
    public long getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public CategoryTag withRank(int rank) {
        this.rank = rank;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(barcode).append(categoryKey).append(rank).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CategoryTag) == false) {
            return false;
        }
        CategoryTag rhs = ((CategoryTag) other);
        return new EqualsBuilder().append(id, rhs.id).append(barcode, rhs.barcode).append(categoryKey, rhs.categoryKey).append(rank, rhs.rank).isEquals();
    }

}