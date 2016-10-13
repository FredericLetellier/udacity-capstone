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

import com.fredericletellier.foodinspector.data.source.ProductDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a remote db
 */
public class ProductRemoteDataSource implements ProductDataSource {

    private static ProductRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private ProductRemoteDataSource() {
    }

    public static ProductRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductRemoteDataSource();
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    //###REMOTE
	//Je recupere l'information via l'API
	//J'ajoute les produits récupérés
	//(pas de callback si le nombre de produits récupérés est nul ou inférieur à ce qui a été demandé)
    @Override
    public void getXProductsInCategory(@NonNull String categoryId, @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts, @NonNull GetXProductsInCategoryCallback callback) {
        checkNotNull(categoryId);
        checkNotNull(nutritionGradeValue);
        checkNotNull(offsetProducts);
        checkNotNull(callback);
    }
}