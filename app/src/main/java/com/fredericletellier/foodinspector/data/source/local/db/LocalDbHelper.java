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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Local.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String CREATE_TABLE = "CREATE TABLE ";

    private static final String FOREIGN_KEY = " FOREIGN KEY";

    private static final String REFERENCES = " REFERENCES ";

    private static final String OPEN_PARENTHESIS = " (";

    private static final String CLOSE_PARENTHESIS = " )";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_EVENT =
            CREATE_TABLE + EventPersistenceContract.EventEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    EventPersistenceContract.EventEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    EventPersistenceContract.EventEntry.COLUMN_NAME_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS + TEXT_TYPE +
                    CLOSE_PARENTHESIS;

    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EVENT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}