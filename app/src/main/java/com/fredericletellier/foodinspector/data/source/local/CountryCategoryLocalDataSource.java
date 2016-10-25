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

import com.fredericletellier.foodinspector.data.CountryCategory;
import com.fredericletellier.foodinspector.data.source.CountryCategoryDataSource;
import com.fredericletellier.foodinspector.data.source.CountryCategoryValues;
import com.fredericletellier.foodinspector.data.source.local.db.CountryCategoryPersistenceContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class CountryCategoryLocalDataSource implements CountryCategoryDataSource {

    private static CountryCategoryLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private CountryCategoryLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static CountryCategoryLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new CountryCategoryLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getCountryCategory(@NonNull CountryCategory countryCategory,
                                   @NonNull GetCountryCategoryCallback getCountryCategoryCallback) {
        // no-op in local
    }

    @Override
    public void checkExistCountryCategory(@NonNull String categoryKey, @NonNull String countryKey,
                                          @NonNull CheckExistCountryCategoryCallback checkExistCountryCategoryCallback) {

        Cursor cursor = mContentResolver.query(
                CountryCategoryPersistenceContract.CountryCategoryEntry.buildCountryCategoryUri(),
                null,
                CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_CATEGORY_KEY + " = ? AND " +
                        CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_COUNTRY_KEY + " = ?",
                new String[]{categoryKey, countryKey},
                null);

        if (cursor.moveToLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(CountryCategoryPersistenceContract.CountryCategoryEntry._ID));
            checkExistCountryCategoryCallback.onCountryCategoryExisted(_id);
        } else {
            checkExistCountryCategoryCallback.onCountryCategoryNotExisted();
        }
        cursor.close();
    }

    @Override
    public void addCountryCategory(@NonNull CountryCategory CountryCategory,
                                   @NonNull CountryCategoryDataSource.AddCountryCategoryCallback addCountryCategoryCallback) {
        checkNotNull(CountryCategory);

        ContentValues values = CountryCategoryValues.from(CountryCategory);
        Uri uri = mContentResolver.insert(CountryCategoryPersistenceContract.CountryCategoryEntry.buildCountryCategoryUri(), values);

        if (uri != null) {
            addCountryCategoryCallback.onCountryCategoryAdded();
        } else {
            addCountryCategoryCallback.onError();
        }
    }

    @Override
    public void updateCountryCategory(@NonNull CountryCategory CountryCategory,
                                      @NonNull CountryCategoryDataSource.UpdateCountryCategoryCallback updateCountryCategoryCallback) {
        checkNotNull(CountryCategory);

        ContentValues values = CountryCategoryValues.from(CountryCategory);

        String selection = CountryCategoryPersistenceContract.CountryCategoryEntry._ID + " LIKE ?";
        String[] selectionArgs = {CountryCategory.getAsStringId()};

        int rows = mContentResolver.update(CountryCategoryPersistenceContract.CountryCategoryEntry.buildCountryCategoryUri(), values, selection, selectionArgs);

        if (rows != 0) {
            updateCountryCategoryCallback.onCountryCategoryUpdated();
        } else {
            updateCountryCategoryCallback.onError();
        }
    }

    @Override
    public void saveCountryCategory(@NonNull final CountryCategory CountryCategory,
                                    @NonNull final CountryCategoryDataSource.SaveCountryCategoryCallback saveCountryCategoryCallback) {
        checkNotNull(CountryCategory);

        String categoryKey = CountryCategory.getCategoryKey();
        String countryKey = CountryCategory.getCountryKey();

        checkExistCountryCategory(categoryKey, countryKey, new CheckExistCountryCategoryCallback() {

            @Override
            public void onCountryCategoryExisted(long id) {
                CountryCategory.setId(id);
                updateCountryCategory(CountryCategory, new CountryCategoryDataSource.UpdateCountryCategoryCallback() {
                    @Override
                    public void onCountryCategoryUpdated() {
                        saveCountryCategoryCallback.onCountryCategorySaved();
                    }

                    @Override
                    public void onError() {
                        saveCountryCategoryCallback.onError();
                    }
                });
            }

            @Override
            public void onCountryCategoryNotExisted() {
                addCountryCategory(CountryCategory, new CountryCategoryDataSource.AddCountryCategoryCallback() {
                    @Override
                    public void onCountryCategoryAdded() {
                        saveCountryCategoryCallback.onCountryCategorySaved();
                    }

                    @Override
                    public void onError() {
                        saveCountryCategoryCallback.onError();
                    }
                });
            }
        });
    }

    @Override
    public void getCountryCategoryOfProduct(@NonNull String barcode, @NonNull String countryKey,
                                            @NonNull GetCountryCategoryOfProductCallback getCountryCategoryOfProductCallback) {
        // no-op in local
    }
}
