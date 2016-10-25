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

import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.Search;
import com.fredericletellier.foodinspector.data.source.CountryCategoryDataSource;
import com.fredericletellier.foodinspector.data.source.remote.API.APIError;
import com.fredericletellier.foodinspector.data.source.remote.API.ErrorUtils;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIEndpointInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

public class CountryCategoryRemoteDataSource implements CountryCategoryDataSource{

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
    public void getCountryCategory(@NonNull CountryCategory countryCategory,
                                   @NonNull final GetCountryCategoryCallback getCountryCategoryCallback) {
        checkNotNull(countryCategory);
        checkNotNull(getCountryCategoryCallback);

        final CountryCategory mCountryCategory = countryCategory;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenFoodFactsAPIEndpointInterface.ENDPOINT_SEARCH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

        Call<Search> call = apiService.getCountryCategory(
                mCountryCategory.getCategoryKey(),
                mCountryCategory.getCountryKey());

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (response.isSuccessful()) {
                    Search search = response.body();
                    mCountryCategory.setSumOfProducts(search.getCount());
                    getCountryCategoryCallback.onCountryCategoryLoaded(mCountryCategory);
                } else {
                    APIError e = ErrorUtils.parseError(response);
                    Throwable t = new Throwable(e.message(), new Throwable(String.valueOf(e.status())));
                    getCountryCategoryCallback.onError(t);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {

                getCountryCategoryCallback.onError(t);

                // TODO Optimize handling error
                //if (t instanceof IOException) {
                //    errorType. = "Network problem (socket timeout, unknown host, etc.)";
                //    errorDesc = String.valueOf(t.getCause());
                //}
                //else if (t instanceof IllegalStateException) {
                //    errorType = "ConversionError";
                //    errorDesc = String.valueOf(t.getCause());
                //} else {
                //    errorType = "Other Error";
                //    errorDesc = String.valueOf(t.getLocalizedMessage());
                //}
            }
        });
    }

    @Override
    public void checkExistCountryCategory(@NonNull String categoryKey, @NonNull String countryKey, @NonNull CheckExistCountryCategoryCallback checkExistCountryCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void addCountryCategory(@NonNull CountryCategory countryCategory,
                                   @NonNull AddCountryCategoryCallback addCountryCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void updateCountryCategory(@NonNull CountryCategory countryCategory,
                                      @NonNull UpdateCountryCategoryCallback updateCountryCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void saveCountryCategory(@NonNull CountryCategory countryCategory,
                                    @NonNull SaveCountryCategoryCallback saveCountryCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void getCountryCategoryOfProduct(@NonNull String barcode, @NonNull String countryKey,
                                            @NonNull GetCountryCategoryOfProductCallback getCountryCategoryOfProductCallback) {
        //no-op in remote
    }
}
