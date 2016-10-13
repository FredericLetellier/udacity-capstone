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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Immutable model class for a Product.
 */
public final class Product {

    @NonNull
    private final String mId;

    @Nullable
    private final String mProductName;

    @Nullable
    private final String mGenericName;

    @Nullable
    private final String mMainBrand;

    @Nullable
    private final String mQuantity;

    @Nullable
    private final String mNutritionGrade;


    /**
     * Use this constructor to create a new Product.
     * @param id
     * @param productName
     * @param genericName
     * @param mainBrand
     * @param quantity
     * @param nutritionGrade
     */
    public Product(String id, @Nullable String productName, @Nullable String genericName,
                   @Nullable String mainBrand, @Nullable String quantity,
                   @Nullable String nutritionGrade) {
        mId = id;
        mProductName = productName;
        mGenericName = genericName;
        mMainBrand = mainBrand;
        mQuantity = quantity;
        mNutritionGrade = nutritionGrade;
    }

    /**
     * Use this constructor to return a Task from a Cursor
     *
     * @return
     */

    //TODO public static Product from(Cursor cursor)

    //TODO public static Product from(ContentValues values)


    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getProductName() {
        return mProductName;
    }

    @Nullable
    public String getGenericName() {
        return mGenericName;
    }

    @Nullable
    public String getMainBrand() {
        return mMainBrand;
    }

    @Nullable
    public String getQuantity() {
        return mQuantity;
    }

    @Nullable
    public String getNutritionGrade() {
        return mNutritionGrade;
    }

    @Nullable
    public String getTitle() {
        return mProductName + " - " + mMainBrand + " - " + mQuantity;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mProductName) &&
                Strings.isNullOrEmpty(mGenericName) &&
                Strings.isNullOrEmpty(mMainBrand) &&
                Strings.isNullOrEmpty(mQuantity) &&
                Strings.isNullOrEmpty(mNutritionGrade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equal(mId, product.mId) &&
                Objects.equal(mProductName, product.mProductName) &&
                Objects.equal(mGenericName, product.mGenericName) &&
                Objects.equal(mMainBrand, product.mMainBrand) &&
                Objects.equal(mQuantity, product.mQuantity) &&
                Objects.equal(mNutritionGrade, product.mNutritionGrade);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mProductName, mGenericName, mMainBrand, mQuantity, mNutritionGrade);
    }

    @Override
    public String toString() {
        return "Product with title " + getTitle();
    }
}
