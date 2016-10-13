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
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.source.CategoryDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a remote db
 */
public class CategoryRemoteDataSource implements CategoryDataSource {

    private static CategoryRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private CategoryRemoteDataSource() {
    }

    public static CategoryRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryRemoteDataSource();
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    //###REMOTE
	//Pour chaque categorie de la liste
	//	 Je recupere l'information via l'API sur le nombre de produits de cette catégorie avec ce code pays et ayant une note nutritionnelle
	//	 J'ajoute en base une nouvelle entrée lié à la catégorie avec son code pays et si elle contient plus d'un produit
    @Override
    public void getCategories(@NonNull String productId, @Nullable List<Category> categories, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);
    }

}
