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

package com.fredericletellier.foodinspector.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class EventLocalDataSource implements EventDataSource {

    private static EventLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private EventLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static EventLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new EventLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    /**
     * Get the list of event with status No_Network
     * If exist, {@link GetEventsCallback#onEventsPendingNetwork(List)} is called
     */
    @Override
    public void getEvents(@Nullable List<Event> events, @NonNull GetEventsCallback callback){
        checkNotNull(callback);

        Uri uri = EventPersistenceContract.EventEntry.buildEventUri();

        Cursor cursor = mContentResolver.query(
                uri,
                null,
                EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS + " = ?",
                new String[]{Event.STATUS_NO_NETWORK},
                null);

        events = new ArrayList<Event>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                events.add(Event.from(cursor));
            } while (cursor.moveToNext());

            callback.onEventsPendingNetwork(events);
        }
        cursor.close();
    }

    /**
     * Check if product exist in local data source
     * If not exist, {@link AddEventCallback#onEventProductNotAvailable()} is called
     * If exist, update or create associated event
     */
    @Override
    public void addEvent(@NonNull String productId, @NonNull AddEventCallback callback){
        checkNotNull(productId);
        checkNotNull(callback);

        Uri uriProduct = ProductPersistenceContract.ProductEntry.buildProductUriWith(productId);

        Cursor cursorProduct = mContentResolver.query(
                uriProduct,
                null,
                null,
                null,
                null);

        if (cursorProduct == null || !cursorProduct.moveToFirst()) {
            cursorProduct.close();
            callback.onEventProductNotAvailable();
            return;
        }
        cursorProduct.close();

        Uri uriEvent = EventPersistenceContract.EventEntry.buildEventUri();

        Cursor cursorEvent = mContentResolver.query(
                uriEvent,
                null,
                EventPersistenceContract.EventEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                new String[]{productId},
                null);

        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();

        if (cursorEvent != null && cursorEvent.moveToFirst()) {
            Event event = Event.from(cursorEvent);

            ContentValues values = new ContentValues();
            values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_UNIX_TIMESTAMP, timestamp);

            mContentResolver.update(
                    EventPersistenceContract.EventEntry.buildEventUri(),
                    values,
                    EventPersistenceContract.EventEntry._ID + " LIKE ?",
                    new String[]{event.getId()});

        } else {

            ContentValues values = new ContentValues();
            values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_UNIX_TIMESTAMP, timestamp);
            values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS, Event.STATUS_OK);
            values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_PRODUCT_ID, productId);
            values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_FAVORITE, false);

            mContentResolver.insert(EventPersistenceContract.EventEntry.buildEventUri(), values);
        }
        cursorEvent.close();
    }

    /**
     * Check if event exist in local data source
     * If exist, update favorite field
     */
    @Override
    public void updateFavoriteFieldEvent(@NonNull String productId){
        checkNotNull(productId);

        Uri uri = EventPersistenceContract.EventEntry.buildEventUri();

        Cursor cursor = mContentResolver.query(
                uri,
                null,
                EventPersistenceContract.EventEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                new String[]{productId},
                null);

        if (cursor != null && cursor.moveToFirst()) {

            Event event = Event.from(cursor);

            ContentValues values = new ContentValues();
            values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_FAVORITE, event.getFavorite() ? 0 : 1);

            mContentResolver.update(
                    EventPersistenceContract.EventEntry.buildEventUri(),
                    values,
                    EventPersistenceContract.EventEntry._ID + " LIKE ?",
                    new String[]{event.getId()});

        }
        cursor.close();
    }

}
