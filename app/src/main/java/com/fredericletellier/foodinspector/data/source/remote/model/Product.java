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

package com.fredericletellier.foodinspector.data.source.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("generic_name")
    @Expose
    private String generic_name;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("nutrition_grades")
    @Expose
    private String nutrition_grades;
    @SerializedName("categories_tags")
    @Expose
    private List<String> categories_tags = new ArrayList<String>();
    @SerializedName("image_front_url")
    @Expose
    private String image_front_url;
    @SerializedName("image_url")
    @Expose
    private String image_url;
    @SerializedName("image_small_url")
    @Expose
    private String image_small_url;
    @SerializedName("image_thumb_url")
    @Expose
    private String image_thumb_url;


    /**
     * 
     * @return
     *     The image_front_url
     */
    public String getImage_front_url() {
        return image_front_url;
    }

    /**
     * 
     * @return
     *     The generic_name
     */
    public String getGeneric_name() {
        return generic_name;
    }

    /**
     * 
     * @return
     *     The categories_tags
     */
    public List<String> getCategories_tags() {
        return categories_tags;
    }

    /**
     * 
     * @return
     *     The image_small_url
     */
    public String getImage_small_url() {
        return image_small_url;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * 
     * @return
     *     The image_url
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     * 
     * @return
     *     The image_thumb_url
     */
    public String getImage_thumb_url() {
        return image_thumb_url;
    }

    /**
     * 
     * @return
     *     The nutrition_grades
     */
    public String getNutrition_grades() {
        return nutrition_grades;
    }

    /**
     * 
     * @return
     *     The product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }
}
