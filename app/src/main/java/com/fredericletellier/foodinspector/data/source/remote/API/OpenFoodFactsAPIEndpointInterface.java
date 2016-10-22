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

package com.fredericletellier.foodinspector.data.source.remote.API;

import com.fredericletellier.foodinspector.data.ProductBarcode;
import com.fredericletellier.foodinspector.data.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenFoodFactsAPIEndpointInterface {

    public static final String ENDPOINT_BARCODE = "http://world.openfoodfacts.org/api/v0/product";
    public static final String ENDPOINT_SEARCH = "http://world.openfoodfacts.org/cgi";

    //Example : http://world.openfoodfacts.org/api/v0/product/3046920029759.json
    @GET("{barcode}.json")
    Call<ProductBarcode> getProduct(
            @Path("barcode") String barcode
    );


    //Example : http://world.openfoodfacts.org/cgi/search.pl?action=process&tagtype_0=categories&tag_contains_0=contains&tag_0=cheese&tagtype_1=countries&tag_contains_1=contains&tag_1=france&tagtype_2=nutrition_grades&tag_contains_2=contains&tag_2=a&sort_by=unique_scans_n&page_size=20&page=1&json=1
    @GET("search.pl?action=process&tagtype_0=categories&tag_contains_0=contains&tagtype_1=countries&tag_contains_1=contains&tagtype_2=nutrition_grades&tag_contains_2=contains&sort_by=unique_scans_n&json=1")
    Call<Search> getProducts(
            @Query("tag_0") String categoryKey,
            @Query("tag_1") String countryKey,
            @Query("tag_2") String nutritionGrade,
            @Query("page_size") String pageSize,
            @Query("page") String page
    );


    //Example : http://world.openfoodfacts.org/cgi/search.pl?action=process&tagtype_0=categories&tag_contains_0=contains&tag_0=cheese&tagtype_1=countries&tag_contains_1=contains&tag_1=france&tagtype_2=nutrition_grades&tag_contains_2=does_not_contain&tag_2=unknown&sort_by=unique_scans_n&page_size=5&page=1&json=1
    @GET("search.pl?action=process&tagtype_0=categories&tag_contains_0=contains&tagtype_1=countries&tag_contains_1=contains&tagtype_2=nutrition_grades&tag_contains_2=does_not_contain&tag_2=unknown&sort_by=unique_scans_n&page_size=5&page=1&json=1")
    Call<Search> getCountryCategory(
            @Query("tag_0") String categoryKey,
            @Query("tag_1") String countryKey
    );

}
