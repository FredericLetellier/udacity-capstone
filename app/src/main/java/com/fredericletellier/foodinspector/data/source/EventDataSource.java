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

import java.util.List;

/**
 * Main entry point for accessing event data.
 */
public interface EventDataSource {
    
    interface GetEventCallback {

        void onEventLoaded(Event event);

        void onError(Throwable throwable);

    }

    interface AddEventCallback {

        void onEventAdded(Event event);

        void onError(Throwable throwable);

    }

    interface UpdateEventCallback {

        void onEventUpdated(Event event);

        void onError(Throwable throwable);

    }

    interface SaveEventCallback {

        void onEventSaved(Event event);

        void onError(Throwable throwable);

    }

    interface SaveScanCallback {

        void onScanSaved(Event event);

        void onError(Throwable throwable);

    }

    interface RefreshEventsOnErrorCallback {

        void onEventsOnErrorRefreshed(List<Event> events);

        void onError(Throwable throwable);

    }

    interface GetEventsOnErrorCallback {

        void onEventsOnErrorLoaded(List<Event> events);

        void onError(Throwable throwable);

    }

    void getEvent(@NonNull Event event, @NonNull GetEventCallback getEventCallback);

    void addEvent(@NonNull Event event, @NonNull AddEventCallback addEventCallback);

    void updateEvent(@NonNull Event event, @NonNull UpdateEventCallback updateEventCallback);

    void saveEvent(@NonNull Event event, @NonNull SaveEventCallback saveEventCallback);

    void saveScan(@NonNull String barcode, @NonNull SaveScanCallback saveScanCallback);

    void refreshEventsOnError(@NonNull RefreshEventsOnErrorCallback refreshEventsOnErrorCallback);

    void getEventsOnError(@NonNull GetEventsOnErrorCallback getEventsOnErrorCallback);

}
