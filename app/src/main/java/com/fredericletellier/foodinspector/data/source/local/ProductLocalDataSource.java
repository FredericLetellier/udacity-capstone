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

package com.fredericletellier.foodinspector.data.source.local;

import android.content.ContentResolver;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.source.ProductDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class ProductLocalDataSource implements ProductDataSource {

    private static ProductLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private ProductLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static ProductLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new ProductLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    //###LOCAL
    //J'ai un code categorie et un code pays
    //Je cherche les x derniers produits avec une note nutritionnelle y
    //Si ces produit existent dans ma base
    //	(callback facultatif ?!?)
    //Si ces produit n'existent pas dans ma base
    //	(callback no data)

    @Override
    public void getXProductsInCategory(@NonNull String categoryId, @NonNull String nutritionGradeValue, @NonNull Integer skipProducts, @NonNull GetXProductsInCategoryCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(nutritionGradeValue);
        checkNotNull(skipProducts);
        checkNotNull(callback);
    }
}
