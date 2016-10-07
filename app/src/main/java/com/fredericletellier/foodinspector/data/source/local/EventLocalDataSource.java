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
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;

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

    @Override
    public void getEvents(@NonNull GetEventsCallback callback){
        // no-op the data is loaded via Cursor Loader
    }

    public void addEvent(@NonNull Event event, @NonNull AddEventCallback callback){
        checkNotNull(event);
        checkNotNull(callback);

        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();

        ContentValues values = new ContentValues();
        values.put(EventPersistenceContract.EventEntry._ID, event.getId());
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_UNIX_TIMESTAMP, timestamp);
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE, event.getBarcode());
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_PRODUCT_ID, event.getProductId());
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_FAVORITE, event.getFavorite() ? 1 : 0);

        mContentResolver.insert(EventPersistenceContract.EventEntry.buildEventUri(), values);
    }

    public void updateEvent(@NonNull String eventId, @NonNull UpdateEventCallback callback){
        checkNotNull(eventId);
        checkNotNull(callback);

        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();

        ContentValues values = new ContentValues();
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_UNIX_TIMESTAMP, timestamp);

        String selection = EventPersistenceContract.EventEntry._ID + " LIKE ?";
        String[] selectionArgs = {eventId};

        int rows = mContentResolver.update(EventPersistenceContract.EventEntry.buildEventUri(), values, selection, selectionArgs);

        if (rows != 0){
            callback.onEventUpdated();
        } else {
            callback.onEventNotUpdated();
        }
    }

    public void deleteEvent(@NonNull String eventId, @NonNull DeleteEventCallback callback){
        checkNotNull(eventId);
        checkNotNull(callback);

        String selection = EventPersistenceContract.EventEntry._ID + " LIKE ?";
        String[] selectionArgs = {eventId};

        int rows = mContentResolver.delete(EventPersistenceContract.EventEntry.buildEventUri(), selection, selectionArgs);

        if (rows != 0){
            callback.onEventDeleted();
        } else {
            callback.onEventNotDeleted();
        }
    }

    public void favoriteEvent(@NonNull String eventId, @NonNull FavoriteEventCallback callback){
        checkNotNull(eventId);
        checkNotNull(callback);

        ContentValues values = new ContentValues();
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_FAVORITE, true);

        String selection = EventPersistenceContract.EventEntry._ID + " LIKE ?";
        String[] selectionArgs = {eventId};

        int rows = mContentResolver.update(EventPersistenceContract.EventEntry.buildEventUri(), values, selection, selectionArgs);

        if (rows != 0){
            callback.onEventFavorited();
        } else {
            callback.onEventNotFavorited();
        }
    }

    public void unfavoriteEvent(@NonNull String eventId, @NonNull UnfavoriteEventCallback callback){
        checkNotNull(eventId);
        checkNotNull(callback);

        ContentValues values = new ContentValues();
        values.put(EventPersistenceContract.EventEntry.COLUMN_NAME_FAVORITE, false);

        String selection = EventPersistenceContract.EventEntry._ID + " LIKE ?";
        String[] selectionArgs = {eventId};

        int rows = mContentResolver.update(EventPersistenceContract.EventEntry.buildEventUri(), values, selection, selectionArgs);

        if (rows != 0){
            callback.onEventUnfavorited();
        } else {
            callback.onEventNotUnfavorited();
        }
    }

}
