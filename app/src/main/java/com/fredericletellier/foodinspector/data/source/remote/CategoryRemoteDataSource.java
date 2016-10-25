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

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.source.CategoryDataSource;

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

    //TODO Intégrer la partie remote de la procédure de récupération du nom

    @Override
    public void checkExistCategory(@NonNull String categoryKey, @NonNull CheckExistCategoryCallback checkExistCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void addCategory(@NonNull Category category, @NonNull AddCategoryCallback addCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void updateCategory(@NonNull Category category, @NonNull UpdateCategoryCallback updateCategoryCallback) {
        //no-op in remote
    }

    @Override
    public void saveCategory(@NonNull Category category, @NonNull SaveCategoryCallback saveCategoryCallback) {
        //no-op in remote
    }
}
