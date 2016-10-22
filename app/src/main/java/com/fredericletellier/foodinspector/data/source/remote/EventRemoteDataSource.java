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

package com.fredericletellier.foodinspector.data.source.remote;

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;

/**
 * Concrete implementation of a data source as a remote db
 */
public class EventRemoteDataSource implements EventDataSource {

    private static EventRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private EventRemoteDataSource() {
    }

    public static EventRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getEvent(@NonNull Event event, @NonNull GetEventCallback getEventCallback) {
        //no-op in remote
    }

    @Override
    public void addEvent(@NonNull Event event, @NonNull AddEventCallback addEventCallback) {
        //no-op in remote
    }

    @Override
    public void updateEvent(@NonNull Event event, @NonNull UpdateEventCallback updateEventCallback) {
        //no-op in remote
    }

    @Override
    public void saveEvent(@NonNull Event event, @NonNull SaveEventCallback saveEventCallback) {
        //no-op in remote
    }

    @Override
    public void saveScan(@NonNull String barcode, @NonNull SaveScanCallback saveScanCallback) {
        //no-op in remote
        //TODO Intégrer la partie remote de la procédure globale du repository
        //TODO ou pas..
    }

    @Override
    public void refreshEventsOnError(@NonNull RefreshEventsOnErrorCallback refreshEventsOnErrorCallback) {
        //TODO Intégrer la partie remote de la procédure globale du repository
    }

    @Override
    public void getEventsOnError(@NonNull GetEventsOnErrorCallback getEventsOnErrorCallback) {
        //no-op in remote
    }

}