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
 * The contract used for the db to save the link between one product and multiple categories locally.
 */
public class CategoryTagPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String CONTENT_CATEGORYTAG_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + CategoryTagEntry.TABLE_NAME;
    public static final String CONTENT_CATEGORYTAG_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + CategoryTagEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private CategoryTagPersistenceContract() {}

    public static Uri getBaseCategoryTagUri(String eventId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_CATEGORYTAG_ITEM_TYPE + SEPARATOR + eventId);
    }

    /* Inner class that defines the table contents */
    public static abstract class CategoryTagEntry implements BaseColumns {

        public static final String TABLE_NAME = "categorytag";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_CATEGORY_KEY = "categoryKey";
        public static final String COLUMN_NAME_RANK = "rank";
        public static final Uri CONTENT_CATEGORYTAG_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] CATEGORYTAG_COLUMNS = new String[]{
                CategoryTagPersistenceContract.CategoryTagEntry._ID,
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE,
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_CATEGORY_KEY,
                CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_RANK};

        public static Uri buildCategoryTagUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_CATEGORYTAG_URI, id);
        }

        public static Uri buildCategoryTagUriWith(String id) {
            Uri uri = CONTENT_CATEGORYTAG_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildCategoryTagUri() {
            return CONTENT_CATEGORYTAG_URI.buildUpon().build();
        }

    }
}
