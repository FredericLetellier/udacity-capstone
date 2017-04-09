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

package com.fredericletellier.foodinspector.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.LocalDbHelper;

public class FoodInspectorContentProvider extends ContentProvider {

    private static final int EVENT = 200;
    private static final int EVENT_ITEM = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private LocalDbHelper mLocalDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = EventPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, EventPersistenceContract.EventEntry.TABLE_NAME, EVENT);
        matcher.addURI(authority, EventPersistenceContract.EventEntry.TABLE_NAME + "/*", EVENT_ITEM);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mLocalDbHelper = new LocalDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case EVENT:
                return EventPersistenceContract.CONTENT_EVENT_TYPE;
            case EVENT_ITEM:
                return EventPersistenceContract.CONTENT_EVENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case EVENT:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        EventPersistenceContract.EventEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case EVENT_ITEM:
                String[] where_event = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        EventPersistenceContract.EventEntry.TABLE_NAME,
                        projection,
                        EventPersistenceContract.EventEntry._ID + " = ?",
                        where_event,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mLocalDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        Cursor exists;

        switch (match) {
            case EVENT:
                exists = db.query(
                        EventPersistenceContract.EventEntry.TABLE_NAME,
                        new String[]{EventPersistenceContract.EventEntry._ID},
                        EventPersistenceContract.EventEntry._ID + " = ?",
                        new String[]{values.getAsString(EventPersistenceContract.EventEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            EventPersistenceContract.EventEntry.TABLE_NAME, values,
                            EventPersistenceContract.EventEntry._ID + " = ?",
                            new String[]{values.getAsString(EventPersistenceContract.EventEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = EventPersistenceContract.EventEntry.buildEventUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(EventPersistenceContract.EventEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = EventPersistenceContract.EventEntry.buildEventUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri + "   " + values.toString());
                    }
                }
                exists.close();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mLocalDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case EVENT:
                rowsDeleted = db.delete(
                        EventPersistenceContract.EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mLocalDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case EVENT:
                rowsUpdated = db.update(EventPersistenceContract.EventEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}