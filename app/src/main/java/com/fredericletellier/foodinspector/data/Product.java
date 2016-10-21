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

package com.fredericletellier.foodinspector.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Product {

    public static final int LOADING_LIMIT = 20;
    public static final String PARSING_DONE = "PARSING_DONE";

    private long id;
    private boolean parsed;

    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("generic_name")
    @Expose
    private String genericName;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("nutrition_grades")
    @Expose
    private String nutritionGrades;
    @SerializedName("categories_tags")
    @Expose
    private String parsableCategories;
    @SerializedName("brands")
    @Expose
    private String brands;
    @SerializedName("image_front_small_url")
    @Expose
    private String imageFrontSmallUrl;
    @SerializedName("image_front_url")
    @Expose
    private String imageFrontUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param id
     * @param brands
     * @param parsed
     * @param imageFrontUrl
     * @param parsableCategories
     * @param barcode
     * @param quantity
     * @param imageFrontSmallUrl
     * @param productName
     * @param genericName
     * @param nutritionGrades
     */
    public Product(long id, String barcode, boolean parsed, String genericName, String productName, String quantity, String nutritionGrades, String parsableCategories, String brands, String imageFrontSmallUrl, String imageFrontUrl) {
        this.id = id;
        this.barcode = barcode;
        this.parsed = parsed;
        this.genericName = genericName;
        this.productName = productName;
        this.quantity = quantity;
        this.nutritionGrades = nutritionGrades;
        this.parsableCategories = parsableCategories;
        this.brands = brands;
        this.imageFrontSmallUrl = imageFrontSmallUrl;
        this.imageFrontUrl = imageFrontUrl;
    }


    //TODO public static Product from(Cursor cursor)

    //TODO public static Product from(ContentValues values)

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    public Product withId(long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     *
     * @param barcode
     * The barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Product withBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    /**
     *
     * @return
     * The parsed
     */
    public boolean isParsed() {
        return parsed;
    }

    /**
     *
     * @param parsed
     * The parsed
     */
    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

    public Product withParsed(boolean parsed) {
        this.parsed = parsed;
        return this;
    }

    /**
     *
     * @return
     * The genericName
     */
    public String getGenericName() {
        return genericName;
    }

    /**
     *
     * @param genericName
     * The generic_name
     */
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public Product withGenericName(String genericName) {
        this.genericName = genericName;
        return this;
    }

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     * The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    /**
     *
     * @return
     * The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Product withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     *
     * @return
     * The nutritionGrades
     */
    public String getNutritionGrades() {
        return nutritionGrades;
    }

    /**
     *
     * @param nutritionGrades
     * The nutrition_grades
     */
    public void setNutritionGrades(String nutritionGrades) {
        this.nutritionGrades = nutritionGrades;
    }

    public Product withNutritionGrades(String nutritionGrades) {
        this.nutritionGrades = nutritionGrades;
        return this;
    }

    /**
     *
     * @return
     * The parsableCategories
     */
    public String getParsableCategories() {
        return parsableCategories;
    }

    /**
     *
     * @param parsableCategories
     * The parsable-categories
     */
    public void setParsableCategories(String parsableCategories) {
        this.parsableCategories = parsableCategories;
    }

    public Product withParsableCategories(String parsableCategories) {
        this.parsableCategories = parsableCategories;
        return this;
    }

    /**
     *
     * @return
     * The brands
     */
    public String getBrands() {
        return brands;
    }

    /**
     *
     * @param brands
     * The brands
     */
    public void setBrands(String brands) {
        this.brands = brands;
    }

    public Product withBrands(String brands) {
        this.brands = brands;
        return this;
    }

    /**
     *
     * @return
     * The imageFrontSmallUrl
     */
    public String getImageFrontSmallUrl() {
        return imageFrontSmallUrl;
    }

    /**
     *
     * @param imageFrontSmallUrl
     * The image_front_small_url
     */
    public void setImageFrontSmallUrl(String imageFrontSmallUrl) {
        this.imageFrontSmallUrl = imageFrontSmallUrl;
    }

    public Product withImageFrontSmallUrl(String imageFrontSmallUrl) {
        this.imageFrontSmallUrl = imageFrontSmallUrl;
        return this;
    }

    /**
     *
     * @return
     * The imageFrontUrl
     */
    public String getImageFrontUrl() {
        return imageFrontUrl;
    }

    /**
     *
     * @param imageFrontUrl
     * The image_front_url
     */
    public void setImageFrontUrl(String imageFrontUrl) {
        this.imageFrontUrl = imageFrontUrl;
    }

    public Product withImageFrontUrl(String imageFrontUrl) {
        this.imageFrontUrl = imageFrontUrl;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(barcode).append(parsed).append(genericName).append(productName).append(quantity).append(nutritionGrades).append(parsableCategories).append(brands).append(imageFrontSmallUrl).append(imageFrontUrl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Product) == false) {
            return false;
        }
        Product rhs = ((Product) other);
        return new EqualsBuilder().append(id, rhs.id).append(barcode, rhs.barcode).append(parsed, rhs.parsed).append(genericName, rhs.genericName).append(productName, rhs.productName).append(quantity, rhs.quantity).append(nutritionGrades, rhs.nutritionGrades).append(parsableCategories, rhs.parsableCategories).append(brands, rhs.brands).append(imageFrontSmallUrl, rhs.imageFrontSmallUrl).append(imageFrontUrl, rhs.imageFrontUrl).isEquals();
    }

}