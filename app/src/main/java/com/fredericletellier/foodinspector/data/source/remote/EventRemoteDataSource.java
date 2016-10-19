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
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.FoodInspector;
import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.remote.API.EndpointBaseUrl;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIEndpointInterface;
import com.fredericletellier.foodinspector.data.source.remote.model.Barcode;
import com.fredericletellier.foodinspector.data.source.remote.model.Product;
import com.fredericletellier.foodinspector.util.Connectivity;

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
public class EventRemoteDataSource implements EventDataSource {

    private static EventRemoteDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private EventRemoteDataSource() {
    }

    public static EventRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * Check the network connectivity
     * If connected, get products data associated to events
     * If data available, save product, update status code of event without refresh timestamp
     * If data not available, update status code of event without refresh timestamp
     */
    @Override
    public void getEvents(@NonNull List<Event> events, @Nullable GetEventsCallback callback){
        checkNotNull(events);

        Context c = FoodInspector.getContext();

        //No-op if no network connectivity
        if (!Connectivity.isConnected(c)){
            return;
        }

        for (Event event : events) {

            String productId = event.getProductId();

            String baseUrl = EndpointBaseUrl.getEndpointBaseUrlBarcode();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

            Call<Barcode> call = apiService.getResultOfBarcode(productId);

            call.enqueue(new Callback<Barcode>() {
                @Override
                public void onResponse(Call<Barcode> call, Response<Barcode> response) {

                    if (response.code() == 200) {

                        List<Product> products = response.body().getProducts();
                        Product product = products.get(0);

                        ContentValues valuesProduct = new ContentValues();
                        valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, product.getProduct_name());
                        valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_GENERIC_NAME, product.getGeneric_name());
                        valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_MAIN_BRAND, product.getBrands());
                        valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_QUANTITY, product.getQuantity());
                        valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_NUTRITION_GRADE, product.getNutrition_grades());
                        valuesProduct.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSABLE_CATEGORIES, product.getCategories_tags());

                        mContentResolver.insert(ProductPersistenceContract.ProductEntry.buildProductUri(), valuesProduct);

                        ContentValues valuesEvent = new ContentValues();
                        valuesEvent.put(EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS, Event.STATUS_OK);
                        valuesEvent.put(EventPersistenceContract.EventEntry.COLUMN_NAME_PRODUCT_ID, product.getId());

                        mContentResolver.update(
                                EventPersistenceContract.EventEntry.buildEventUri(),
                                valuesEvent,
                                EventPersistenceContract.EventEntry._ID + " LIKE ?",
                                // TODO Get the id of event for this callback
                                new String[]{product.getId()});

                    } else {

                        // TODO Get the id of event for this callback
                        // Need a custom callback
                        // http://stackoverflow.com/questions/28814283/receiving-custom-parameter-in-retrofit-callback

                    }
                }

                @Override
                public void onFailure(Call<Barcode> call, Throwable t) {
                    //no-op
                }
            });
        }
    }

    //TODO COMPLETE
    //###REMOTE
	//Si il y a du réseau
	//	Je recupere les informations du produit via l'API
	//	Si erreur lors de la récupération
	//		Creation / Mise à jour du code erreur et timestamp de l'evenement
	//	Si recupération ok
	//		J'inscris le produit en base
	//		Creation / Mise à jour du code produit et timestamp de l'evenement
	//		callback = ok
	//Si pas de réseau
	//	Creation / Mise à jour du code erreur et timestamp de l'evenement
    @Override
    public void addEvent(@NonNull String productId, @NonNull AddEventCallback callback){
        checkNotNull(productId);
        checkNotNull(callback);

    }

    @Override
    public void updateFavoriteFieldEvent(@NonNull String productId){
        checkNotNull(productId);
        //no-op in remote
    }

}