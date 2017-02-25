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

import java.util.List;

public class Product {

    private long mId;
    private boolean mParsed;
    private boolean mBookmarked;

    @SerializedName("code")
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
    private List<String> mParsableCategories;
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


    public Product(String mBarcode, boolean mBookmarked, String mBrands, String mGenericName, long mId, String mImageFrontSmallUrl, String mImageFrontUrl, String mNutritionGrades, List<String> mParsableCategories, boolean mParsed, String mProductName, String mQuantity) {
        this.mBarcode = mBarcode;
        this.mBookmarked = mBookmarked;
        this.mBrands = mBrands;
        this.mGenericName = mGenericName;
        this.mId = mId;
        this.mImageFrontSmallUrl = mImageFrontSmallUrl;
        this.mImageFrontUrl = mImageFrontUrl;
        this.mNutritionGrades = mNutritionGrades;
        this.mParsableCategories = mParsableCategories;
        this.mParsed = mParsed;
        this.mProductName = mProductName;
        this.mQuantity = mQuantity;
    }

    public String getmBarcode() {
        return mBarcode;
    }

    public void setmBarcode(String mBarcode) {
        this.mBarcode = mBarcode;
    }

    public boolean ismBookmarked() {
        return mBookmarked;
    }

    public void setmBookmarked(boolean mBookmarked) {
        this.mBookmarked = mBookmarked;
    }

    public String getmBrands() {
        return mBrands;
    }

    public void setmBrands(String mBrands) {
        this.mBrands = mBrands;
    }

    public String getmGenericName() {
        return mGenericName;
    }

    public void setmGenericName(String mGenericName) {
        this.mGenericName = mGenericName;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmImageFrontSmallUrl() {
        return mImageFrontSmallUrl;
    }

    public void setmImageFrontSmallUrl(String mImageFrontSmallUrl) {
        this.mImageFrontSmallUrl = mImageFrontSmallUrl;
    }

    public String getmImageFrontUrl() {
        return mImageFrontUrl;
    }

    public void setmImageFrontUrl(String mImageFrontUrl) {
        this.mImageFrontUrl = mImageFrontUrl;
    }

    public String getmNutritionGrades() {
        return mNutritionGrades;
    }

    public void setmNutritionGrades(String mNutritionGrades) {
        this.mNutritionGrades = mNutritionGrades;
    }

    public List<String> getmParsableCategories() {
        return mParsableCategories;
    }

    public void setmParsableCategories(List<String> mParsableCategories) {
        this.mParsableCategories = mParsableCategories;
    }

    public boolean ismParsed() {
        return mParsed;
    }

    public void setmParsed(boolean mParsed) {
        this.mParsed = mParsed;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mBarcode='" + mBarcode + '\'' +
                ", mId=" + mId +
                ", mParsed=" + mParsed +
                ", mBookmarked=" + mBookmarked +
                ", mGenericName='" + mGenericName + '\'' +
                ", mProductName='" + mProductName + '\'' +
                ", mQuantity='" + mQuantity + '\'' +
                ", mNutritionGrades='" + mNutritionGrades + '\'' +
                ", mParsableCategories=" + mParsableCategories +
                ", mBrands='" + mBrands + '\'' +
                ", mImageFrontSmallUrl='" + mImageFrontSmallUrl + '\'' +
                ", mImageFrontUrl='" + mImageFrontUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getmId() != product.getmId()) return false;
        if (ismParsed() != product.ismParsed()) return false;
        if (ismBookmarked() != product.ismBookmarked()) return false;
        if (getmBarcode() != null ? !getmBarcode().equals(product.getmBarcode()) : product.getmBarcode() != null)
            return false;
        if (getmGenericName() != null ? !getmGenericName().equals(product.getmGenericName()) : product.getmGenericName() != null)
            return false;
        if (getmProductName() != null ? !getmProductName().equals(product.getmProductName()) : product.getmProductName() != null)
            return false;
        if (getmQuantity() != null ? !getmQuantity().equals(product.getmQuantity()) : product.getmQuantity() != null)
            return false;
        if (getmNutritionGrades() != null ? !getmNutritionGrades().equals(product.getmNutritionGrades()) : product.getmNutritionGrades() != null)
            return false;
        if (getmParsableCategories() != null ? !getmParsableCategories().equals(product.getmParsableCategories()) : product.getmParsableCategories() != null)
            return false;
        if (getmBrands() != null ? !getmBrands().equals(product.getmBrands()) : product.getmBrands() != null)
            return false;
        if (getmImageFrontSmallUrl() != null ? !getmImageFrontSmallUrl().equals(product.getmImageFrontSmallUrl()) : product.getmImageFrontSmallUrl() != null)
            return false;
        return getmImageFrontUrl() != null ? getmImageFrontUrl().equals(product.getmImageFrontUrl()) : product.getmImageFrontUrl() == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (getmId() ^ (getmId() >>> 32));
        result = 31 * result + (ismParsed() ? 1 : 0);
        result = 31 * result + (ismBookmarked() ? 1 : 0);
        result = 31 * result + (getmBarcode() != null ? getmBarcode().hashCode() : 0);
        result = 31 * result + (getmGenericName() != null ? getmGenericName().hashCode() : 0);
        result = 31 * result + (getmProductName() != null ? getmProductName().hashCode() : 0);
        result = 31 * result + (getmQuantity() != null ? getmQuantity().hashCode() : 0);
        result = 31 * result + (getmNutritionGrades() != null ? getmNutritionGrades().hashCode() : 0);
        result = 31 * result + (getmParsableCategories() != null ? getmParsableCategories().hashCode() : 0);
        result = 31 * result + (getmBrands() != null ? getmBrands().hashCode() : 0);
        result = 31 * result + (getmImageFrontSmallUrl() != null ? getmImageFrontSmallUrl().hashCode() : 0);
        result = 31 * result + (getmImageFrontUrl() != null ? getmImageFrontUrl().hashCode() : 0);
        return result;
    }
}