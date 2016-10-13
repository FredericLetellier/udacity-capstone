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

    @SerializedName("image_front_url")
    @Expose
    private String imageFrontUrl;
    @SerializedName("generic_name_fr")
    @Expose
    private String genericNameFr;
    @SerializedName("countries")
    @Expose
    private String countries;
    @SerializedName("countries_hierarchy")
    @Expose
    private List<String> countriesHierarchy = new ArrayList<String>();
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("languages_hierarchy")
    @Expose
    private List<String> languagesHierarchy = new ArrayList<String>();
    @SerializedName("generic_name")
    @Expose
    private String genericName;
    @SerializedName("categories_tags")
    @Expose
    private List<String> categoriesTags = new ArrayList<String>();
    @SerializedName("image_small_url")
    @Expose
    private String imageSmallUrl;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("image_thumb_url")
    @Expose
    private String imageThumbUrl;
    @SerializedName("nutrition_grades")
    @Expose
    private String nutritionGrades;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_name_fr")
    @Expose
    private String productNameFr;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     *     The imageFrontUrl
     */
    public String getImageFrontUrl() {
        return imageFrontUrl;
    }

    /**
     *
     * @param imageFrontUrl
     *     The image_front_url
     */
    public void setImageFrontUrl(String imageFrontUrl) {
        this.imageFrontUrl = imageFrontUrl;
    }

    /**
     *
     * @return
     *     The genericNameFr
     */
    public String getGenericNameFr() {
        return genericNameFr;
    }

    /**
     *
     * @param genericNameFr
     *     The generic_name_fr
     */
    public void setGenericNameFr(String genericNameFr) {
        this.genericNameFr = genericNameFr;
    }

    /**
     *
     * @return
     *     The countries
     */
    public String getCountries() {
        return countries;
    }

    /**
     *
     * @param countries
     *     The countries
     */
    public void setCountries(String countries) {
        this.countries = countries;
    }

    /**
     *
     * @return
     *     The countriesHierarchy
     */
    public List<String> getCountriesHierarchy() {
        return countriesHierarchy;
    }

    /**
     *
     * @param countriesHierarchy
     *     The countries_hierarchy
     */
    public void setCountriesHierarchy(List<String> countriesHierarchy) {
        this.countriesHierarchy = countriesHierarchy;
    }

    /**
     *
     * @return
     *     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang
     *     The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return
     *     The languagesHierarchy
     */
    public List<String> getLanguagesHierarchy() {
        return languagesHierarchy;
    }

    /**
     *
     * @param languagesHierarchy
     *     The languages_hierarchy
     */
    public void setLanguagesHierarchy(List<String> languagesHierarchy) {
        this.languagesHierarchy = languagesHierarchy;
    }

    /**
     *
     * @return
     *     The genericName
     */
    public String getGenericName() {
        return genericName;
    }

    /**
     *
     * @param genericName
     *     The generic_name
     */
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    /**
     *
     * @return
     *     The categoriesTags
     */
    public List<String> getCategoriesTags() {
        return categoriesTags;
    }

    /**
     *
     * @param categoriesTags
     *     The categories_tags
     */
    public void setCategoriesTags(List<String> categoriesTags) {
        this.categoriesTags = categoriesTags;
    }

    /**
     *
     * @return
     *     The imageSmallUrl
     */
    public String getImageSmallUrl() {
        return imageSmallUrl;
    }

    /**
     *
     * @param imageSmallUrl
     *     The image_small_url
     */
    public void setImageSmallUrl(String imageSmallUrl) {
        this.imageSmallUrl = imageSmallUrl;
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
     * @param quantity
     *     The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     *     The imageThumbUrl
     */
    public String getImageThumbUrl() {
        return imageThumbUrl;
    }

    /**
     *
     * @param imageThumbUrl
     *     The image_thumb_url
     */
    public void setImageThumbUrl(String imageThumbUrl) {
        this.imageThumbUrl = imageThumbUrl;
    }

    /**
     *
     * @return
     *     The nutritionGrades
     */
    public String getNutritionGrades() {
        return nutritionGrades;
    }

    /**
     *
     * @param nutritionGrades
     *     The nutrition_grades
     */
    public void setNutritionGrades(String nutritionGrades) {
        this.nutritionGrades = nutritionGrades;
    }

    /**
     *
     * @return
     *     The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     *     The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     *     The productNameFr
     */
    public String getProductNameFr() {
        return productNameFr;
    }

    /**
     *
     * @param productNameFr
     *     The product_name_fr
     */
    public void setProductNameFr(String productNameFr) {
        this.productNameFr = productNameFr;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

}

