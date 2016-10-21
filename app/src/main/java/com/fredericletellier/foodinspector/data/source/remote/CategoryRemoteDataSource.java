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
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.source.CategoryDataSource;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductsInCategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.remote.API.EndpointBaseUrl;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIEndpointInterface;
import com.fredericletellier.foodinspector.data.source.remote.model.Product;
import com.fredericletellier.foodinspector.data.source.remote.model.Search;

import java.util.ArrayList;
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
public class CategoryRemoteDataSource implements CategoryDataSource {

    public static final int PAGE_SIZE = 5;
    public static final int PAGE = 1;
    public static final String TAG_NUTRITION_GRADE = "does_not_contain";
    public static final String NUTRITION_GRADE = "unknown";

    private static CategoryRemoteDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private CategoryRemoteDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static CategoryRemoteDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryRemoteDataSource(contentResolver);
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    //###REMOTE
	//Pour chaque categorie de la liste
	//	 Je recupere l'information via l'API sur le nombre de produits de cette catégorie avec ce code pays et ayant une note nutritionnelle
	//	 J'ajoute en base une nouvelle entrée lié à la catégorie avec son code pays et si elle contient plus d'un produit
    @Override
    public void getCategories(@NonNull String productId, @Nullable ArrayList<String> categories, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);

        String baseUrl = EndpointBaseUrl.getEndpointBaseUrlSearch();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

        for (String categorie : categories){

            Call<Search> call = apiService.getResultOfSearch(
                    categorie,
                    TAG_NUTRITION_GRADE,
                    NUTRITION_GRADE,
                    String.valueOf(PAGE_SIZE),
                    String.valueOf(PAGE));

            call.enqueue(new Callback<Search>() {
                @Override
                public void onResponse(Call<Search> call, Response<Search> response) {
                    Search search = response.body();

                    //TODO check the response = 200

                    //TODO Get informations of category to add

                    ContentValues valuesCategory = new ContentValues();
                    valuesCategory.put(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_COUNTRY_ID, null);
                    valuesCategory.put(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_WORLD_CATEGORY_ID, null);

                    //TODO Check number of result
                    valuesCategory.put(CategoryPersistenceContract.CategoryEntry.COLUMN_HAVE_DATA, null);

                    mContentResolver.insert(CategoryPersistenceContract.CategoryEntry.buildCategoryUri(), valuesCategory);
                }

                @Override
                public void onFailure(Call<Search> call, Throwable t) {
                    //no-op
                }
            });

        }

    }

}
