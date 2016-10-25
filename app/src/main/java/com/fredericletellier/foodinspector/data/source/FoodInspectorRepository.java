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

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.CategoryTag;
import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.Suggestion;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to check local data exist, and load remote data
 */
public class FoodInspectorRepository implements ProductDataSource, EventDataSource,
        CategoryDataSource, CategoryTagDataSource, CountryCategoryDataSource, SuggestionDataSource {

    private static FoodInspectorRepository INSTANCE = null;

    private final ProductDataSource mProductRemoteDataSource;

    private final ProductDataSource mProductLocalDataSource;

    private final EventDataSource mEventRemoteDataSource;

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
                                    @NonNull EventDataSource eventRemoteDataSource,
                                    @NonNull EventDataSource eventLocalDataSource,
                                    @NonNull CategoryDataSource categoryRemoteDataSource,
                                    @NonNull CategoryDataSource categoryLocalDataSource,
                                    @NonNull CategoryTagDataSource categoryTagLocalDataSource,
                                    @NonNull CountryCategoryDataSource countryCategoryRemoteDataSource,
                                    @NonNull CountryCategoryDataSource countryCategoryLocalDataSource,
                                    @NonNull SuggestionDataSource suggestionLocalDataSource) {
        mProductRemoteDataSource = checkNotNull(productRemoteDataSource);
        mProductLocalDataSource = checkNotNull(productLocalDataSource);
        mEventRemoteDataSource = checkNotNull(eventRemoteDataSource);
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
     * @param eventRemoteDataSource  the backend event data source
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
                                                      EventDataSource eventRemoteDataSource,
                                                      EventDataSource eventLocalDataSource,
                                                      CategoryDataSource categoryRemoteDataSource,
                                                      CategoryDataSource categoryLocalDataSource,
                                                      CategoryTagDataSource categoryTagLocalDataSource,
                                                      CountryCategoryDataSource countryCategoryRemoteDataSource,
                                                      CountryCategoryDataSource countryCategoryLocalDataSource,
                                                      SuggestionDataSource suggestionLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FoodInspectorRepository(productRemoteDataSource, productLocalDataSource,
                    eventRemoteDataSource, eventLocalDataSource, categoryRemoteDataSource,
                    categoryLocalDataSource, categoryTagLocalDataSource, countryCategoryRemoteDataSource,
                    countryCategoryLocalDataSource, suggestionLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ProductDataSource, ProductDataSource, EventDataSource, EventDataSource, CategoryDataSource, CategoryDataSource, CategoryTagDataSource, CountryCategoryDataSource, CountryCategoryDataSource, SuggestionDataSource)}
     * to create a new instance next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void checkExistCategory(@NonNull String categoryKey, @NonNull CheckExistCategoryCallback checkExistCategoryCallback) {
        //no-op, internal use only
    }

    @Override
    public void addCategory(@NonNull Category category, @NonNull AddCategoryCallback addCategoryCallback) {
        //no-op, internal use only
    }

    @Override
    public void updateCategory(@NonNull Category category, @NonNull UpdateCategoryCallback updateCategoryCallback) {
        //no-op, internal use only
    }

    @Override
    public void saveCategory(@NonNull Category category, @NonNull final SaveCategoryCallback saveCategoryCallback) {
        mCategoryLocalDataSource.saveCategory(category, new SaveCategoryCallback() {
            @Override
            public void onCategorySaved() {
                saveCategoryCallback.onCategorySaved();
            }

            @Override
            public void onError() {
                saveCategoryCallback.onError();
            }
        });
    }

    @Override
    public void getCategoryOfProduct(@NonNull String barcode, @NonNull GetCategoryOfProductCallback getCategoryOfProductCallback) {
        // TODO
    }

    @Override
    public void checkExistCategoryTag(@NonNull String barcode, @NonNull String categoryKey, @NonNull CheckExistCategoryTagCallback checkExistCategoryTagCallback) {
        //no-op, internal use only
    }

    @Override
    public void addCategoryTag(@NonNull CategoryTag categoryTag, @NonNull AddCategoryTagCallback addCategoryTagCallback) {
        //no-op, internal use only
    }

    @Override
    public void updateCategoryTag(@NonNull CategoryTag categoryTag, @NonNull UpdateCategoryTagCallback updateCategoryTagCallback) {
        //no-op, internal use only
    }

    @Override
    public void saveCategoryTag(@NonNull CategoryTag categoryTag, @NonNull final SaveCategoryTagCallback saveCategoryTagCallback) {
        mCategoryTagLocalDataSource.saveCategoryTag(categoryTag, new SaveCategoryTagCallback() {
            @Override
            public void onCategoryTagSaved() {
                saveCategoryTagCallback.onCategoryTagSaved();
            }

            @Override
            public void onError() {
                saveCategoryTagCallback.onError();
            }
        });
    }

    @Override
    public void getCountryCategory(@NonNull CountryCategory countryCategory, @NonNull GetCountryCategoryCallback getCountryCategoryCallback) {
        // TODO
    }

    @Override
    public void checkExistCountryCategory(@NonNull String categoryKey, @NonNull String countryKey, @NonNull CheckExistCountryCategoryCallback checkExistCountryCategoryCallback) {
        //no-op, internal use only
    }

    @Override
    public void addCountryCategory(@NonNull CountryCategory countryCategory, @NonNull AddCountryCategoryCallback addCountryCategoryCallback) {
        //no-op, internal use only
    }

    @Override
    public void updateCountryCategory(@NonNull CountryCategory countryCategory, @NonNull UpdateCountryCategoryCallback updateCountryCategoryCallback) {
        //no-op, internal use only
    }

    @Override
    public void saveCountryCategory(@NonNull CountryCategory countryCategory, @NonNull final SaveCountryCategoryCallback saveCountryCategoryCallback) {
        mCountryCategoryLocalDataSource.saveCountryCategory(countryCategory, new SaveCountryCategoryCallback() {
            @Override
            public void onCountryCategorySaved() {
                saveCountryCategoryCallback.onCountryCategorySaved();
            }

            @Override
            public void onError() {
                saveCountryCategoryCallback.onError();
            }
        });
    }

    @Override
    public void getCountryCategoryOfProduct(@NonNull String barcode, @NonNull GetCountryCategoryOfProductCallback getCountryCategoryOfProductCallback) {
        // TODO
    }

    @Override
    public void checkExistEvent(@NonNull String barcode, @NonNull CheckExistEventCallback checkExistEventCallback) {
        //no-op, internal use only
    }

    @Override
    public void addEvent(@NonNull Event event, @NonNull AddEventCallback addEventCallback) {
        //no-op, internal use only
    }

    @Override
    public void updateEvent(@NonNull Event event, @NonNull UpdateEventCallback updateEventCallback) {
        //no-op, internal use only
    }

    @Override
    public void saveEvent(@NonNull Event event, @NonNull final SaveEventCallback saveEventCallback) {
        mEventLocalDataSource.saveEvent(event, new SaveEventCallback() {
            @Override
            public void onEventSaved() {
                saveEventCallback.onEventSaved();
            }

            @Override
            public void onError() {
                saveEventCallback.onError();
            }
        });
    }

    @Override
    public void saveScan(@NonNull String barcode, @NonNull SaveScanCallback saveScanCallback) {
        // TODO
    }

    @Override
    public void refreshEventsOnError(@NonNull RefreshEventsOnErrorCallback refreshEventsOnErrorCallback) {
        // TODO
    }

    @Override
    public void getEventsOnError(@NonNull GetEventsOnErrorCallback getEventsOnErrorCallback) {
        // TODO
    }

    @Override
    public void getProduct(@NonNull String barcode, @NonNull final GetProductCallback getProductCallback) {
        mProductLocalDataSource.checkExistProduct(barcode, new CheckExistProductCallback() {
            @Override
            public void onProductExisted(long id) {
                //TODO dernier point d arret
            }

            @Override
            public void onProductNotExisted() {

            }
        });
    }

    @Override
    public void getProducts(@NonNull String categoryKey, @NonNull String countryKey, @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts, @NonNull Integer numberOfProducts, @NonNull GetProductsCallback getProductsCallback) {
        // TODO
    }

    @Override
    public void checkExistProduct(@NonNull String barcode, @NonNull CheckExistProductCallback checkExistProductCallback) {
        //no-op, internal use only
    }

    @Override
    public void addProduct(@NonNull Product product, @NonNull AddProductCallback addProductCallback) {
        //no-op, internal use only
    }

    @Override
    public void updateProduct(@NonNull Product product, @NonNull UpdateProductCallback updateProductCallback) {
        //no-op, internal use only
    }

    @Override
    public void saveProduct(@NonNull Product product, @NonNull final SaveProductCallback saveProductCallback) {
        mProductLocalDataSource.saveProduct(product, new SaveProductCallback() {
            @Override
            public void onProductSaved() {
                saveProductCallback.onProductSaved();
            }

            @Override
            public void onError() {
                saveProductCallback.onError();
            }
        });
    }

    @Override
    public void parseProduct(@NonNull String barcode, @NonNull ParseProductCallback parseProductCallback) {
        // TODO
    }

    @Override
    public void updateProductBookmark(@NonNull String barcode, @NonNull UpdateProductBookmarkCallback updateProductBookmarkCallback) {
        // TODO
    }

    @Override
    public void checkExistSuggestion(@NonNull String barcode, @NonNull String categoryKey, @NonNull String countryKey, @NonNull CheckExistSuggestionCallback checkExistSuggestionCallback) {
        //no-op, internal use only
    }

    @Override
    public void addSuggestion(@NonNull Suggestion suggestion, @NonNull AddSuggestionCallback addSuggestionCallback) {
        //no-op, internal use only
    }

    @Override
    public void updateSuggestion(@NonNull Suggestion suggestion, @NonNull UpdateSuggestionCallback updateSuggestionCallback) {
        //no-op, internal use only
    }

    @Override
    public void saveSuggestion(@NonNull Suggestion suggestion, @NonNull final SaveSuggestionCallback saveSuggestionCallback) {
        mSuggestionLocalDataSource.saveSuggestion(suggestion, new SaveSuggestionCallback() {
            @Override
            public void onSuggestionSaved() {
                saveSuggestionCallback.onSuggestionSaved();
            }

            @Override
            public void onError() {
                saveSuggestionCallback.onError();
            }
        });
    }
}
