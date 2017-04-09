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

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.Product;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to check local data exist, and load remote data
 */
public class FoodInspectorRepository implements ProductDataSource, EventDataSource,
        CategoryDataSource, CountryCategoryDataSource {

    private static FoodInspectorRepository INSTANCE = null;

    private final EventDataSource.Local mEventLocalDataSource;
    private final ProductDataSource mProductRemoteDataSource;
    private final CategoryDataSource mCategoryRemoteDataSource;
    private final CountryCategoryDataSource mCountryCategoryRemoteDataSource;




    // Prevent direct instantiation.
    private FoodInspectorRepository(@NonNull ProductDataSource productRemoteDataSource,
                                    @NonNull EventDataSource.Local eventLocalDataSource,
                                    @NonNull CategoryDataSource categoryRemoteDataSource,
                                    @NonNull CountryCategoryDataSource countryCategoryRemoteDataSource) {
        mProductRemoteDataSource = checkNotNull(productRemoteDataSource);
        mEventLocalDataSource = checkNotNull(eventLocalDataSource);
        mCategoryRemoteDataSource = checkNotNull(categoryRemoteDataSource);
        mCountryCategoryRemoteDataSource = checkNotNull(countryCategoryRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     */
    public static FoodInspectorRepository getInstance(ProductDataSource productRemoteDataSource,
                                                      EventDataSource.Local eventLocalDataSource,
                                                      CategoryDataSource categoryRemoteDataSource,
                                                      CountryCategoryDataSource countryCategoryRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FoodInspectorRepository(
                    productRemoteDataSource,
                    eventLocalDataSource,
                    categoryRemoteDataSource,
                    countryCategoryRemoteDataSource);
        }
        return INSTANCE;
    }


    /**
     * Used to force to create a new instance next time it's called.
     *
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getCategory(@NonNull final String categoryKey, @NonNull final GetCategoryCallback getCategoryCallback) {

        mCategoryRemoteDataSource.getCategory(categoryKey, new GetCategoryCallback() {
            @Override
            public void onCategoryLoaded(final Category category) {
                getCategoryCallback.onCategoryLoaded(category);
            }
            @Override
            public void onError(Throwable throwable) {
                getCategoryCallback.onError(throwable);
            }
        });
    }

    @Override
    public void getCountryCategory(@NonNull final String categoryKey, @NonNull final String countryKey, @NonNull final GetCountryCategoryCallback getCountryCategoryCallback) {

        mCountryCategoryRemoteDataSource.getCountryCategory(categoryKey, countryKey, new GetCountryCategoryCallback() {
            @Override
            public void onCountryCategoryLoaded(final CountryCategory countryCategory) {
                getCountryCategoryCallback.onCountryCategoryLoaded(countryCategory);
            }

            @Override
            public void onError(Throwable throwable) {
                getCountryCategoryCallback.onError(throwable);
            }
        });
    }



    @Override
    public void getProduct(@NonNull final String barcode,
                           @NonNull final GetProductCallback getProductCallback) {

        mProductRemoteDataSource.getProduct(barcode, new GetProductCallback() {
            @Override
            public void onProductLoaded(final Product product) {
                getProductCallback.onProductLoaded(product);
            }
            @Override
            public void onError(Throwable throwable) {
                getProductCallback.onError(throwable);
            }
        });
    }

    @Override
    public void getProducts(@NonNull final String categoryKey,
                            @NonNull final String nutritionGradeValue, @NonNull final GetProductsCallback getProductsCallback) {
        mProductRemoteDataSource.getProducts(categoryKey, nutritionGradeValue, new GetProductsCallback() {
                    @Override
                    public void onProductsLoaded(List<Product> products) {
                        getProductsCallback.onProductsLoaded(products);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getProductsCallback.onError(throwable);
                    }
                });
    }

    @Override
    public void saveScan(@NonNull final String barcode,
                         @NonNull final SaveScanCallback saveScanCallback) {

        getProduct(barcode, new GetProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                Event event = new Event(barcode, Event.STATUS_OK);
                mEventLocalDataSource.saveEvent(event, new Local.SaveEventCallback() {
                    @Override
                    public void onEventSaved() {
                        saveScanCallback.onScanSaved();
                    }

                    @Override
                    public void onError() {
                        saveScanCallback.onError(null);
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                String eventStatus;

                if (throwable instanceof IOException) {
                    eventStatus = Event.STATUS_NO_NETWORK;
                } else {
                    eventStatus = Event.STATUS_NOT_IN_OFF_DATABASE;
                }
                final Event event = new Event(barcode, eventStatus);

                mEventLocalDataSource.saveEvent(event, new Local.SaveEventCallback() {
                    @Override
                    public void onEventSaved() {
                        if (event.getStatus() == Event.STATUS_OK) {
                            saveScanCallback.onScanSaved();
                        } else {
                            saveScanCallback.onScanSavedWithError();
                        }
                    }

                    @Override
                    public void onError() {
                        saveScanCallback.onError(null);
                    }
                });
            }
        });
    }

    public interface LoadDataCallback {
        void onDataLoaded(Cursor data);

        void onDataEmpty();

        void onError(Throwable throwable);

        void onDataReset();
    }
}