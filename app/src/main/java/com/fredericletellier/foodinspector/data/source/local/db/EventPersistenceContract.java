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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The contract used for the db to save the category locally.
 */
public final class EventPersistenceContract {

    public static final String CONTENT_AUTHORITY = "com.fredericletellier.foodinspector";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private EventPersistenceContract() {}

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(EventEntry.TABLE_NAME).build();

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + EventEntry.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + EventEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class EventEntry implements BaseColumns {

        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_STATUS = "status";
        public static String[] EVENT_COLUMNS = new String[]{
                EventPersistenceContract.EventEntry._ID,
                EventPersistenceContract.EventEntry.COLUMN_NAME_TIMESTAMP,
                EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE,
                EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS};

        public static Uri buildEventUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildEventUri() {
            return CONTENT_URI.buildUpon().build();
        }

    }

}