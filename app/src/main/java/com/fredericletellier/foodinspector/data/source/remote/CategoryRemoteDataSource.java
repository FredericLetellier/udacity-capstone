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

import com.fredericletellier.foodinspector.data.source.CategoryDataSource;

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

    @Override
    public void getCategories(@NonNull String productId, @NonNull String countryCode, @NonNull GetCategoriesCallback callback) {
        checkNotNull(productId);
        checkNotNull(countryCode);
        checkNotNull(callback);

    }
}
