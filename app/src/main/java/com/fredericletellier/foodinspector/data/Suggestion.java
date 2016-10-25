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

import com.fredericletellier.foodinspector.data.source.local.db.SuggestionPersistenceContract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Suggestion {

    private long mId;
    private String mBarcode;
    private String mCategoryKey;
    private String mCountryKey;

    /**
     * No args constructor for use in serialization
     *
     */
    public Suggestion() {
    }

    /**
     *
     * @param id
     * @param categoryKey
     * @param barcode
     * @param countryKey
     */
    public Suggestion(long id, String barcode, String categoryKey, String countryKey) {
        this.mId = id;
        this.mBarcode = barcode;
        this.mCategoryKey = categoryKey;
        this.mCountryKey = countryKey;
    }

    /**
     * Use this constructor to return a Suggestion from a Cursor
     *
     * @return
     */
    public static Suggestion from(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                SuggestionPersistenceContract.SuggestionEntry._ID));
        String barcode = cursor.getString(cursor.getColumnIndexOrThrow(
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_BARCODE));
        String categoryKey = cursor.getString(cursor.getColumnIndexOrThrow(
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_CATEGORY_KEY));
        String countryKey = cursor.getString(cursor.getColumnIndexOrThrow(
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_COUNTRY_KEY));
        return new Suggestion(id, barcode, categoryKey, countryKey);
    }

    /**
     * Use this constructor to return a Suggestion from ContentValues
     *
     * @return
     */
    public static Suggestion from(ContentValues values) {
        long id = values.getAsLong(SuggestionPersistenceContract.SuggestionEntry._ID);
        String barcode = values.getAsString(SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_BARCODE);
        String categoryKey = values.getAsString(SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_CATEGORY_KEY);
        String countryKey = values.getAsString(SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_COUNTRY_KEY);
        return new Suggestion(id, barcode, categoryKey, countryKey);
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

    public Suggestion withId(long id) {
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

    public Suggestion withBarcode(String barcode) {
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

    public Suggestion withCategoryKey(String categoryKey) {
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

    public Suggestion withCountryKey(String countryKey) {
        this.mCountryKey = countryKey;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mId).append(mBarcode).append(mCategoryKey).append(mCountryKey).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Suggestion) == false) {
            return false;
        }
        Suggestion rhs = ((Suggestion) other);
        return new EqualsBuilder().append(mId, rhs.mId).append(mBarcode, rhs.mBarcode).append(mCategoryKey, rhs.mCategoryKey).append(mCountryKey, rhs.mCountryKey).isEquals();
    }

}