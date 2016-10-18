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
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Event;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to check local data exist, and load remote data
 */
public class FoodInspectorRepository implements ProductDataSource, CategoryDataSource, EventDataSource {

    private static FoodInspectorRepository INSTANCE = null;

    private final ProductDataSource mProductRemoteDataSource;

    private final ProductDataSource mProductLocalDataSource;

    private final CategoryDataSource mCategoryRemoteDataSource;

    private final CategoryDataSource mCategoryLocalDataSource;

    private final EventDataSource mEventRemoteDataSource;

    private final EventDataSource mEventLocalDataSource;

    // Prevent direct instantiation.
    private FoodInspectorRepository(@NonNull ProductDataSource productRemoteDataSource,
                                    @NonNull ProductDataSource productLocalDataSource,
                                    @NonNull CategoryDataSource categoryRemoteDataSource,
                                    @NonNull CategoryDataSource categoryLocalDataSource,
                                    @NonNull EventDataSource eventRemoteDataSource,
                                    @NonNull EventDataSource eventLocalDataSource) {
        mProductRemoteDataSource = checkNotNull(productRemoteDataSource);
        mProductLocalDataSource = checkNotNull(productLocalDataSource);
        mCategoryRemoteDataSource = checkNotNull(categoryRemoteDataSource);
        mCategoryLocalDataSource = checkNotNull(categoryLocalDataSource);
        mEventRemoteDataSource = checkNotNull(eventRemoteDataSource);
        mEventLocalDataSource = checkNotNull(eventLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param productRemoteDataSource the backend product data source
     * @param productLocalDataSource  the device storage product data source
     * @param categoryRemoteDataSource the backend category data source
     * @param categoryLocalDataSource  the device storage category data source
     * @param eventRemoteDataSource  the backend category data source
     * @param eventLocalDataSource  the device storage event data source
     * @return the {@link FoodInspectorRepository} instance
     */
    public static FoodInspectorRepository getInstance(ProductDataSource productRemoteDataSource,
                                                      ProductDataSource productLocalDataSource,
                                                      CategoryDataSource categoryRemoteDataSource,
                                                      CategoryDataSource categoryLocalDataSource,
                                                      EventDataSource eventRemoteDataSource,
                                                      EventDataSource eventLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FoodInspectorRepository(productRemoteDataSource, productLocalDataSource,
                    categoryRemoteDataSource, categoryLocalDataSource, eventRemoteDataSource,
                    eventLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ProductDataSource, ProductDataSource, CategoryDataSource, CategoryDataSource, EventDataSource, EventDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Check events from local data source (SQLite), if some events are pending network,
     * {@link GetEventsCallback#onEventsPendingNetwork(List)} is called
     * Get data of product in remote source for each events in the callback
     */
    @Override
    public void getEvents(@Nullable List<Event> events, @NonNull final GetEventsCallback callback){
        checkNotNull(callback);

        mEventLocalDataSource.getEvents(null, new GetEventsCallback() {
            @Override
            public void onEventsPendingNetwork(List<Event> events) {
                mEventRemoteDataSource.getEvents(events, callback);
            }
        });
    }

    /**
     * Check if product of the event exist in local data source (SQLite), if not,
     * {@link AddEventCallback#onEventProductNotAvailable()} is called and
     * Get data of product in remote source for this event
     */
    @Override
    public void addEvent(@NonNull final String productId, @NonNull final AddEventCallback callback){
        checkNotNull(productId);
        checkNotNull(callback);

        mEventLocalDataSource.addEvent(productId, new AddEventCallback() {
            @Override
            public void onEventProductNotAvailable() {
                mEventRemoteDataSource.addEvent(productId, callback);
            }
        });

    }

    /**
     * Update the favorite field of event in local data source (SQLite)
     */
    @Override
    public void updateFavoriteFieldEvent(@NonNull String productId){
        checkNotNull(productId);

        mEventLocalDataSource.updateFavoriteFieldEvent(productId);
    }

    /**
     * Check if categories for this product and this country code exist in local data source (SQLite),
     * If not, {@link GetCategoriesCallback#onCategoriesNotAvailable(ArrayList)} is called
     * Get data in remote source for each categories in the callback
     */
    @Override
    public void getCategories(@NonNull final String productId, @Nullable ArrayList<String> categories, @NonNull final String countryCode, @NonNull final GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);

        mCategoryLocalDataSource.getCategories(productId, null, countryCode, new GetCategoriesCallback() {
            @Override
            public void onCategoriesNotAvailable(ArrayList<String> categories) {
                mCategoryRemoteDataSource.getCategories(null, categories, countryCode, callback);
            }
        });
    }

    /**
     * Check if products exist in local data source (SQLite),
     * If not, {@link GetXProductsInCategoryCallback#onProductsNotAvailable()} is called and
     * Get products in remote source
     */
    @Override
    public void getXProductsInCategory(@NonNull final String categoryId, @NonNull final String nutritionGradeValue, @NonNull final Integer skipProducts, @NonNull final GetXProductsInCategoryCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(nutritionGradeValue);
        checkNotNull(skipProducts);
        checkNotNull(callback);

        mProductLocalDataSource.getXProductsInCategory(categoryId, nutritionGradeValue, skipProducts, new GetXProductsInCategoryCallback() {
            @Override
            public void onProductsNotAvailable() {
                mProductRemoteDataSource.getXProductsInCategory(categoryId, nutritionGradeValue, skipProducts, callback);
            }
        });
    }

}
