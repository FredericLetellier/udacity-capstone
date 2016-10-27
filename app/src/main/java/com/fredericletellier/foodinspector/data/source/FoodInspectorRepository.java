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

import com.fredericletellier.foodinspector.FoodInspector;
import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.CategoryTag;
import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.Suggestion;
import com.fredericletellier.foodinspector.util.Connectivity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to check local data exist, and load remote data
 */
public class FoodInspectorRepository implements ProductDataSource, EventDataSource,
        CategoryDataSource, CategoryTagDataSource, CountryCategoryDataSource, SuggestionDataSource {

    private static FoodInspectorRepository INSTANCE = null;

    private final ProductDataSource mProductRemoteDataSource;

    private final ProductDataSource mProductLocalDataSource;

    private final EventDataSource mEventLocalDataSource;

    private final CategoryDataSource mCategoryRemoteDataSource;

    private final CategoryDataSource mCategoryLocalDataSource;

    private final CategoryTagDataSource mCategoryTagLocalDataSource;

    private final CountryCategoryDataSource mCountryCategoryRemoteDataSource;

    private final CountryCategoryDataSource mCountryCategoryLocalDataSource;

    private final SuggestionDataSource mSuggestionLocalDataSource;


    // Prevent direct instantiation.
    private FoodInspectorRepository(@NonNull ProductDataSource productRemoteDataSource,
                                    @NonNull ProductDataSource productLocalDataSource,
                                    @NonNull EventDataSource eventLocalDataSource,
                                    @NonNull CategoryDataSource categoryRemoteDataSource,
                                    @NonNull CategoryDataSource categoryLocalDataSource,
                                    @NonNull CategoryTagDataSource categoryTagLocalDataSource,
                                    @NonNull CountryCategoryDataSource countryCategoryRemoteDataSource,
                                    @NonNull CountryCategoryDataSource countryCategoryLocalDataSource,
                                    @NonNull SuggestionDataSource suggestionLocalDataSource) {
        mProductRemoteDataSource = checkNotNull(productRemoteDataSource);
        mProductLocalDataSource = checkNotNull(productLocalDataSource);
        mEventLocalDataSource = checkNotNull(eventLocalDataSource);
        mCategoryRemoteDataSource = checkNotNull(categoryRemoteDataSource);
        mCategoryLocalDataSource = checkNotNull(categoryLocalDataSource);
        mCategoryTagLocalDataSource = checkNotNull(categoryTagLocalDataSource);
        mCountryCategoryRemoteDataSource = checkNotNull(countryCategoryRemoteDataSource);
        mCountryCategoryLocalDataSource = checkNotNull(countryCategoryLocalDataSource);
        mSuggestionLocalDataSource = checkNotNull(suggestionLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param productRemoteDataSource the backend product data source
     * @param productLocalDataSource  the device storage product data source
     * @param eventLocalDataSource  the device storage event data source
     * @param categoryRemoteDataSource the backend category data source
     * @param categoryLocalDataSource  the device storage category data source
     * @param categoryTagLocalDataSource  the device storage categoryTag data source
     * @param countryCategoryRemoteDataSource  the backend countryCategory data source
     * @param countryCategoryLocalDataSource  the device storage countryCategory data source
     * @param suggestionLocalDataSource  the device storage suggestion data source
     * @return the {@link FoodInspectorRepository} instance
     */
    public static FoodInspectorRepository getInstance(ProductDataSource productRemoteDataSource,
                                                      ProductDataSource productLocalDataSource,
                                                      EventDataSource eventLocalDataSource,
                                                      CategoryDataSource categoryRemoteDataSource,
                                                      CategoryDataSource categoryLocalDataSource,
                                                      CategoryTagDataSource categoryTagLocalDataSource,
                                                      CountryCategoryDataSource countryCategoryRemoteDataSource,
                                                      CountryCategoryDataSource countryCategoryLocalDataSource,
                                                      SuggestionDataSource suggestionLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FoodInspectorRepository(productRemoteDataSource, productLocalDataSource,
                    eventLocalDataSource, categoryRemoteDataSource, categoryLocalDataSource,
                    categoryTagLocalDataSource, countryCategoryRemoteDataSource,
                    countryCategoryLocalDataSource, suggestionLocalDataSource);
        }
        return INSTANCE;
    }

    // TODO Optimize : some callbacks are not supported, commented as "no-op in a loop"
    // TODO Optimize : callbacks in error need to be handle globally and correctly

    /**
     * Used to force {@link #getInstance(ProductDataSource, ProductDataSource, EventDataSource, CategoryDataSource, CategoryDataSource, CategoryTagDataSource, CountryCategoryDataSource, CountryCategoryDataSource, SuggestionDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getCategory(@NonNull final String categoryKey, @NonNull final GetCategoryCallback getCategoryCallback) {

        mCategoryLocalDataSource.getCategory(categoryKey, new GetCategoryCallback() {
            @Override
            public void onCategoryLoaded(Category category) {
                getCategoryCallback.onCategoryLoaded(category);
            }

            @Override
            public void onError(Throwable throwable) {
                mCategoryRemoteDataSource.getCategory(categoryKey, new GetCategoryCallback() {

                    @Override
                    public void onCategoryLoaded(final Category category) {
                        mCategoryLocalDataSource.saveCategory(category, new SaveCategoryCallback() {
                            @Override
                            public void onCategorySaved() {
                                getCategoryCallback.onCategoryLoaded(category);
                            }

                            @Override
                            public void onError() {
                                getCategoryCallback.onError(null);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getCategoryCallback.onError(throwable);
                    }
                });
            }
        });
    }

    @Override
    public void checkExistCategory(@NonNull String categoryKey,
                                   @NonNull CheckExistCategoryCallback checkExistCategoryCallback) {

        // no-op
    }

    @Override
    public void addCategory(@NonNull Category category,
                            @NonNull AddCategoryCallback addCategoryCallback) {

        // no-op
    }

    @Override
    public void updateCategory(@NonNull Category category,
                               @NonNull UpdateCategoryCallback updateCategoryCallback) {

        // no-op
    }

    @Override
    public void saveCategory(@NonNull Category category,
                             @NonNull final SaveCategoryCallback saveCategoryCallback) {

        // no-op
    }

    @Override
    public void getCategoryTags(@NonNull String barcode,
                                @NonNull GetCategoryTagsCallback getCategoryTagsCallback) {
        // no-op
    }

    @Override
    public void checkExistCategoryTag(@NonNull String barcode, @NonNull String categoryKey,
                                      @NonNull CheckExistCategoryTagCallback checkExistCategoryTagCallback) {

        // no-op
    }

    @Override
    public void addCategoryTag(@NonNull CategoryTag categoryTag,
                               @NonNull AddCategoryTagCallback addCategoryTagCallback) {

        // no-op
    }

    @Override
    public void updateCategoryTag(@NonNull CategoryTag categoryTag,
                                  @NonNull UpdateCategoryTagCallback updateCategoryTagCallback) {

        // no-op
    }

    @Override
    public void saveCategoryTag(@NonNull CategoryTag categoryTag,
                                @NonNull final SaveCategoryTagCallback saveCategoryTagCallback) {

        // no-op
    }

    @Override
    public void getCountryCategory(@NonNull final String categoryKey, @NonNull final String countryKey, @NonNull final GetCountryCategoryCallback getCountryCategoryCallback) {

        mCountryCategoryLocalDataSource.getCountryCategory(categoryKey, countryKey, new GetCountryCategoryCallback() {
            @Override
            public void onCountryCategoryLoaded(CountryCategory countryCategory) {
                getCountryCategoryCallback.onCountryCategoryLoaded(countryCategory);
            }

            @Override
            public void onError(Throwable throwable) {
                mCountryCategoryLocalDataSource.getCountryCategory(categoryKey, countryKey, new GetCountryCategoryCallback() {
                    @Override
                    public void onCountryCategoryLoaded(final CountryCategory countryCategory) {
                        mCountryCategoryLocalDataSource.saveCountryCategory(countryCategory, new SaveCountryCategoryCallback() {

                            @Override
                            public void onCountryCategorySaved() {
                                getCountryCategoryCallback.onCountryCategoryLoaded(countryCategory);
                            }

                            @Override
                            public void onError() {
                                getCountryCategoryCallback.onError(null);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getCountryCategoryCallback.onError(throwable);
                    }
                });
            }
        });
    }

    @Override
    public void checkExistCountryCategory(@NonNull String categoryKey, @NonNull String countryKey,
                                          @NonNull CheckExistCountryCategoryCallback checkExistCountryCategoryCallback) {

        // no-op
    }

    @Override
    public void addCountryCategory(@NonNull CountryCategory countryCategory,
                                   @NonNull AddCountryCategoryCallback addCountryCategoryCallback) {

        // no-op
    }

    @Override
    public void updateCountryCategory(@NonNull CountryCategory countryCategory,
                                      @NonNull UpdateCountryCategoryCallback updateCountryCategoryCallback) {

        // no-op
    }

    @Override
    public void saveCountryCategory(@NonNull CountryCategory countryCategory,
                                    @NonNull final SaveCountryCategoryCallback saveCountryCategoryCallback) {

        // no-op
    }

    @Override
    public void getCountryCategoryOfProduct(@NonNull final String barcode, @NonNull final String countryKey,
                                            @NonNull final GetCountryCategoryOfProductCallback getCountryCategoryOfProductCallback) {
        parseProduct(barcode, new ParseProductCallback() {
            @Override
            public void onProductParsed() {
                mCategoryTagLocalDataSource.getCategoryTags(barcode, new GetCategoryTagsCallback() {
                    @Override
                    public void onCategoryTagsLoaded(List<String> categoriesKey) {
                        for (final String categoryKey : categoriesKey) {
                            getCountryCategory(categoryKey, countryKey, new GetCountryCategoryCallback() {
                                @Override
                                public void onCountryCategoryLoaded(CountryCategory countryCategory) {
                                    // no-op in a loop
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    // no-op in a loop
                                }
                            });
                        }
                    }

                    @Override
                    public void onCategoryTagsNotExist() {
                        getCountryCategoryOfProductCallback.onError(null);
                    }
                });
            }

            @Override
            public void onProductMustBeParsed(String parsableCategories) {
                getCountryCategoryOfProductCallback.onError(null);
            }

            @Override
            public void onError() {
                getCountryCategoryOfProductCallback.onError(null);
            }
        });
    }

    @Override
    public void checkExistEvent(@NonNull String barcode,
                                @NonNull CheckExistEventCallback checkExistEventCallback) {

        // no-op
    }

    @Override
    public void addEvent(@NonNull Event event,
                         @NonNull AddEventCallback addEventCallback) {

        // no-op
    }

    @Override
    public void updateEvent(@NonNull Event event,
                            @NonNull UpdateEventCallback updateEventCallback) {

        // no-op
    }

    @Override
    public void saveEvent(@NonNull Event event,
                          @NonNull final SaveEventCallback saveEventCallback) {

        // no-op
    }

    @Override
    public void saveScan(@NonNull final String barcode,
                         @NonNull final SaveScanCallback saveScanCallback) {

        getProduct(barcode, new GetProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                Event event = new Event(barcode, Event.STATUS_OK);
                mEventLocalDataSource.saveEvent(event, new SaveEventCallback() {
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
                Event event = new Event(barcode, eventStatus);

                mEventLocalDataSource.saveEvent(event, new SaveEventCallback() {
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
        });
    }

    @Override
    public void refreshEventsOnError(@NonNull final RefreshEventsOnErrorCallback refreshEventsOnErrorCallback) {
        mEventLocalDataSource.getEventsOnError(new GetEventsOnErrorCallback() {
            @Override
            public void onEventsOnErrorLoaded(List<String> barcodeEventsOnError) {
                if (!Connectivity.isConnected(FoodInspector.getContext())){
                    refreshEventsOnErrorCallback.onError(null);
                    return;
                }

                for (final String barcodeEventOnError : barcodeEventsOnError) {
                    getProduct(barcodeEventOnError, new GetProductCallback() {
                        @Override
                        public void onProductLoaded(Product product) {
                            Event event = new Event(barcodeEventOnError, Event.STATUS_OK);
                            mEventLocalDataSource.saveEvent(event, new SaveEventCallback() {
                                @Override
                                public void onEventSaved() {
                                    // no-op in a loop
                                }

                                @Override
                                public void onError() {
                                    // no-op in loop
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            // no-op in a loop
                        }
                    });
                }
            }

            @Override
            public void onError() {
                refreshEventsOnErrorCallback.onEventsOnErrorRefreshed(null);
            }
        });
    }

    @Override
    public void getEventsOnError(@NonNull GetEventsOnErrorCallback getEventsOnErrorCallback) {

        // no-op
    }

    @Override
    public void getProduct(@NonNull final String barcode,
                           @NonNull final GetProductCallback getProductCallback) {

        mProductLocalDataSource.getProduct(barcode, new GetProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                getProductCallback.onProductLoaded(product);
            }

            @Override
            public void onError(Throwable throwable) {
                mProductRemoteDataSource.getProduct(barcode, new GetProductCallback() {
                    @Override
                    public void onProductLoaded(final Product product) {
                        mProductLocalDataSource.saveProduct(product, new SaveProductCallback() {
                            @Override
                            public void onProductSaved(String barcode) {
                                getProductCallback.onProductLoaded(product);
                            }

                            @Override
                            public void onError() {
                                getProductCallback.onError(null);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getProductCallback.onError(throwable);
                    }
                });
            }
        });
    }

    @Override
    public void getProducts(@NonNull final String categoryKey, @NonNull final String countryKey,
                            @NonNull final String nutritionGradeValue, @NonNull final Integer offsetProducts,
                            @NonNull final Integer numberOfProducts, @NonNull final GetProductsCallback getProductsCallback) {
        mProductLocalDataSource.getProducts(categoryKey, countryKey, nutritionGradeValue, offsetProducts,
                numberOfProducts, new GetProductsCallback() {
                    @Override
                    public void onProductsLoaded(List<Product> products) {
                        getProductsCallback.onProductsLoaded(null);
                    }

                    @Override
                    public void onProductsUnfilled() {
                        if (!Connectivity.isConnected(FoodInspector.getContext())){
                            getProductsCallback.onError(null);
                            return;
                        }

                        mProductRemoteDataSource.getProducts(categoryKey, countryKey, nutritionGradeValue, offsetProducts,
                                numberOfProducts, new GetProductsCallback() {
                                    @Override
                                    public void onProductsLoaded(List<Product> products) {

                                        for (final Product product : products) {
                                            mProductLocalDataSource.saveProduct(product, new SaveProductCallback() {
                                                @Override
                                                public void onProductSaved(String barcode) {
                                                    Suggestion suggestion = new Suggestion(barcode, categoryKey, countryKey);
                                                    mSuggestionLocalDataSource.saveSuggestion(suggestion, new SaveSuggestionCallback() {
                                                        @Override
                                                        public void onSuggestionSaved() {
                                                            // no-op in loop
                                                        }

                                                        @Override
                                                        public void onError() {
                                                            // no-op in loop
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onError() {
                                                    // no-op in loop
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onProductsUnfilled() {
                                        getProductsCallback.onProductsUnfilled();
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        getProductsCallback.onError(throwable);
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getProductsCallback.onError(throwable);
                    }
                });
    }

    @Override
    public void checkExistProduct(@NonNull String barcode,
                                  @NonNull CheckExistProductCallback checkExistProductCallback) {

        // no-op
    }

    @Override
    public void addProduct(@NonNull Product product,
                           @NonNull AddProductCallback addProductCallback) {

        // no-op
    }

    @Override
    public void updateProduct(@NonNull Product product,
                              @NonNull UpdateProductCallback updateProductCallback) {

        // no-op
    }

    @Override
    public void saveProduct(@NonNull Product product,
                            @NonNull final SaveProductCallback saveProductCallback) {

        // no-op
    }

    AtomicInteger countDownLatch = null;
    AtomicInteger countDownLatchSuccess = null;

    @Override
    public void parseProduct(@NonNull final String barcode,
                             @NonNull final ParseProductCallback parseProductCallback) {

        mProductLocalDataSource.parseProduct(barcode, new ParseProductCallback() {
            @Override
            public void onProductParsed() {
                parseProductCallback.onProductParsed();
            }

            @Override
            public void onProductMustBeParsed(final String parsableCategories) {
                String[] parsedCategories = parsableCategories.split(",");

                countDownLatch = new AtomicInteger(parsedCategories.length);
                countDownLatchSuccess = new AtomicInteger(parsedCategories.length);

                for (int i = 0; i < parsedCategories.length; i++) {
                    final int rank = i;
                    final String parsedCategory = parsedCategories[i];

                    getCategory(parsedCategory, new GetCategoryCallback() {
                        @Override
                        public void onCategoryLoaded(Category category) {
                            CategoryTag categoryTag = new CategoryTag(barcode, parsedCategory, rank);
                            mCategoryTagLocalDataSource.saveCategoryTag(categoryTag, new SaveCategoryTagCallback() {
                                @Override
                                public void onCategoryTagSaved() {
                                    int value = countDownLatch.decrementAndGet();
                                    countDownLatchSuccess.decrementAndGet();
                                    if ( value == 0 ) {
                                        afterParseProduct(barcode, parseProductCallback);
                                    }
                                }

                                @Override
                                public void onError() {
                                    int value = countDownLatch.decrementAndGet();
                                    if ( value == 0 ) {
                                        afterParseProduct(barcode, parseProductCallback);                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }
                    });

                }

            }

            @Override
            public void onError() {
                parseProductCallback.onError();
            }
        });
    }

    private void afterParseProduct(String barcode, final ParseProductCallback parseProductCallback){
        if (countDownLatch.get() == countDownLatchSuccess.get()) {
            Product product = new Product(barcode, true);
            saveProduct(product, new SaveProductCallback() {
                @Override
                public void onProductSaved(String barcode) {
                    parseProductCallback.onProductParsed();
                }

                @Override
                public void onError() {
                    parseProductCallback.onError();
                }
            });
        } else {
            parseProductCallback.onError();
        }
    }

    @Override
    public void updateProductBookmark(@NonNull String barcode,
                                      @NonNull final UpdateProductBookmarkCallback updateProductBookmarkCallback) {
        mProductLocalDataSource.updateProductBookmark(barcode, updateProductBookmarkCallback);
    }

    @Override
    public void checkExistSuggestion(@NonNull String barcode,
                                     @NonNull String categoryKey, @NonNull String countryKey,
                                     @NonNull CheckExistSuggestionCallback checkExistSuggestionCallback) {

        // no-op
    }

    @Override
    public void addSuggestion(@NonNull Suggestion suggestion,
                              @NonNull AddSuggestionCallback addSuggestionCallback) {

        // no-op
    }

    @Override
    public void updateSuggestion(@NonNull Suggestion suggestion,
                                 @NonNull UpdateSuggestionCallback updateSuggestionCallback) {

        // no-op
    }

    @Override
    public void saveSuggestion(@NonNull Suggestion suggestion,
                               @NonNull final SaveSuggestionCallback saveSuggestionCallback) {

        // no-op
    }

    public interface LoadDataCallback {
        void onDataLoaded(Cursor data);

        void onDataEmpty();

        void onError(Throwable throwable);

        void onDataReset();
    }
}