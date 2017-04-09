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

import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.Search;
import com.fredericletellier.foodinspector.data.source.CountryCategoryDataSource;
import com.fredericletellier.foodinspector.data.source.remote.API.CountryCategoryNotExistException;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIClient;
import com.fredericletellier.foodinspector.data.source.remote.API.ServerUnreachableException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class CountryCategoryRemoteDataSource implements CountryCategoryDataSource {

    private static final String TAG = CountryCategoryRemoteDataSource.class.getName();

    private static CountryCategoryRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private CountryCategoryRemoteDataSource() {
    }

    public static CountryCategoryRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CountryCategoryRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * Gets the sumOfProducts from remote data source to complete CountryCategory data
     * <p/>
     * Note: {@link GetCountryCategoryCallback#onError(Throwable)} is fired if remote data sources fail to
     * get the data (HTTP error, IOException, IllegalStateException, ...)
     */
    @Override
    public void getCountryCategory(@NonNull final String categoryKey, @NonNull final String countryKey,
                                   @NonNull final GetCountryCategoryCallback getCountryCategoryCallback) {
        checkNotNull(categoryKey);
        checkNotNull(countryKey);
        checkNotNull(getCountryCategoryCallback);

        OpenFoodFactsAPIClient openFoodFactsAPIClient = new OpenFoodFactsAPIClient(OpenFoodFactsAPIClient.ENDPOINT_SEARCH);
        Call<Search> call = openFoodFactsAPIClient.getCountryCategory(categoryKey, countryKey);

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    ServerUnreachableException e = new ServerUnreachableException();
                    Log.w(TAG, e);
                    getCountryCategoryCallback.onError(e);
                    return;
                }

                Search search = response.body();

                if (search.getCount() == 0) {
                    CountryCategoryNotExistException e = new CountryCategoryNotExistException();
                    Log.w(TAG, e);
                    getCountryCategoryCallback.onError(e);
                    return;
                }

                int sumOfProducts = search.getCount();
                CountryCategory countryCategory = new CountryCategory(categoryKey, countryKey, sumOfProducts);
                getCountryCategoryCallback.onCountryCategoryLoaded(countryCategory);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                ServerUnreachableException e = new ServerUnreachableException();
                Log.w(TAG, e);
                getCountryCategoryCallback.onError(e);
            }
        });
    }
}
