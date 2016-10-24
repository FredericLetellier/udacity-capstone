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

import com.fredericletellier.foodinspector.data.Suggestion;
import com.fredericletellier.foodinspector.data.source.local.db.SuggestionPersistenceContract;

public class SuggestionValues {

    public static ContentValues from(Suggestion suggestion) {
        ContentValues values = new ContentValues();
        values.put(SuggestionPersistenceContract.SuggestionEntry._ID, suggestion.getId());
        values.put(SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_BARCODE, suggestion.getBarcode());
        values.put(SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_CATEGORY_KEY, suggestion.getCategoryKey());
        values.put(SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_COUNTRY_KEY, suggestion.getCountryKey());
        return values;
    }
}
