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

import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryTagPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.CountryCategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.LocalDbHelper;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.SuggestionPersistenceContract;

public class FoodInspectorContentProvider extends ContentProvider {

    private static final int PRODUCT = 100;
    private static final int PRODUCT_ITEM = 101;
    private static final int EVENT = 200;
    private static final int EVENT_ITEM = 201;
    private static final int CATEGORY = 300;
    private static final int CATEGORY_ITEM = 301;
    private static final int CATEGORYTAG = 400;
    private static final int CATEGORYTAG_ITEM = 401;
    private static final int COUNTRYCATEGORY = 500;
    private static final int COUNTRYCATEGORY_ITEM = 501;
    private static final int SUGGESTION = 600;
    private static final int SUGGESTION_ITEM = 601;

    //TODO Reuse
    // private static final int PRODUCTSINCATEGORY_JOIN_PRODUCT = 102;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private LocalDbHelper mLocalDbHelper;

    //TODO Reuse
    // private static final SQLiteQueryBuilder sProductsInCategoryQueryBuilder;

    //TODO Reuse
    //static{
    //    sProductsInCategoryQueryBuilder = new SQLiteQueryBuilder();
    //
    //    sProductsInCategoryQueryBuilder.setTables(
    //            ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME + " INNER JOIN " +
    //                    ProductPersistenceContract.ProductEntry.TABLE_NAME +
    //                    " ON " + ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME +
    //                    "." + ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.COLUMN_NAME_PRODUCT_ID +
    //                    " = " + ProductPersistenceContract.ProductEntry.TABLE_NAME +
    //                    "." + ProductPersistenceContract.ProductEntry._ID);
    //}

    //        matcher.addURI(authority, ProductPersistenceContract.ProductEntry.PRODUCTSINCATEGORY_JOIN_PRODUCT, PRODUCTSINCATEGORY_JOIN_PRODUCT);


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProductPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ProductPersistenceContract.ProductEntry.TABLE_NAME, PRODUCT);
        matcher.addURI(authority, ProductPersistenceContract.ProductEntry.TABLE_NAME + "/*", PRODUCT_ITEM);

        matcher.addURI(authority, EventPersistenceContract.EventEntry.TABLE_NAME, EVENT);
        matcher.addURI(authority, EventPersistenceContract.EventEntry.TABLE_NAME + "/*", EVENT_ITEM);

        matcher.addURI(authority, CategoryPersistenceContract.CategoryEntry.TABLE_NAME, CATEGORY);
        matcher.addURI(authority, CategoryPersistenceContract.CategoryEntry.TABLE_NAME + "/*", CATEGORY_ITEM);

        matcher.addURI(authority, CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME, CATEGORYTAG);
        matcher.addURI(authority, CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME + "/*", CATEGORYTAG_ITEM);

        matcher.addURI(authority, CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME, COUNTRYCATEGORY);
        matcher.addURI(authority, CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME + "/*", COUNTRYCATEGORY_ITEM);

        matcher.addURI(authority, SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME, SUGGESTION);
        matcher.addURI(authority, SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME + "/*", SUGGESTION_ITEM);

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
            case PRODUCT:
                return ProductPersistenceContract.CONTENT_PRODUCT_TYPE;
            case PRODUCT_ITEM:
                return ProductPersistenceContract.CONTENT_PRODUCT_ITEM_TYPE;
            case EVENT:
                return EventPersistenceContract.CONTENT_EVENT_TYPE;
            case EVENT_ITEM:
                return EventPersistenceContract.CONTENT_EVENT_ITEM_TYPE;
            case CATEGORY:
                return CategoryPersistenceContract.CONTENT_CATEGORY_TYPE;
            case CATEGORY_ITEM:
                return CategoryPersistenceContract.CONTENT_CATEGORY_ITEM_TYPE;
            case CATEGORYTAG:
                return CategoryTagPersistenceContract.CONTENT_CATEGORYTAG_TYPE;
            case CATEGORYTAG_ITEM:
                return CategoryTagPersistenceContract.CONTENT_CATEGORYTAG_ITEM_TYPE;
            case COUNTRYCATEGORY:
                return CountryCategoryPersistenceContract.CONTENT_COUNTRYCATEGORY_TYPE;
            case COUNTRYCATEGORY_ITEM:
                return CountryCategoryPersistenceContract.CONTENT_COUNTRYCATEGORY_ITEM_TYPE;
            case SUGGESTION:
                return SuggestionPersistenceContract.CONTENT_SUGGESTION_TYPE;
            case SUGGESTION_ITEM:
                return SuggestionPersistenceContract.CONTENT_SUGGESTION_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case PRODUCT:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        ProductPersistenceContract.ProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PRODUCT_ITEM:
                String[] where_product = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        ProductPersistenceContract.ProductEntry.TABLE_NAME,
                        projection,
                        ProductPersistenceContract.ProductEntry._ID + " = ?",
                        where_product,
                        null,
                        null,
                        sortOrder
                );
                break;
            // TODO Reuse
            //case PRODUCTSINCATEGORY_JOIN_PRODUCT:
            //    retCursor = sProductsInCategoryQueryBuilder.query(mLocalDbHelper.getReadableDatabase(),
            //            projection,
            //            selection,
            //            selectionArgs,
            //            null,
            //            null,
            //            sortOrder
            //    );
            //    break;
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
            case CATEGORY:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CategoryPersistenceContract.CategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORY_ITEM:
                String[] where_category = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CategoryPersistenceContract.CategoryEntry.TABLE_NAME,
                        projection,
                        CategoryPersistenceContract.CategoryEntry._ID + " = ?",
                        where_category,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORYTAG:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORYTAG_ITEM:
                String[] where_categoryTag = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME,
                        projection,
                        CategoryTagPersistenceContract.CategoryTagEntry._ID + " = ?",
                        where_categoryTag,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COUNTRYCATEGORY:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COUNTRYCATEGORY_ITEM:
                String[] where_countryCategory = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME,
                        projection,
                        CountryCategoryPersistenceContract.CountryCategoryEntry._ID + " = ?",
                        where_countryCategory,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SUGGESTION:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SUGGESTION_ITEM:
                String[] where_suggestion = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME,
                        projection,
                        SuggestionPersistenceContract.SuggestionEntry._ID + " = ?",
                        where_suggestion,
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
            case PRODUCT:
                exists = db.query(
                        ProductPersistenceContract.ProductEntry.TABLE_NAME,
                        new String[]{ProductPersistenceContract.ProductEntry._ID},
                        ProductPersistenceContract.ProductEntry._ID + " = ?",
                        new String[]{values.getAsString(ProductPersistenceContract.ProductEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            ProductPersistenceContract.ProductEntry.TABLE_NAME, values,
                            ProductPersistenceContract.ProductEntry._ID + " = ?",
                            new String[]{values.getAsString(ProductPersistenceContract.ProductEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = ProductPersistenceContract.ProductEntry.buildProductUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(ProductPersistenceContract.ProductEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = ProductPersistenceContract.ProductEntry.buildProductUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
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
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            case CATEGORY:
                exists = db.query(
                        CategoryPersistenceContract.CategoryEntry.TABLE_NAME,
                        new String[]{CategoryPersistenceContract.CategoryEntry._ID},
                        CategoryPersistenceContract.CategoryEntry._ID + " = ?",
                        new String[]{values.getAsString(CategoryPersistenceContract.CategoryEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            CategoryPersistenceContract.CategoryEntry.TABLE_NAME, values,
                            CategoryPersistenceContract.CategoryEntry._ID + " = ?",
                            new String[]{values.getAsString(CategoryPersistenceContract.CategoryEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = CategoryPersistenceContract.CategoryEntry.buildCategoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(CategoryPersistenceContract.CategoryEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = CategoryPersistenceContract.CategoryEntry.buildCategoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            case CATEGORYTAG:
                exists = db.query(
                        CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME,
                        new String[]{CategoryTagPersistenceContract.CategoryTagEntry._ID},
                        CategoryTagPersistenceContract.CategoryTagEntry._ID + " = ?",
                        new String[]{values.getAsString(CategoryTagPersistenceContract.CategoryTagEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME, values,
                            CategoryTagPersistenceContract.CategoryTagEntry._ID + " = ?",
                            new String[]{values.getAsString(CategoryTagPersistenceContract.CategoryTagEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = CategoryTagPersistenceContract.CategoryTagEntry.buildCategoryTagUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            case COUNTRYCATEGORY:
                exists = db.query(
                        CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME,
                        new String[]{CountryCategoryPersistenceContract.CountryCategoryEntry._ID},
                        CountryCategoryPersistenceContract.CountryCategoryEntry._ID + " = ?",
                        new String[]{values.getAsString(CountryCategoryPersistenceContract.CountryCategoryEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME, values,
                            CountryCategoryPersistenceContract.CountryCategoryEntry._ID + " = ?",
                            new String[]{values.getAsString(CountryCategoryPersistenceContract.CountryCategoryEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = CountryCategoryPersistenceContract.CountryCategoryEntry.buildCountryCategoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = CountryCategoryPersistenceContract.CountryCategoryEntry.buildCountryCategoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            case SUGGESTION:
                exists = db.query(
                        SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME,
                        new String[]{SuggestionPersistenceContract.SuggestionEntry._ID},
                        SuggestionPersistenceContract.SuggestionEntry._ID + " = ?",
                        new String[]{values.getAsString(SuggestionPersistenceContract.SuggestionEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME, values,
                            SuggestionPersistenceContract.SuggestionEntry._ID + " = ?",
                            new String[]{values.getAsString(SuggestionPersistenceContract.SuggestionEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = SuggestionPersistenceContract.SuggestionEntry.buildSuggestionUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = SuggestionPersistenceContract.SuggestionEntry.buildSuggestionUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
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
            case PRODUCT:
                rowsDeleted = db.delete(
                        ProductPersistenceContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EVENT:
                rowsDeleted = db.delete(
                        EventPersistenceContract.EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORY:
                rowsDeleted = db.delete(
                        CategoryPersistenceContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORYTAG:
                rowsDeleted = db.delete(
                        CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case COUNTRYCATEGORY:
                rowsDeleted = db.delete(
                        CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case SUGGESTION:
                rowsDeleted = db.delete(
                        SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME, selection, selectionArgs);
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
            case PRODUCT:
                rowsUpdated = db.update(ProductPersistenceContract.ProductEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case EVENT:
                rowsUpdated = db.update(EventPersistenceContract.EventEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case CATEGORY:
                rowsUpdated = db.update(CategoryPersistenceContract.CategoryEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case CATEGORYTAG:
                rowsUpdated = db.update(CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case COUNTRYCATEGORY:
                rowsUpdated = db.update(CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case SUGGESTION:
                rowsUpdated = db.update(SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME, values, selection,
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