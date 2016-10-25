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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Suggestion;
import com.fredericletellier.foodinspector.data.source.SuggestionDataSource;
import com.fredericletellier.foodinspector.data.source.SuggestionValues;
import com.fredericletellier.foodinspector.data.source.local.db.SuggestionPersistenceContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class SuggestionLocalDataSource implements SuggestionDataSource {

    private static SuggestionLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private SuggestionLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static SuggestionLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new SuggestionLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }


    @Override
    public void checkExistSuggestion(@NonNull String barcode, @NonNull String categoryKey, @NonNull String countryKey,
                                     @NonNull CheckExistSuggestionCallback checkExistSuggestionCallback) {
        Cursor cursor = mContentResolver.query(
                SuggestionPersistenceContract.SuggestionEntry.buildSuggestionUri(),
                new String[]{SuggestionPersistenceContract.SuggestionEntry._ID},
                SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_BARCODE + " = ? AND " +
                        SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_CATEGORY_KEY + " = ? AND" +
                        SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_COUNTRY_KEY + " = ? AND",
                new String[]{barcode, categoryKey, countryKey},
                null);

        if (cursor.moveToLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(SuggestionPersistenceContract.SuggestionEntry._ID));
            checkExistSuggestionCallback.onSuggestionExisted(_id);
        } else {
            checkExistSuggestionCallback.onSuggestionNotExisted();
        }
        cursor.close();
    }

    @Override
    public void addSuggestion(@NonNull Suggestion Suggestion, @NonNull AddSuggestionCallback addSuggestionCallback) {
        checkNotNull(Suggestion);

        ContentValues values = SuggestionValues.from(Suggestion);
        Uri uri = mContentResolver.insert(SuggestionPersistenceContract.SuggestionEntry.buildSuggestionUri(), values);

        if (uri != null) {
            addSuggestionCallback.onSuggestionAdded();
        } else {
            addSuggestionCallback.onError();
        }
    }

    @Override
    public void updateSuggestion(@NonNull Suggestion Suggestion, @NonNull UpdateSuggestionCallback updateSuggestionCallback) {
        checkNotNull(Suggestion);

        ContentValues values = SuggestionValues.from(Suggestion);

        String selection = SuggestionPersistenceContract.SuggestionEntry._ID + " LIKE ?";
        String[] selectionArgs = {Suggestion.getAsStringId()};

        int rows = mContentResolver.update(SuggestionPersistenceContract.SuggestionEntry.buildSuggestionUri(), values, selection, selectionArgs);

        if (rows != 0) {
            updateSuggestionCallback.onSuggestionUpdated();
        } else {
            updateSuggestionCallback.onError();
        }
    }

    @Override
    public void saveSuggestion(@NonNull final Suggestion Suggestion, @NonNull final SaveSuggestionCallback saveSuggestionCallback) {
        checkNotNull(Suggestion);

        String barcode = Suggestion.getBarcode();
        String categoryKey = Suggestion.getCategoryKey();
        String countryKey = Suggestion.getCountryKey();

        checkExistSuggestion(barcode, categoryKey, countryKey, new CheckExistSuggestionCallback() {

            @Override
            public void onSuggestionExisted(long id) {
                Suggestion.setId(id);
                updateSuggestion(Suggestion, new UpdateSuggestionCallback() {
                    @Override
                    public void onSuggestionUpdated() {
                        saveSuggestionCallback.onSuggestionSaved();
                    }

                    @Override
                    public void onError() {
                        saveSuggestionCallback.onError();
                    }
                });
            }

            @Override
            public void onSuggestionNotExisted() {
                addSuggestion(Suggestion, new AddSuggestionCallback() {
                    @Override
                    public void onSuggestionAdded() {
                        saveSuggestionCallback.onSuggestionSaved();
                    }

                    @Override
                    public void onError() {
                        saveSuggestionCallback.onError();
                    }
                });
            }
        });
    }
}