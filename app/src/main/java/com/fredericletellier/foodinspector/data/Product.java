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

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Product {

    public static final int LOADING_LIMIT = 20;
    public static final String PARSING_DONE = "PARSING_DONE";

    private long mId;
    private boolean mParsed;
    private boolean mBookmarked;

    @SerializedName("barcode")
    @Expose
    private String mBarcode;
    @SerializedName("generic_name")
    @Expose
    private String mGenericName;
    @SerializedName("product_name")
    @Expose
    private String mProductName;
    @SerializedName("quantity")
    @Expose
    private String mQuantity;
    @SerializedName("nutrition_grades")
    @Expose
    private String mNutritionGrades;
    @SerializedName("categories_tags")
    @Expose
    private String mParsableCategories;
    @SerializedName("brands")
    @Expose
    private String mBrands;
    @SerializedName("image_front_small_url")
    @Expose
    private String mImageFrontSmallUrl;
    @SerializedName("image_front_url")
    @Expose
    private String mImageFrontUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param barcode
     * @param parsed
     */
    public Product(String barcode, boolean parsed) {
        this.mBarcode = barcode;
        this.mParsed = parsed;
    }

    /**
     *
     * @param id
     * @param barcode
     * @param parsed
     * @param bookmarked
     * @param genericName
     * @param productName
     * @param quantity
     * @param nutritionGrades
     * @param parsableCategories
     * @param brands
     * @param imageFrontSmallUrl
     * @param imageFrontUrl
     */
    public Product(long id, String barcode, boolean parsed, boolean bookmarked, String genericName, String productName, String quantity, String nutritionGrades, String parsableCategories, String brands, String imageFrontSmallUrl, String imageFrontUrl) {
        this.mId = id;
        this.mBarcode = barcode;
        this.mParsed = parsed;
        this.mBookmarked = bookmarked;
        this.mGenericName = genericName;
        this.mProductName = productName;
        this.mQuantity = quantity;
        this.mNutritionGrades = nutritionGrades;
        this.mParsableCategories = parsableCategories;
        this.mBrands = brands;
        this.mImageFrontSmallUrl = imageFrontSmallUrl;
        this.mImageFrontUrl = imageFrontUrl;
    }

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return mId;
    }

    /**
     *
     * @return
     * The id
     */
    public String getAsStringId() {
        return String.valueOf(mId);
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.mId = id;
    }

    public Product withId(long id) {
        this.mId = id;
        return this;
    }

    /**
     *
     * @return
     * The barcode
     */
    public String getBarcode() {
        return mBarcode;
    }

    /**
     *
     * @param barcode
     * The barcode
     */
    public void setBarcode(String barcode) {
        this.mBarcode = barcode;
    }

    public Product withBarcode(String barcode) {
        this.mBarcode = barcode;
        return this;
    }

    /**
     *
     * @return
     * The parsed
     */
    public boolean isParsed() {
        return mParsed;
    }

    /**
     *
     * @param parsed
     * The parsed
     */
    public void setParsed(boolean parsed) {
        this.mParsed = parsed;
    }

    public Product withParsed(boolean parsed) {
        this.mParsed = parsed;
        return this;
    }

    /**
     *
     * @return
     * The bookmarked
     */
    public boolean isBookmarked() {
        return mBookmarked;
    }

    /**
     *
     * @param bookmarked
     * The bookmarked
     */
    public void setBookmarked(boolean bookmarked) {
        this.mBookmarked = bookmarked;
    }

    public Product withBookmarked(boolean bookmarked) {
        this.mBookmarked = bookmarked;
        return this;
    }

    /**
     *
     * @return
     * The genericName
     */
    public String getGenericName() {
        return mGenericName;
    }

    /**
     *
     * @param genericName
     * The generic_name
     */
    public void setGenericName(String genericName) {
        this.mGenericName = genericName;
    }

    public Product withGenericName(String genericName) {
        this.mGenericName = genericName;
        return this;
    }

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {
        return mProductName;
    }

    /**
     *
     * @param productName
     * The product_name
     */
    public void setProductName(String productName) {
        this.mProductName = productName;
    }

    public Product withProductName(String productName) {
        this.mProductName = productName;
        return this;
    }

    /**
     *
     * @return
     * The quantity
     */
    public String getQuantity() {
        return mQuantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(String quantity) {
        this.mQuantity = quantity;
    }

    public Product withQuantity(String quantity) {
        this.mQuantity = quantity;
        return this;
    }

    /**
     *
     * @return
     * The nutritionGrades
     */
    public String getNutritionGrades() {
        return mNutritionGrades;
    }

    /**
     *
     * @param nutritionGrades
     * The nutrition_grades
     */
    public void setNutritionGrades(String nutritionGrades) {
        this.mNutritionGrades = nutritionGrades;
    }

    public Product withNutritionGrades(String nutritionGrades) {
        this.mNutritionGrades = nutritionGrades;
        return this;
    }

    /**
     *
     * @return
     * The parsableCategories
     */
    public String getParsableCategories() {
        return mParsableCategories;
    }

    /**
     *
     * @param parsableCategories
     * The parsable-categories
     */
    public void setParsableCategories(String parsableCategories) {
        this.mParsableCategories = parsableCategories;
    }

    public Product withParsableCategories(String parsableCategories) {
        this.mParsableCategories = parsableCategories;
        return this;
    }

    /**
     *
     * @return
     * The brands
     */
    public String getBrands() {
        return mBrands;
    }

    /**
     *
     * @param brands
     * The brands
     */
    public void setBrands(String brands) {
        this.mBrands = brands;
    }

    public Product withBrands(String brands) {
        this.mBrands = brands;
        return this;
    }

    /**
     *
     * @return
     * The imageFrontSmallUrl
     */
    public String getImageFrontSmallUrl() {
        return mImageFrontSmallUrl;
    }

    /**
     *
     * @param imageFrontSmallUrl
     * The image_front_small_url
     */
    public void setImageFrontSmallUrl(String imageFrontSmallUrl) {
        this.mImageFrontSmallUrl = imageFrontSmallUrl;
    }

    public Product withImageFrontSmallUrl(String imageFrontSmallUrl) {
        this.mImageFrontSmallUrl = imageFrontSmallUrl;
        return this;
    }

    /**
     *
     * @return
     * The imageFrontUrl
     */
    public String getImageFrontUrl() {
        return mImageFrontUrl;
    }

    /**
     *
     * @param imageFrontUrl
     * The image_front_url
     */
    public void setImageFrontUrl(String imageFrontUrl) {
        this.mImageFrontUrl = imageFrontUrl;
    }

    public Product withImageFrontUrl(String imageFrontUrl) {
        this.mImageFrontUrl = imageFrontUrl;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mId).append(mBarcode).append(mParsed).append(mBookmarked).append(mGenericName).append(mProductName).append(mQuantity).append(mNutritionGrades).append(mParsableCategories).append(mBrands).append(mImageFrontSmallUrl).append(mImageFrontUrl).toHashCode();
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
        return new EqualsBuilder().append(mId, rhs.mId).append(mBarcode, rhs.mBarcode).append(mParsed, rhs.mParsed).append(mBookmarked, rhs.mBookmarked).append(mGenericName, rhs.mGenericName).append(mProductName, rhs.mProductName).append(mQuantity, rhs.mQuantity).append(mNutritionGrades, rhs.mNutritionGrades).append(mParsableCategories, rhs.mParsableCategories).append(mBrands, rhs.mBrands).append(mImageFrontSmallUrl, rhs.mImageFrontSmallUrl).append(mImageFrontUrl, rhs.mImageFrontUrl).isEquals();
    }

}