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
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;

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

    //TODO COMPLETE
    //###LOCAL
    //J'ai un code
    //Je cherche ce produit dans ma base
    //Si ce produit est dans ma base
    //	Creation / Mise à jour du code produit et timestamp de l'evenement
    //Si il n'y est pas
    //	callback = erreur
    @Override
    public void addEvent(@NonNull String productId, @NonNull AddEventCallback callback){
        checkNotNull(productId);
        checkNotNull(callback);

    }

    //TODO COMPLETE
    //###LOCAL
    //J'ai un code
    //Je cherche cet evenement dans ma base
    //Si cet evenement est dans ma base
    //	Mise à jour du champ favori de l'evenement
    //Si cet evenement n'est pas dans ma base
    //	(callback facultatif ?!?)
    @Override
    public void updateFavoriteFieldEvent(@NonNull String productId){
        checkNotNull(productId);
    }
}
