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

import android.content.ContentValues;

import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.source.local.db.CountryCategoryPersistenceContract;

public class CountryCategoryValues {

    public static ContentValues from(CountryCategory countryCategory) {
        ContentValues values = new ContentValues();
        values.put(CountryCategoryPersistenceContract.CountryCategoryEntry._ID, countryCategory.getId());
        values.put(CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_CATEGORY_KEY, countryCategory.getCategoryKey());
        values.put(CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_COUNTRY_KEY, countryCategory.getCountryKey());
        values.put(CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_SUM_OF_PRODUCTS, countryCategory.getSumOfProducts());
        return values;
    }
}
