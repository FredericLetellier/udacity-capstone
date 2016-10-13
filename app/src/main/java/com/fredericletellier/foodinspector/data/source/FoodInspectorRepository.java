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

    private final EventRemoteSource mEventRemoteDataSource;

    private final EventDataSource mEventLocalDataSource;

    // Prevent direct instantiation.
    private FoodInspectorRepository(@NonNull ProductDataSource productRemoteDataSource,
                                    @NonNull ProductDataSource productLocalDataSource,
                                    @NonNull CategoryDataSource categoryRemoteDataSource,
                                    @NonNull CategoryDataSource categoryLocalDataSource,
                                    @NonNull EventRemoteSource eventRemoteDataSource,
                                    @NonNull EventDataSource eventLocalDataSource,) {
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
                                                      EventRemoteSource eventRemoteDataSource,
                                                      EventDataSource eventLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FoodInspectorRepository(productRemoteDataSource, productLocalDataSource,
                    categoryRemoteDataSource, categoryLocalDataSource, eventRemoteDataSource,
                    eventLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ProductDataSource, ProductDataSource, CategoryDataSource, CategoryDataSource, EventRemoteSource, EventDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Check products exist from local data source (SQLite), if not exist get products from remote data source
     * Note: {@link GetProductsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getProducts(@NonNull final String categoryId, @NonNull final GetProductsCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(callback);

        // Check exist in local
        mProductLocalDataSource.getProducts(categoryId, new GetProductsCallback() {
            @Override
            public void onProductsLoaded() {
                callback.onProductsLoaded();
            }

            @Override
            public void onDataNotAvailable() {

                // Load from server
                mProductRemoteDataSource.getProducts(categoryId, new GetProductsCallback() {
                    @Override
                    public void onProductsLoaded() {
                        callback.onProductsLoaded();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onErrorCategoryNotAvailable() {
                        callback.onErrorCategoryNotAvailable();
                    }
                });
            }

            @Override
            public void onErrorCategoryNotAvailable() {
                callback.onErrorCategoryNotAvailable();
            }
        });
    }

    /**
     * Check product exist from local data source (SQLite), if not exist get product from remote data source
     * Note: {@link GetProductsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getProduct(@NonNull final String productId, @NonNull final GetProductCallback callback) {
        checkNotNull(productId);
        checkNotNull(callback);

        // Check exist in local
        mProductLocalDataSource.getProduct(productId, new GetProductCallback() {
            @Override
            public void onProductLoaded() {
                callback.onProductLoaded();
            }

            @Override
            public void onDataNotAvailable() {
                // Load from server
                mProductRemoteDataSource.getProduct(productId, new GetProductCallback() {
                    @Override
                    public void onProductLoaded() {
                        callback.onProductLoaded();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    /**
     * Check categories exist from local data source (SQLite), if not exist get categories from remote data source
     * Note: {@link GetCategoriesCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getCategories(@NonNull final String productId, @NonNull final String countryCode, @NonNull final GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);

        // Check exist in local
        mCategoryLocalDataSource.getCategories(productId, countryCode, new GetCategoriesCallback() {
            @Override
            public void onCategoriesLoaded() {
                callback.onCategoriesLoaded();
            }

            @Override
            public void onDataNotAvailable() {
                // Load from server
                mCategoryRemoteDataSource.getCategories(productId, countryCode, new GetCategoriesCallback() {
                    @Override
                    public void onCategoriesLoaded() {
                        callback.onCategoriesLoaded();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onErrorProductNotAvailable() {
                        callback.onErrorProductNotAvailable();
                    }
                });
            }

            @Override
            public void onErrorProductNotAvailable() {
                callback.onErrorProductNotAvailable();
            }
        });
    }

    @Override
    public void getEvents(@NonNull GetEventsCallback callback) {
        checkNotNull(callback);
        mEventLocalDataSource.getEvents(callback);
    }

    // TODO Optimize Add/Update of Event with check of barcode and the link with the product
    @Override
    public void addEvent(@NonNull Event event, @NonNull AddEventCallback callback) {
        checkNotNull(event);
        checkNotNull(callback);
        mEventLocalDataSource.addEvent(event, callback);
    }

    @Override
    public void updateEvent(@NonNull String eventId, @NonNull UpdateEventCallback callback) {
        checkNotNull(eventId);
        checkNotNull(callback);
        mEventLocalDataSource.updateEvent(eventId, callback);
    }

    @Override
    public void deleteEvent(@NonNull String eventId, @NonNull DeleteEventCallback callback) {
        checkNotNull(eventId);
        checkNotNull(callback);
        mEventLocalDataSource.deleteEvent(eventId, callback);
    }

    @Override
    public void favoriteEvent(@NonNull String eventId, @NonNull FavoriteEventCallback callback) {
        checkNotNull(eventId);
        checkNotNull(callback);
        mEventLocalDataSource.favoriteEvent(eventId, callback);
    }

    @Override
    public void unfavoriteEvent(@NonNull String eventId, @NonNull UnfavoriteEventCallback callback) {
        checkNotNull(eventId);
        checkNotNull(callback);
        mEventLocalDataSource.unfavoriteEvent(eventId, callback);
    }


    // TODO LoadDataCallback

}
