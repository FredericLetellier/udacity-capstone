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

package com.fredericletellier.foodinspector.data.source;

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Event;

/**
 * Main entry point for accessing events data.
 */
public interface EventDataSource {

    interface GetEventsCallback {

        void onEventsLoaded();

        void onDataNotAvailable();
    }

    interface AddEventCallback {

        void onEventSaved();

        void onEventNotSaved();
    }

    interface UpdateEventCallback {

        void onEventUpdated();

        void onEventNotUpdated();
    }

    interface DeleteEventCallback {

        void onEventDeleted();

        void onEventNotDeleted();
    }

    interface FavoriteEventCallback {

        void onEventFavorited();

        void onEventNotFavorited();
    }

    interface UnfavoriteEventCallback {

        void onEventUnfavorited();

        void onEventNotUnfavorited();
    }

    void getEvents(@NonNull GetEventsCallback callback);

    void addEvent(@NonNull Event event, @NonNull AddEventCallback callback);

    void updateEvent(@NonNull String eventId, @NonNull UpdateEventCallback callback);

    void deleteEvent(@NonNull String eventId, @NonNull DeleteEventCallback callback);

    void favoriteEvent(@NonNull String eventId, @NonNull FavoriteEventCallback callback);

    void unfavoriteEvent(@NonNull String eventId, @NonNull UnfavoriteEventCallback callback);

}
