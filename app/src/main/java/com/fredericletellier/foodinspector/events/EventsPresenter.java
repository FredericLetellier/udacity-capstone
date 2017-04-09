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
import android.util.Log;

import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.FoodInspectorRepository;
import com.fredericletellier.foodinspector.data.source.LoaderProvider;
import com.google.android.gms.vision.barcode.Barcode;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link EventsFragment}), retrieves the data and updates the
 * UI as required. It is implemented as a non UI {@link Fragment} to make use of the
 * {@link LoaderManager} mechanism for managing loading and updating data asynchronously.
 */
public class EventsPresenter implements
        EventsContract.Presenter,
        FoodInspectorRepository.LoadDataCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = EventsPresenter.class.getName();

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

    public void loadEvents() {
        Log.d(TAG, "loadEvents");
        mEventsView.setLoadingIndicator(true);

        if (mLoaderManager.getLoader(EVENTS_LOADER) == null) {
            mLoaderManager.initLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
        } else {
            mLoaderManager.restartLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
        }
    }

    @Override
    public void onDataLoaded(Cursor data) {
        Log.d(TAG, "onDataLoaded");
        mEventsView.setLoadingIndicator(false);
        mEventsView.showEvents(data);
    }

    @Override
    public void onDataEmpty() {
        Log.d(TAG, "onDataEmpty");
        mEventsView.setLoadingIndicator(false);
        mEventsView.showNoEvents();
    }

    @Override
    public void onError(Throwable throwable) {
        Log.d(TAG, "onError " + throwable.toString());
        mEventsView.setLoadingIndicator(false);
        mEventsView.showLoadingEventsError();
    }

    @Override
    public void onDataReset() {
        Log.d(TAG, "onDataReset");
        mEventsView.showEvents(null);
    }

    @Override
    public void openEventDetails(String barcode) {
        Log.d(TAG, "openEventDetails " + barcode);
        checkNotNull(barcode, "requestedEvent cannot be null!");
        mEventsView.showEventDetailsUi(barcode);
    }

    @Override
    public void clickOnFab() {
        Log.d(TAG, "clickOnFab");
        mEventsView.showScanUi();
    }

    @Override
    public void newEvent(final Barcode barcode) {
        Log.d(TAG, "newEvent " + barcode.toString());
        mFoodInspectorRepository.saveScan(barcode.displayValue, new EventDataSource.SaveScanCallback() {
            @Override
            public void onScanSaved() {
                mEventsView.showEventDetailsUi(barcode.displayValue);
            }

            @Override
            public void onScanSavedWithError() {
                mEventsView.showNewEventError();
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, throwable.toString());
                mEventsView.showNewEventError();
            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        return mLoaderProvider.createFilteredEventsLoader(mCurrentFiltering);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished");
        if (data != null) {
            if (data.moveToLast() && data.getCount() > 0) {
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
        Log.d(TAG, "onLoaderReset");
        onDataReset();
    }

}

