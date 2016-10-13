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

package com.fredericletellier.foodinspector.data.source.local.db;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.fredericletellier.foodinspector.BuildConfig;

/**
 * The contract used for the db to save the category locally.
 */
public final class CategoryPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String CONTENT_CATEGORY_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + CategoryEntry.TABLE_NAME;
    public static final String CONTENT_CATEGORY_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + CategoryEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private CategoryPersistenceContract() {}

    public static Uri getBaseCategoryUri(String categoryId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_CATEGORY_ITEM_TYPE + SEPARATOR + categoryId);
    }

    /* Inner class that defines the table contents */
    public static abstract class CategoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_COUNTRY_ID = "countryid";
        public static final String COLUMN_NAME_WORLD_CATEGORY_ID = "worldcategoryid";
        public static final String COLUMN_HAVE_DATA = "havedata";
        public static final Uri CONTENT_CATEGORY_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] CATEGORY_COLUMNS = new String[]{
                CategoryPersistenceContract.CategoryEntry._ID,
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_COUNTRY_ID,
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_WORLD_CATEGORY_ID,
                CategoryPersistenceContract.CategoryEntry.COLUMN_HAVE_DATA};

        public static Uri buildCategoryUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_CATEGORY_URI, id);
        }

        public static Uri buildCategoryUriWith(String id) {
            Uri uri = CONTENT_CATEGORY_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildCategoryUri() {
            return CONTENT_CATEGORY_URI.buildUpon().build();
        }

    }

}

