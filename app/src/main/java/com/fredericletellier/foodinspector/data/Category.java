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

public class Category {

    private long mId;
    private String mCategoryKey;
    private String mCategoryName;

    /**
     * No args constructor for use in serialization
     *
     */
    public Category() {
    }

    /**
     *
     * @param categoryName
     * @param categoryKey
     */
    public Category(String categoryKey, String categoryName) {
        this.mCategoryKey = categoryKey;
        this.mCategoryName = categoryName;
    }

    /**
     *
     * @param id
     * @param categoryName
     * @param categoryKey
     */
    public Category(long id, String categoryKey, String categoryName) {
        this.mId = id;
        this.mCategoryKey = categoryKey;
        this.mCategoryName = categoryName;
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

    public Category withId(long id) {
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

    public Category withCategoryKey(String categoryKey) {
        this.mCategoryKey = categoryKey;
        return this;
    }

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return mCategoryName;
    }

    /**
     *
     * @param categoryName
     * The category-name
     */
    public void setCategoryName(String categoryName) {
        this.mCategoryName = categoryName;
    }

    public Category withCategoryName(String categoryName) {
        this.mCategoryName = categoryName;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mId).append(mCategoryKey).append(mCategoryName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Category) == false) {
            return false;
        }
        Category rhs = ((Category) other);
        return new EqualsBuilder().append(mId, rhs.mId).append(mCategoryKey, rhs.mCategoryKey).append(mCategoryName, rhs.mCategoryName).isEquals();
    }

}