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
 * The contract used for the db to save the link between one category in a country and multiple products locally.
 */
public final class SuggestionPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String CONTENT_SUGGESTION_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + SuggestionEntry.TABLE_NAME;
    public static final String CONTENT_SUGGESTION_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + SuggestionEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private SuggestionPersistenceContract() {}

    public static Uri getBaseSuggestionUri(String eventId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_SUGGESTION_ITEM_TYPE + SEPARATOR + eventId);
    }

    /* Inner class that defines the table contents */
    public static abstract class SuggestionEntry implements BaseColumns {

        public static final String TABLE_NAME = "suggestion";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_CATEGORY_KEY = "categoryKey";
        public static final String COLUMN_NAME_COUNTRY_KEY = "countryKey";
        public static final Uri CONTENT_SUGGESTION_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] SUGGESTION_COLUMNS = new String[]{
                SuggestionPersistenceContract.SuggestionEntry._ID,
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_BARCODE,
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_CATEGORY_KEY,
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_COUNTRY_KEY};

        public static Uri buildSuggestionUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_SUGGESTION_URI, id);
        }

        public static Uri buildSuggestionUriWith(String id) {
            Uri uri = CONTENT_SUGGESTION_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildSuggestionUri() {
            return CONTENT_SUGGESTION_URI.buildUpon().build();
        }

    }
}