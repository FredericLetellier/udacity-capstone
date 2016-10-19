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

import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Immutable model class for a Event.
 */
public final class Event {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_NOT_A_PRODUCT = "BARCODE_NOT_DESCRIBE_A_PRODUCT";
    public static final String STATUS_NOT_IN_OFF_DATABASE = "PRODUCT_IS_NOT_IN_OPENFOODFACTS_DATABASE";
    public static final String STATUS_NO_NETWORK = "NO_NETWORK";

    @NonNull
    private final String mId;

    @Nullable
    private final Long mUnixTimestamp;

    @Nullable
    private final String mStatus;

    @Nullable
    private final String mProductId;

    @Nullable
    private final Boolean mFavorite;

    /**
     * Use this constructor to create a new Event.
     * @param id
     * @param unixTimestamp
     * @param status
     * @param productId
     * @param favorite
     */
    public Event(String id, @Nullable Long unixTimestamp, @Nullable String status,
                    @Nullable String productId, @Nullable Boolean favorite) {
        mId = id;
        mUnixTimestamp = unixTimestamp;
        mStatus = status;
        mProductId = productId;
        mFavorite = favorite;
    }

    /**
     * Use this constructor to return a Event from a Cursor
     *
     * @return
     */
    public static Event from(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry._ID));
        Long unixTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_UNIX_TIMESTAMP));
        String status = cursor.getString(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS));
        String productId = cursor.getString(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_PRODUCT_ID));
        Boolean favorite = cursor.getInt(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_FAVORITE)) == 1;
        return new Event(id, unixTimestamp, status, productId, favorite);
    }

    //TODO public static Event from(ContentValues values)


    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public Long getUnixTimestamp() {
        return mUnixTimestamp;
    }

    @Nullable
    public String getStatus() {
        return mStatus;
    }

    @Nullable
    public String getProductId() {
        return mProductId;
    }

    @Nullable
    public Boolean getFavorite() {
        return mFavorite;
    }

    @Nullable
    public String getTitle() {
        return mUnixTimestamp + " - " + mStatus + " - " + mProductId;
    }

    public boolean isEmpty() {
        return (mUnixTimestamp == null) &&
                Strings.isNullOrEmpty(mStatus) &&
                Strings.isNullOrEmpty(mProductId) &&
                (mFavorite == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equal(mId, event.mId) &&
                Objects.equal(mUnixTimestamp, event.mUnixTimestamp) &&
                Objects.equal(mStatus, event.mStatus) &&
                Objects.equal(mProductId, event.mProductId) &&
                Objects.equal(mFavorite, event.mFavorite);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mUnixTimestamp, mStatus, mProductId, mFavorite);
    }

    @Override
    public String toString() {
        return "Event with title " + getTitle();
    }
}