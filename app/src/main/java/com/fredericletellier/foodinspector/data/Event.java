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
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Random;

public class Event {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_NOT_A_PRODUCT = "BARCODE_NOT_DESCRIBE_A_PRODUCT";
    public static final String STATUS_NOT_IN_OFF_DATABASE = "PRODUCT_IS_NOT_IN_OPENFOODFACTS_DATABASE";
    public static final String STATUS_NO_NETWORK = "NO_NETWORK";

    @NonNull
    private long mId;
    private long mTimestamp;
    private String mBarcode;
    private String mStatus;

    /**
     *
     * @param status
     * @param barcode
     */
    public Event(String barcode, String status) {
        Long x = 1234567L;
        Long y = 23456789L;
        Random r = new Random();
        this.mId = x + ((long)(r.nextDouble()*(y-x)));
        this.mTimestamp = System.currentTimeMillis()/1000;
        this.mBarcode = barcode;
        this.mStatus = status;
    }

    /**
     *
     * @param timestamp
     * @param id
     * @param status
     * @param barcode
     */
    public Event(long id, long timestamp, String barcode, String status) {
        this.mId = id;
        this.mTimestamp = timestamp;
        this.mBarcode = barcode;
        this.mStatus = status;
    }

    /**
     * Use this constructor to return a Event from a Cursor
     *
     * @return
     */
    public static Event from(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry._ID));
        long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_TIMESTAMP));
        String barcode = cursor.getString(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE));
        String status = cursor.getString(cursor.getColumnIndexOrThrow(
                EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS));
        return new Event(id, timestamp, barcode, status);
    }

    /**
     * Use this constructor to return a Event from ContentValues
     *
     * @return
     */
    public static Event from(ContentValues values) {
        long id = values.getAsLong(EventPersistenceContract.EventEntry._ID);
        long timestamp = values.getAsLong(EventPersistenceContract.EventEntry.COLUMN_NAME_TIMESTAMP);
        String barcode = values.getAsString(EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE);
        String status = values.getAsString(EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS);
        return new Event(id, timestamp, barcode, status);
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
     * @param id
     * The id
     */
    public void setId(long id) {
        this.mId = id;
    }

    /**
     *
     * @return
     * The id
     */
    public String getAsStringId() {
        return String.valueOf(mId);
    }

    public Event withId(long id) {
        this.mId = id;
        return this;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public long getTimestamp() {
        return mTimestamp;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public Event withTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
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

    public Event withBarcode(String barcode) {
        this.mBarcode = barcode;
        return this;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return mStatus;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.mStatus = status;
    }

    public Event withStatus(String status) {
        this.mStatus = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mId).append(mTimestamp).append(mBarcode).append(mStatus).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Event)) {
            return false;
        }
        Event rhs = ((Event) other);
        return new EqualsBuilder().append(mId, rhs.mId).append(mTimestamp, rhs.mTimestamp).append(mBarcode, rhs.mBarcode).append(mStatus, rhs.mStatus).isEquals();
    }

}