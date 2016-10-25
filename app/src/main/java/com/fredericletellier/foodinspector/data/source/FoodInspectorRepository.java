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
    public void checkExistCategory(@NonNull String categoryKey,
                                   @NonNull CheckExistCategoryCallback checkExistCategoryCallback) {

        mCategoryLocalDataSource.checkExistCategory(categoryKey, checkExistCategoryCallback);
    }

    @Override
    public void addCategory(@NonNull Category category,
                            @NonNull AddCategoryCallback addCategoryCallback) {

        mCategoryLocalDataSource.addCategory(category, addCategoryCallback);
    }

    @Override
    public void updateCategory(@NonNull Category category,
                               @NonNull UpdateCategoryCallback updateCategoryCallback) {

        mCategoryLocalDataSource.updateCategory(category, updateCategoryCallback);
    }

    @Override
    public void saveCategory(@NonNull Category category,
                             @NonNull final SaveCategoryCallback saveCategoryCallback) {

        mCategoryLocalDataSource.saveCategory(category, saveCategoryCallback);
    }

    @Override
    public void getCategoryTags(@NonNull String barcode,
                                @NonNull GetCategoryTagsCallback getCategoryTagsCallback) {
        mCategoryTagLocalDataSource.getCategoryTags(barcode, getCategoryTagsCallback);
    }

    @Override
    public void checkExistCategoryTag(@NonNull String barcode, @NonNull String categoryKey,
                                      @NonNull CheckExistCategoryTagCallback checkExistCategoryTagCallback) {

        mCategoryTagLocalDataSource.checkExistCategoryTag(barcode, categoryKey, checkExistCategoryTagCallback);
    }

    @Override
    public void addCategoryTag(@NonNull CategoryTag categoryTag,
                               @NonNull AddCategoryTagCallback addCategoryTagCallback) {

        mCategoryTagLocalDataSource.addCategoryTag(categoryTag, addCategoryTagCallback);
    }

    @Override
    public void updateCategoryTag(@NonNull CategoryTag categoryTag,
                                  @NonNull UpdateCategoryTagCallback updateCategoryTagCallback) {

        mCategoryTagLocalDataSource.updateCategoryTag(categoryTag, updateCategoryTagCallback);
    }

    @Override
    public void saveCategoryTag(@NonNull CategoryTag categoryTag,
                                @NonNull final SaveCategoryTagCallback saveCategoryTagCallback) {

        mCategoryTagLocalDataSource.saveCategoryTag(categoryTag, saveCategoryTagCallback);
    }

    @Override
    public void getCountryCategory(@NonNull CountryCategory countryCategory,
                                   @NonNull GetCountryCategoryCallback getCountryCategoryCallback) {
        // TODO
    }

    @Override
    public void checkExistCountryCategory(@NonNull String categoryKey, @NonNull String countryKey,
                                          @NonNull CheckExistCountryCategoryCallback checkExistCountryCategoryCallback) {

        mCountryCategoryLocalDataSource.checkExistCountryCategory(categoryKey, countryKey, checkExistCountryCategoryCallback);
    }

    @Override
    public void addCountryCategory(@NonNull CountryCategory countryCategory,
                                   @NonNull AddCountryCategoryCallback addCountryCategoryCallback) {

        mCountryCategoryLocalDataSource.addCountryCategory(countryCategory, addCountryCategoryCallback);
    }

    @Override
    public void updateCountryCategory(@NonNull CountryCategory countryCategory,
                                      @NonNull UpdateCountryCategoryCallback updateCountryCategoryCallback) {

        mCountryCategoryLocalDataSource.updateCountryCategory(countryCategory,updateCountryCategoryCallback);
    }

    @Override
    public void saveCountryCategory(@NonNull CountryCategory countryCategory,
                                    @NonNull final SaveCountryCategoryCallback saveCountryCategoryCallback) {

        mCountryCategoryLocalDataSource.saveCountryCategory(countryCategory, saveCountryCategoryCallback);
    }

    @Override
    public void getCountryCategoryOfProduct(@NonNull String barcode, @NonNull String countryKey,
                                            @NonNull GetCountryCategoryOfProductCallback getCountryCategoryOfProductCallback) {
        // TODO
    }

    @Override
    public void checkExistEvent(@NonNull String barcode,
                                @NonNull CheckExistEventCallback checkExistEventCallback) {

        mEventLocalDataSource.checkExistEvent(barcode, checkExistEventCallback);
    }

    @Override
    public void addEvent(@NonNull Event event,
                         @NonNull AddEventCallback addEventCallback) {

        mEventLocalDataSource.addEvent(event, addEventCallback);
    }

    @Override
    public void updateEvent(@NonNull Event event,
                            @NonNull UpdateEventCallback updateEventCallback) {

        mEventLocalDataSource.updateEvent(event, updateEventCallback);
    }

    @Override
    public void saveEvent(@NonNull Event event,
                          @NonNull final SaveEventCallback saveEventCallback) {

        mEventLocalDataSource.saveEvent(event, saveEventCallback);
    }

    @Override
    public void saveScan(@NonNull String barcode,
                         @NonNull SaveScanCallback saveScanCallback) {
        // TODO
    }

    @Override
    public void refreshEventsOnError(@NonNull RefreshEventsOnErrorCallback refreshEventsOnErrorCallback) {
        // TODO
    }

    @Override
    public void getEventsOnError(@NonNull GetEventsOnErrorCallback getEventsOnErrorCallback) {

        mEventLocalDataSource.getEventsOnError(getEventsOnErrorCallback);
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
                            public void onProductSaved() {
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
    public void getProducts(@NonNull String categoryKey, @NonNull String countryKey,
                            @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts,
                            @NonNull Integer numberOfProducts, @NonNull GetProductsCallback getProductsCallback) {
        // TODO
    }

    @Override
    public void checkExistProduct(@NonNull String barcode,
                                  @NonNull CheckExistProductCallback checkExistProductCallback) {

        mProductLocalDataSource.checkExistProduct(barcode, checkExistProductCallback);
    }

    @Override
    public void addProduct(@NonNull Product product,
                           @NonNull AddProductCallback addProductCallback) {

        mProductLocalDataSource.addProduct(product, addProductCallback);
    }

    @Override
    public void updateProduct(@NonNull Product product,
                              @NonNull UpdateProductCallback updateProductCallback) {

        mProductLocalDataSource.updateProduct(product, updateProductCallback);
    }

    @Override
    public void saveProduct(@NonNull Product product,
                            @NonNull final SaveProductCallback saveProductCallback) {

        mProductLocalDataSource.saveProduct(product, saveProductCallback);
    }

    @Override
    public void parseProduct(@NonNull String barcode,
                             @NonNull final ParseProductCallback parseProductCallback) {

        mProductLocalDataSource.parseProduct(barcode, new ParseProductCallback() {
            @Override
            public void onProductParsed() {
                parseProductCallback.onProductParsed();
            }

            @Override
            public void onProductMustBeParsed(String parsableCategories) {
                String[] parsedCategories = parsableCategories.split(",");

                for (String parsedCategory : parsedCategories){

                    // TODO dernier point d'arret
                    // Appeler getCategory
                    //     qui appelle checkCategory
                    //         appelle getNameCategory
                    //         appelle saveCategory
                    //     qui appelle saveCategoryTag

                }

            }

            @Override
            public void onError() {
                parseProductCallback.onError();
            }
        });
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

        mSuggestionLocalDataSource.checkExistSuggestion(barcode, categoryKey, countryKey, checkExistSuggestionCallback);
    }

    @Override
    public void addSuggestion(@NonNull Suggestion suggestion,
                              @NonNull AddSuggestionCallback addSuggestionCallback) {

        mSuggestionLocalDataSource.addSuggestion(suggestion, addSuggestionCallback);
    }

    @Override
    public void updateSuggestion(@NonNull Suggestion suggestion,
                                 @NonNull UpdateSuggestionCallback updateSuggestionCallback) {

        mSuggestionLocalDataSource.updateSuggestion(suggestion, updateSuggestionCallback);
    }

    @Override
    public void saveSuggestion(@NonNull Suggestion suggestion,
                               @NonNull final SaveSuggestionCallback saveSuggestionCallback) {

        mSuggestionLocalDataSource.saveSuggestion(suggestion, saveSuggestionCallback);
    }
}
