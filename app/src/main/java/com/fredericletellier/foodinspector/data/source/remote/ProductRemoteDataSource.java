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

package com.fredericletellier.foodinspector.data.source.remote;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.source.ProductDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductsInCategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.remote.API.EndpointBaseUrl;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIEndpointInterface;
import com.fredericletellier.foodinspector.data.source.remote.model.Product;
import com.fredericletellier.foodinspector.data.source.remote.model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a remote db
 */
public class ProductRemoteDataSource implements ProductDataSource {

    public static final int PAGE_SIZE = 20;
    public static final String TAG_NUTRITION_GRADE = "contain";

    private static ProductRemoteDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private ProductRemoteDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static ProductRemoteDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new ProductRemoteDataSource(contentResolver);
        }
        return INSTANCE;
    }

    /**
     * Get the limited list of products with an offset, for a specific categoryId and specific nutritionGradeValue
     */
    @Override
    public void getXProductsInCategory(@NonNull final String categoryId, @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts, @NonNull GetXProductsInCategoryCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(nutritionGradeValue);
        checkNotNull(offsetProducts);
        checkNotNull(callback);

        int page;
        if (offsetProducts == 0){
            page = 1;
        }else{
            page = offsetProducts / PAGE_SIZE;
        }

        String baseUrl = EndpointBaseUrl.getEndpointBaseUrlSearch();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

        Call<Search> call = apiService.getResultOfSearch(
                categoryId,
                TAG_NUTRITION_GRADE,
                nutritionGradeValue,
                String.valueOf(PAGE_SIZE),
                String.valueOf(page));

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                List<Product> products = response.body().getProducts();
                for (Product product : products){

                    ContentValues valuesProduct = new ContentValues();
                    valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, product.getProduct_name());
                    valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_GENERIC_NAME, product.getGeneric_name());
                    valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_MAIN_BRAND, product.getBrands());
                    valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_QUANTITY, product.getQuantity());
                    valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_NUTRITION_GRADE, product.getNutrition_grades());
                    valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSABLE_CATEGORIES, product.getCategories_tags());

                    mContentResolver.insert(ProductPersistenceContract.ProductEntry.buildProductUri(), valuesProduct);

                    ContentValues valuesProductsInCategory = new ContentValues();
                    valuesProduct.put(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.COLUMN_NAME_CATEGORY_ID, categoryId);
                    valuesProduct.put(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.COLUMN_NAME_PRODUCT_ID, product.getId());

                    mContentResolver.insert(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.buildProductsInCategoryUri(), valuesProductsInCategory);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                //no-op
            }
        });

    }
}