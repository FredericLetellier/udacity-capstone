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

import com.fredericletellier.foodinspector.data.CategoryTag;

/**
 * Main entry point for accessing categoryTag data.
 */
public interface CategoryTagDataSource {

    interface GetCategoryTagCallback {

        void onCategoryTagLoaded(CategoryTag categoryTag);

        void onError(Exception exception);

    }

    interface AddCategoryTagCallback {

        void onCategoryTagAdded(CategoryTag categoryTag);

        void onError(Exception exception);

    }

    interface UpdateCategoryTagCallback {

        void onCategoryTagUpdated(CategoryTag categoryTag);

        void onError(Exception exception);

    }

    interface SaveCategoryTagCallback {

        void onCategoryTagSaved(CategoryTag categoryTag);

        void onError(Exception exception);

    }

    void getCategoryTag(@NonNull CategoryTag categoryTag, @NonNull GetCategoryTagCallback getCategoryTagCallback);

    void addCategoryTag(@NonNull CategoryTag categoryTag, @NonNull AddCategoryTagCallback addCategoryTagCallback);

    void updateCategoryTag(@NonNull CategoryTag categoryTag, @NonNull UpdateCategoryTagCallback updateCategoryTagCallback);

    void saveCategoryTag(@NonNull CategoryTag categoryTag, @NonNull SaveCategoryTagCallback saveCategoryTagCallback);
}
