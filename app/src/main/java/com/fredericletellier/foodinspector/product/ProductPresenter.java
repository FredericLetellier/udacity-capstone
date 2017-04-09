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

package com.fredericletellier.foodinspector.product;

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.source.FoodInspectorRepository;
import com.fredericletellier.foodinspector.data.source.ProductDataSource;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductPresenter implements ProductContract.Presenter {

    public final static int TASK_LOADER = 2;

    @NonNull
    private String mProductBarcode;
    @NonNull
    private final FoodInspectorRepository mFoodInspectorRepository;
    @NonNull
    private ProductActivity mProductActivity;
    @NonNull
    private ProductContract.ProductView mProductView;
    @NonNull
    private ProductContract.CategoryView mCategoryView;
    @NonNull
    private ProductContract.SuggestionsView mSuggestionsViewA;
    @NonNull
    private ProductContract.SuggestionsView mSuggestionsViewB;
    @NonNull
    private ProductContract.SuggestionsView mSuggestionsViewC;
    @NonNull
    private ProductContract.SuggestionsView mSuggestionsViewD;
    @NonNull
    private ProductContract.SuggestionsView mSuggestionsViewE;

    public ProductPresenter(@NonNull String productBarcode,
                            @NonNull FoodInspectorRepository foodInspectorRepository,
                            @NonNull ProductActivity productActivity,
                            @NonNull ProductContract.ProductView productView,
                            @NonNull ProductContract.CategoryView categoryView,
                            @NonNull ProductContract.SuggestionsView suggestionsViewA,
                            @NonNull ProductContract.SuggestionsView suggestionsViewB,
                            @NonNull ProductContract.SuggestionsView suggestionsViewC,
                            @NonNull ProductContract.SuggestionsView suggestionsViewD,
                            @NonNull ProductContract.SuggestionsView suggestionsViewE) {
        mProductBarcode = checkNotNull(productBarcode, "productBarcode cannot be null!");
        mFoodInspectorRepository = checkNotNull(foodInspectorRepository, "foodInspectorRepository cannot be null!");
        mProductActivity = checkNotNull(productActivity, "productActivity cannot be null!");
        mProductView = checkNotNull(productView, "productView cannot be null!");
        mCategoryView = checkNotNull(categoryView, "categoryView cannot be null!");
        mSuggestionsViewA = checkNotNull(suggestionsViewA, "suggestionsViewA cannot be null!");
        mSuggestionsViewB = checkNotNull(suggestionsViewB, "suggestionsViewB cannot be null!");
        mSuggestionsViewC = checkNotNull(suggestionsViewC, "suggestionsViewC cannot be null!");
        mSuggestionsViewD = checkNotNull(suggestionsViewD, "suggestionsViewD cannot be null!");
        mSuggestionsViewE = checkNotNull(suggestionsViewE, "suggestionsViewE cannot be null!");

        mProductView.setPresenter(this);
        mCategoryView.setPresenter(this);
        mSuggestionsViewA.setPresenter(this);
        mSuggestionsViewB.setPresenter(this);
        mSuggestionsViewC.setPresenter(this);
        mSuggestionsViewD.setPresenter(this);
        mSuggestionsViewE.setPresenter(this);
    }

    @Override
    public void start() {
        //no-op
    }



    @Override
    public void startProduct() {
        loadProduct();
    }

    @Override
    public void startCountryCategory() {
        mProductActivity.hideCategoryView();
    }

    @Override
    public void startSuggestion(String rank) {
        switch (rank){
            case ProductActivity.SUGGESTIONS_RANK_A:
                mProductActivity.hideSuggestionsViewA();
                break;
            case ProductActivity.SUGGESTIONS_RANK_B:
                mProductActivity.hideSuggestionsViewB();
                break;
            case ProductActivity.SUGGESTIONS_RANK_C:
                mProductActivity.hideSuggestionsViewC();
                break;
            case ProductActivity.SUGGESTIONS_RANK_D:
                mProductActivity.hideSuggestionsViewD();
                break;
            case ProductActivity.SUGGESTIONS_RANK_E:
                mProductActivity.hideSuggestionsViewE();
                break;
            default:
                break;
        }
    }

    @Override
    public void chooseNewCategory(String parsableCategory){
        loadSuggestions(parsableCategory, ProductActivity.SUGGESTIONS_RANK_A);
        loadSuggestions(parsableCategory, ProductActivity.SUGGESTIONS_RANK_B);
        loadSuggestions(parsableCategory, ProductActivity.SUGGESTIONS_RANK_C);
        loadSuggestions(parsableCategory, ProductActivity.SUGGESTIONS_RANK_D);
        loadSuggestions(parsableCategory, ProductActivity.SUGGESTIONS_RANK_E);
    }

    private void loadProduct(){
        mProductView.setLoadingIndicator(true);

        mFoodInspectorRepository.getProduct(mProductBarcode, new ProductDataSource.GetProductCallback() {
            @Override
            public void onProductLoaded(Product product) {
                mProductView.setLoadingIndicator(false);
                mProductView.showProduct(product);
                loadCountryCategories(product.getmParsableCategories());
            }

            @Override
            public void onError(Throwable throwable) {
                mProductView.setLoadingIndicator(false);
                mProductView.showError();
            }
        });
    }

    private void loadCountryCategories(List<String> parsableCategories){

        mProductActivity.showCategoryView();
        mCategoryView.setLoadingIndicator(true);

        List<Category> categoryList = new ArrayList<>();

        if (parsableCategories != null) {
            //parse category
            for (String parsableCategory: parsableCategories) {
                String segments[] = parsableCategory.split(":");
                String mShortParsableCategory;
                if (segments.length > 0) {
                    mShortParsableCategory = segments[1];
                } else {
                    mShortParsableCategory = segments[0];
                }

                mShortParsableCategory = mShortParsableCategory.replace("-", " ");
                String categoryName = mShortParsableCategory.substring(0, 1).toUpperCase() + mShortParsableCategory.substring(1).toLowerCase();

                Category category = new Category(parsableCategory, categoryName);
                categoryList.add(category);
            }

            mCategoryView.setLoadingIndicator(false);
            mCategoryView.showCategories(categoryList);

        } else {
            mCategoryView.setLoadingIndicator(false);
            mProductActivity.hideCategoryView();
        }
    }

    private void loadSuggestions(String parsableCategory, final String rank){
        switch (rank){
            case ProductActivity.SUGGESTIONS_RANK_A:
                mProductActivity.showSuggestionsViewA();
                mSuggestionsViewA.setLoadingIndicator(true);
                break;
            case ProductActivity.SUGGESTIONS_RANK_B:
                mProductActivity.showSuggestionsViewB();
                mSuggestionsViewB.setLoadingIndicator(true);
                break;
            case ProductActivity.SUGGESTIONS_RANK_C:
                mProductActivity.showSuggestionsViewC();
                mSuggestionsViewC.setLoadingIndicator(true);
                break;
            case ProductActivity.SUGGESTIONS_RANK_D:
                mProductActivity.showSuggestionsViewD();
                mSuggestionsViewD.setLoadingIndicator(true);
                break;
            case ProductActivity.SUGGESTIONS_RANK_E:
                mProductActivity.showSuggestionsViewE();
                mSuggestionsViewE.setLoadingIndicator(true);
                break;
            default:
                break;
        }

        mFoodInspectorRepository.getProducts(parsableCategory, rank, new ProductDataSource.GetProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                switch (rank){
                    case ProductActivity.SUGGESTIONS_RANK_A:
                        mSuggestionsViewA.setLoadingIndicator(false);
                        mSuggestionsViewA.showProducts(products);
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_B:
                        mSuggestionsViewB.setLoadingIndicator(false);
                        mSuggestionsViewB.showProducts(products);
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_C:
                        mSuggestionsViewC.setLoadingIndicator(false);
                        mSuggestionsViewC.showProducts(products);
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_D:
                        mSuggestionsViewD.setLoadingIndicator(false);
                        mSuggestionsViewD.showProducts(products);
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_E:
                        mSuggestionsViewE.setLoadingIndicator(false);
                        mSuggestionsViewE.showProducts(products);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                switch (rank){
                    case ProductActivity.SUGGESTIONS_RANK_A:
                        mSuggestionsViewA.setLoadingIndicator(false);
                        mProductActivity.hideSuggestionsViewA();
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_B:
                        mSuggestionsViewB.setLoadingIndicator(false);
                        mProductActivity.hideSuggestionsViewB();
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_C:
                        mSuggestionsViewC.setLoadingIndicator(false);
                        mProductActivity.hideSuggestionsViewC();
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_D:
                        mSuggestionsViewD.setLoadingIndicator(false);
                        mProductActivity.hideSuggestionsViewD();
                        break;
                    case ProductActivity.SUGGESTIONS_RANK_E:
                        mSuggestionsViewE.setLoadingIndicator(false);
                        mProductActivity.hideSuggestionsViewE();
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
