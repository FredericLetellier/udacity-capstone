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

import com.fredericletellier.foodinspector.data.source.local.db.CategoryTagPersistenceContract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CategoryTag {

    private long mId;
    private String mBarcode;
    private String mCategoryKey;
    private int mRank;

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
        this.mId = id;
        this.mBarcode = barcode;
        this.mCategoryKey = categoryKey;
        this.mRank = rank;
    }

    /**
     * Use this constructor to return a CategoryTag from a Cursor
     *
     * @return
     */
    public static CategoryTag from(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                CategoryTagPersistenceContract.CategoryTagEntry._ID));
        String barcode = cursor.getString(cursor.getColumnIndexOrThrow(
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE));
        String categoryKey = cursor.getString(cursor.getColumnIndexOrThrow(
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_CATEGORY_KEY));
        int rank = cursor.getInt(cursor.getColumnIndexOrThrow(
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_RANK));
        return new CategoryTag(id, barcode, categoryKey, rank);
    }

    /**
     * Use this constructor to return a CategoryTag from ContentValues
     *
     * @return
     */
    public static CategoryTag from(ContentValues values) {
        long id = values.getAsLong(CategoryTagPersistenceContract.CategoryTagEntry._ID);
        String barcode = values.getAsString(CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE);
        String categoryKey = values.getAsString(CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_CATEGORY_KEY);
        int rank = values.getAsInteger(CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_RANK);
        return new CategoryTag(id, barcode, categoryKey, rank);
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

    public CategoryTag withId(long id) {
        this.mId = id;
        return this;
    }

    /**
     *
     * @return
     * The barcode
     */
    public String getBarcode() {
        return mBarcode;
    }

    /**
     *
     * @param barcode
     * The barcode
     */
    public void setBarcode(String barcode) {
        this.mBarcode = barcode;
    }

    public CategoryTag withBarcode(String barcode) {
        this.mBarcode = barcode;
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

    public CategoryTag withCategoryKey(String categoryKey) {
        this.mCategoryKey = categoryKey;
        return this;
    }

    /**
     *
     * @return
     * The rank
     */
    public long getRank() {
        return mRank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    public void setRank(int rank) {
        this.mRank = rank;
    }

    public CategoryTag withRank(int rank) {
        this.mRank = rank;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mId).append(mBarcode).append(mCategoryKey).append(mRank).toHashCode();
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
        return new EqualsBuilder().append(mId, rhs.mId).append(mBarcode, rhs.mBarcode).append(mCategoryKey, rhs.mCategoryKey).append(mRank, rhs.mRank).isEquals();
    }

}
