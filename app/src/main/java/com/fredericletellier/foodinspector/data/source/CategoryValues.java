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

import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;

public class CategoryValues {

    public static ContentValues from(Category category) {
        ContentValues values = new ContentValues();
        values.put(CategoryPersistenceContract.CategoryEntry._ID, category.getId());
        values.put(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_CATEGORY_KEY, category.getCategoryKey());
        values.put(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_CATEGORY_NAME, category.getCategoryName());
        return values;
    }

}
