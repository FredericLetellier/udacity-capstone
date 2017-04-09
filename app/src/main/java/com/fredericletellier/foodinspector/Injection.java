package com.fredericletellier.foodinspector;/*
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

import android.content.Context;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.source.FoodInspectorRepository;
import com.fredericletellier.foodinspector.data.source.local.EventLocalDataSource;
import com.fredericletellier.foodinspector.data.source.remote.CategoryRemoteDataSource;
import com.fredericletellier.foodinspector.data.source.remote.CountryCategoryRemoteDataSource;
import com.fredericletellier.foodinspector.data.source.remote.ProductRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static FoodInspectorRepository provideFoodInspectorRepository(@NonNull Context context) {
        checkNotNull(context);
        return FoodInspectorRepository.getInstance(
                ProductRemoteDataSource.getInstance(),
                EventLocalDataSource.getInstance(context.getContentResolver()),
                CategoryRemoteDataSource.getInstance(),
                CountryCategoryRemoteDataSource.getInstance()
        );
    }

}
