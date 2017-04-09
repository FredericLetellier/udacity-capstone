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
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIClient;
import com.fredericletellier.foodinspector.data.source.remote.API.ProductNotExistException;
import com.fredericletellier.foodinspector.data.source.remote.API.ServerUnreachableException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        OpenFoodFactsAPIClient openFoodFactsAPIClient = new OpenFoodFactsAPIClient(OpenFoodFactsAPIClient.ENDPOINT_BARCODE);
        Call<ProductBarcode> call = openFoodFactsAPIClient.getProduct(barcode);

        call.enqueue(new Callback<ProductBarcode>() {
            @Override
            public void onResponse(Call<ProductBarcode> call, Response<ProductBarcode> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    ServerUnreachableException e = new ServerUnreachableException();
                    Log.w(TAG, e);
                    getProductCallback.onError(e);
                    return;
                }

                ProductBarcode productBarcode = response.body();

                if (productBarcode.getStatus() != 1) {
                    ProductNotExistException e = new ProductNotExistException();
                    Log.w(TAG, e);
                    getProductCallback.onError(e);
                    return;
                }

                Product product = productBarcode.getProduct();
                getProductCallback.onProductLoaded(product);
            }

            @Override
            public void onFailure(Call<ProductBarcode> call, Throwable t) {
                ServerUnreachableException e = new ServerUnreachableException();
                Log.w(TAG, e);
                getProductCallback.onError(e);
            }
        });
    }

    @Override
    public void getProducts(@NonNull String categoryKey,
                            @NonNull String nutritionGradeValue, @NonNull final GetProductsCallback getProductsCallback) {
        checkNotNull(categoryKey);
        checkNotNull(nutritionGradeValue);

        OpenFoodFactsAPIClient openFoodFactsAPIClient = new OpenFoodFactsAPIClient(OpenFoodFactsAPIClient.ENDPOINT_SEARCH);
        Call<Search> call = openFoodFactsAPIClient.getProducts(categoryKey, nutritionGradeValue);

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    ServerUnreachableException e = new ServerUnreachableException();
                    Log.w(TAG, e);
                    getProductsCallback.onError(e);
                    return;
                }

                Search search = response.body();

                if (search.getCount() == 0) {
                    ProductNotExistException e = new ProductNotExistException();
                    Log.w(TAG, e);
                    getProductsCallback.onError(e);
                    return;
                }

                List<Product> products = search.getProducts();
                getProductsCallback.onProductsLoaded(products);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                ProductNotExistException e = new ProductNotExistException();
                Log.w(TAG, e);
                getProductsCallback.onError(e);
            }
        });
    }
}