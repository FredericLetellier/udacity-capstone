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

package com.fredericletellier.foodinspector.events;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.FoodInspectorRepository;
import com.fredericletellier.foodinspector.data.source.LoaderProvider;
import com.fredericletellier.foodinspector.data.source.ProductDataSource;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link EventsFragment}), retrieves the data and updates the
 * UI as required. It is implemented as a non UI {@link Fragment} to make use of the
 * {@link LoaderManager} mechanism for managing loading and updating data asynchronously.
 */
public class EventsPresenter implements EventsContract.Presenter, FoodInspectorRepository.LoadDataCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static int EVENTS_LOADER = 1;

    private final EventsContract.View mEventsView;

    @NonNull
    private final FoodInspectorRepository mFoodInspectorRepository;
    private final LoaderManager mLoaderManager;
    private final LoaderProvider mLoaderProvider;

    private EventsFilter mCurrentFiltering;

    public EventsPresenter(@NonNull LoaderProvider loaderProvider,
                           @NonNull LoaderManager loaderManager,
                           @NonNull FoodInspectorRepository foodInspectorRepository,
                           @NonNull EventsContract.View eventsView) {
        mLoaderProvider = checkNotNull(loaderProvider, "loaderProvider provider cannot be null");
        mLoaderManager = checkNotNull(loaderManager, "loaderManager provider cannot be null");
        mFoodInspectorRepository = checkNotNull(foodInspectorRepository, "foodInspectorRepository provider cannot be null");
        mEventsView = checkNotNull(eventsView, "eventsView cannot be null!");
        mEventsView.setPresenter(this);
    }

    @Override
    public void start() {
        mCurrentFiltering = EventsFilter.from(EventsFilterType.ALL_EVENTS);
        loadEvents();
    }

    /**
     * We will always have fresh data from remote, the Loaders handle the local data
     */
    public void loadEvents() {

        if (mLoaderManager.getLoader(EVENTS_LOADER) == null) {
            mLoaderManager.initLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
        } else {
            mLoaderManager.restartLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
        }

        /*
        mFoodInspectorRepository.refreshEventsOnError(this);
        */
    }

    @Override
    public void onDataLoaded(Cursor data) {
        // Show the list of events
        mEventsView.showEvents(data);
    }


    @Override
    public void onDataEmpty() {
        // Show a message indicating there are no events for that filter type.
        processEmptyEvents();
    }



    @Override
    public void onError(Throwable throwable) {
        mEventsView.showLoadingEventsError();
    }

    @Override
    public void onDataReset() {
        mEventsView.showEvents(null);
    }

    private void processEmptyEvents() {
        switch (mCurrentFiltering.getEventsFilterType()) {
            default:
            case ALL_EVENTS:
                mEventsView.showNoEvents();
                break;
        }
    }

    @Override
    public void openEventDetails(String barcode) {
        checkNotNull(barcode, "requestedEvent cannot be null!");
        mEventsView.showEventDetailsUi(barcode);
    }

    @Override
    public void clickOnFab() {
        mEventsView.showScanUi();
    }

    @Override
    public void newEvent(final Barcode barcode) {
        mFoodInspectorRepository.saveScan(barcode.displayValue, new EventDataSource.SaveScanCallback() {
            @Override
            public void onScanSaved() {
                mEventsView.showEventDetailsUi(barcode.displayValue);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createFilteredEventsLoader(mCurrentFiltering);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.moveToLast()) {
                onDataLoaded(data);
            } else {
                onDataEmpty();
            }
        } else {
            onError(null);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        onDataReset();
    }

}

