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

package com.fredericletellier.foodinspector.data.source;

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Category;

import java.util.List;

/**
 * Main entry point for accessing category data.
 */
public interface CategoryDataSource {

    interface GetCategoryIdCallback {

        void onCategoryIdLoaded(long id);

        void onCategoryNotExist();

    }

    interface AddCategoryCallback {

        void onCategoryAdded();

        void onError();

    }

    interface UpdateCategoryCallback {

        void onCategoryUpdated();

        void onError();

    }

    interface SaveCategoryCallback {

        void onCategorySaved();

        void onError();

    }

    interface GetCategoryOfProductCallback {

        void onCategoryOfProductLoaded(List<Category> categories);

        void onError(Throwable throwable);

    }

    void getCategoryId(@NonNull String categoryKey, @NonNull GetCategoryIdCallback getCategoryIdCallback);

    void addCategory(@NonNull Category category, @NonNull AddCategoryCallback addCategoryCallback);

    void updateCategory(@NonNull Category category, @NonNull UpdateCategoryCallback updateCategoryCallback);

    void saveCategory(@NonNull Category category, @NonNull SaveCategoryCallback saveCategoryCallback);

    void getCategoryOfProduct(@NonNull String barcode, @NonNull GetCategoryOfProductCallback getCategoryOfProductCallback);

}
