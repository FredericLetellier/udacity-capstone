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

import com.fredericletellier.foodinspector.data.source.remote.model.Search;
import com.google.android.gms.vision.barcode.Barcode;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenFoodFactsAPIEndpointInterface {

    @GET("/api/v0/product/{idproduct}.json")
    void getResultOfBarcode(
            @Path("idproduct") String idproduct,
            Callback<Barcode> callback
    );

    @GET("/cgi/search.pl?action=process&tagtype_0=categories&tag_contains_0=contains&tag_0={idCategory}&tagtype_1=nutrition_grades&tag_contains_1={tagNutritionGrade}&tag_1={nutritionGrade}&sort_by=unique_scans_n&page_size={pageSize}&page={page}&json=1")
    void getResultOfSearch(
            @Path("idCategory") String idCategory,
            @Path("tagNutritionGrade") String tagNutritionGrade,
            @Path("nutritionGrade") String nutritionGrade,
            @Path("pageSize") String pageSize,
            @Path("page") String page,
            Callback<Search> callback
    );

}
