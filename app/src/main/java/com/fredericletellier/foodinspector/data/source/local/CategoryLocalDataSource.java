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
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.source.CategoryDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class CategoryLocalDataSource implements CategoryDataSource {

    private static CategoryLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private CategoryLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static CategoryLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    //###LOCAL
    //J'ai un code
    //Je cherche ce produit dans ma base
    //Si ce produit n'est pas dans ma base
    //	(callback facultatif ?!?)
    //Si ce produit est dans ma base
    //	Je recupere le champ PARSE
    //	Si ce champ n'est pas égal à DONE
    //		Je parse le champ
    //		Pour chauqe catégorie
    //			Je cherche cette categorie et ce code pays
    //			Si je trouve
    //				J'ajoute en base une nouvelle entrée lié au produit pour la catégorie avec son rang, son nom, et son code
    //			Si je ne trouve pas
    //				J'ajoute en base le couple categorie/pays avce son code categorie et son code pays
    //			J'ajoute en base une nouvelle entrée lié au produit pour la catégorie avec son rang, son nom, et son code
    //		J'inscris DONE dans le champ
    //	Je recupere la liste des catégories associées à ce produit
    //	Pour chaque catégorie
    //		Si la catégorie existe avec le CODE_PAYS_ACTUEL
    //			rien
    //		Si la catégorie n'existe pas avec le CODE_PAYS_ACTUEL
    //			J'ajoute cette categorie à une liste
    //	callback avec la liste des categories qui ne sont pas en local

    @Override
    public void getCategories(@NonNull String productId, @Nullable List<Category> categories, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);
    }
}
