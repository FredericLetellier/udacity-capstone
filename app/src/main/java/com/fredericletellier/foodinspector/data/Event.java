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
 * Immutable model class for a Event.
 */
public final class Event {

    @NonNull
    private final String mId;

    @Nullable
    private final Long mUnixTimestamp;

    @Nullable
    private final String mBarcode;

    @Nullable
    private final String mProductId;

    @Nullable
    private final Boolean mFavorite;

    /**
     * Use this constructor to create a new Product.
     * @param id
     * @param unixTimestamp
     * @param barcode
     * @param productId
     * @param favorite
     */
    public Event(String id, @Nullable Long unixTimestamp, @Nullable String barcode,
                    @Nullable String productId, @Nullable Boolean favorite) {
        mId = id;
        mUnixTimestamp = unixTimestamp;
        mBarcode = barcode;
        mProductId = productId;
        mFavorite = favorite;
    }

    /**
     * Use this constructor to return a Task from a Cursor
     *
     * @return
     */

    //TODO public static Event from(Cursor cursor)

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
    public String getBarcode() {
        return mBarcode;
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
        return mUnixTimestamp + " - " + mBarcode + " - " + mProductId;
    }

    public boolean isEmpty() {
        return (mUnixTimestamp == null) &&
                Strings.isNullOrEmpty(mBarcode) &&
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
                Objects.equal(mBarcode, event.mBarcode) &&
                Objects.equal(mProductId, event.mProductId) &&
                Objects.equal(mFavorite, event.mFavorite);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mUnixTimestamp, mBarcode, mProductId, mFavorite);
    }

    @Override
    public String toString() {
        return "Event with title " + getTitle();
    }
}