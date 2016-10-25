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

import com.fredericletellier.foodinspector.data.CategoryTag;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryTagPersistenceContract;

public class CategoryTagValues {

    public static ContentValues from(CategoryTag categoryTag) {
        ContentValues values = new ContentValues();

        if(categoryTag.getId() != 0L) {
            values.put(CategoryTagPersistenceContract.CategoryTagEntry._ID, categoryTag.getId());
        }

        if(categoryTag.getBarcode() != null) {
            values.put(CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE, categoryTag.getBarcode());
        }

        if(categoryTag.getCategoryKey() != null) {
            values.put(CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_CATEGORY_KEY, categoryTag.getCategoryKey());
        }

        if(categoryTag.getRank() != 0) {
            values.put(CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_RANK, categoryTag.getRank());
        }

        return values;
    }

}
