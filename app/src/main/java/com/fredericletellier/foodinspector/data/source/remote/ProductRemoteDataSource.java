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

import android.support.annotation.NonNull;
import android.util.Log;

import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.ProductBarcode;
import com.fredericletellier.foodinspector.data.Search;
import com.fredericletellier.foodinspector.data.source.ProductDataSource;
import com.fredericletellier.foodinspector.data.source.remote.API.APIError;
import com.fredericletellier.foodinspector.data.source.remote.API.ErrorUtils;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIEndpointInterface;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a remote db
 */
public class ProductRemoteDataSource implements ProductDataSource {

    private static final String TAG = ProductRemoteDataSource.class.getName();

    private static ProductRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private ProductRemoteDataSource() {
    }

    public static ProductRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * Gets the product from remote data source
     * <p/>
     * Note: {@link GetProductCallback#onError(Throwable)} is fired if remote data sources fail to
     * get the data (HTTP error, IOException, IllegalStateException, ...)
     */
    @Override
    public void getProduct(@NonNull String barcode, @NonNull final GetProductCallback getProductCallback) {
        Log.d(TAG, "getProduct");

        checkNotNull(barcode);
        checkNotNull(getProductCallback);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenFoodFactsAPIEndpointInterface.ENDPOINT_BARCODE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

        Call<ProductBarcode> call = apiService.getProduct(barcode);

        call.enqueue(new Callback<ProductBarcode>() {
            @Override
            public void onResponse(Call<ProductBarcode> call, Response<ProductBarcode> response) {
                Log.d(TAG, "getProduct.onResponse");

                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getProduct.response.isSuccessful");

                    ProductBarcode mProductBarcode = response.body();
                    Product mProduct = mProductBarcode.getProduct();
                    getProductCallback.onProductLoaded(mProduct);
                } else {
                    Log.d(TAG, "getProduct.response.isOnError");

                    APIError e = ErrorUtils.parseError(response);
                    Throwable t = new Throwable(e.message(), new Throwable(String.valueOf(e.status())));
                    getProductCallback.onError(t);
                }
            }

            @Override
            public void onFailure(Call<ProductBarcode> call, Throwable t) {
                Log.d(TAG, "getProduct.onFailure" + t.toString());

                getProductCallback.onError(t);
            }
        });
    }

    @Override
    public void getProducts(@NonNull String categoryKey,
                            @NonNull String nutritionGradeValue, @NonNull final GetProductsCallback getProductsCallback) {
        checkNotNull(categoryKey);
        checkNotNull(nutritionGradeValue);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenFoodFactsAPIEndpointInterface.ENDPOINT_SEARCH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

        Call<Search> call = apiService.getProducts(categoryKey, nutritionGradeValue);

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body().getProducts();
                    getProductsCallback.onProductsLoaded(products);
                } else {
                    APIError e = ErrorUtils.parseError(response);
                    Throwable t = new Throwable(e.message(), new Throwable(String.valueOf(e.status())));
                    getProductsCallback.onError(t);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                getProductsCallback.onError(t);
            }
        });
    }


    private OkHttpClient getOkHttpClient() {
        try {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}