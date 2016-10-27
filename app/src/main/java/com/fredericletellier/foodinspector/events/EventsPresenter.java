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

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.FoodInspectorRepository;
import com.fredericletellier.foodinspector.data.source.LoaderProvider;
import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link EventsFragment}), retrieves the data and updates the
 * UI as required. It is implemented as a non UI {@link Fragment} to make use of the
 * {@link LoaderManager} mechanism for managing loading and updating data asynchronously.
 */
public class EventsPresenter implements EventsContract.Presenter,
        EventDataSource.RefreshEventsOnErrorCallback, LoaderManager.LoaderCallbacks<Cursor> {

    public final static int EVENTS_LOADER = 1;

    private final EventsContract.View mEventsView;

    @NonNull
    private final FoodInspectorRepository mFoodInspectorRepository;

    @NonNull
    private final LoaderManager mLoaderManager;

    @NonNull
    private final LoaderProvider mLoaderProvider;

    private EventsFilter mCurrentFiltering;

    public EventsPresenter(@NonNull LoaderProvider loaderProvider, @NonNull LoaderManager loaderManager, @NonNull FoodInspectorRepository foodInspectorRepository, @NonNull EventsContract.View eventsView, @NonNull EventsFilter eventsFilter) {
        mLoaderProvider = checkNotNull(loaderProvider, "loaderProvider provider cannot be null");
        mLoaderManager = checkNotNull(loaderManager, "loaderManager provider cannot be null");
        mFoodInspectorRepository = checkNotNull(foodInspectorRepository, "foodInspectorRepository provider cannot be null");
        mEventsView = checkNotNull(eventsView, "eventsView cannot be null!");
        mCurrentFiltering = checkNotNull(eventsFilter, "eventsFilter cannot be null!");
        mEventsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks();
    }

    /**
     * We will always have fresh data from remote, the Loaders handle the local data
     */
    public void loadTasks() {
        mEventsView.setLoadingIndicator(true);
        mFoodInspectorRepository.refreshEventsOnError(this);
    }

    @Override
    public void onDataLoaded(Cursor data) {
        mEventsView.setLoadingIndicator(false);
        // Show the list of events
        mEventsView.showTasks(data);
    }


    @Override
    public void onDataEmpty() {
        mEventsView.setLoadingIndicator(false);
        // Show a message indicating there are no events for that filter type.
        processEmptyTasks();
    }

    @Override
    public void onEventsOnErrorRefreshed(List<Event> events) {
        // we don't care about the result since the CursorLoader will load the data for us
        if (mLoaderManager.getLoader(EVENTS_LOADER) == null) {
            mLoaderManager.initLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
        } else {
            mLoaderManager.restartLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        mEventsView.setLoadingIndicator(false);
        mEventsView.showLoadingEventsError();
    }

    @Override
    public void onDataReset() {
        mEventsView.showTasks(null);
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering.getEventsFilterType()) {
            default:
            case ALL_EVENTS:
                mEventsView.showNoEvents();
                break;
            case EVENTS_WITH_BOOKMARKED_PRODUCT:
                mEventsView.showNoEventsWithBookmarkedProduct();
                break;
        }
    }

    @Override
    public void openEventDetails(@NonNull Event requestedEvent) {
        checkNotNull(requestedEvent, "requestedEvent cannot be null!");
        mEventsView.showTaskDetailsUi(requestedEvent);
    }

    @Override
    public EventsFilterType getFiltering() {
        return mCurrentFiltering.getEventsFilterType();
    }

    /**
     * Sets the current task filtering type.
     *
     * @param eventsFilter Can be {@link EventsFilterType#ALL_EVENTS} or
     *                   {@link EventsFilterType#EVENTS_WITH_BOOKMARKED_PRODUCT}
     */
    @Override
    public void setFiltering(EventsFilter eventsFilter) {
        mCurrentFiltering = eventsFilter;
        mLoaderManager.initLoader(EVENTS_LOADER, mCurrentFiltering.getFilterExtras(), this);
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

