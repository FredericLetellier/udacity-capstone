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

import com.fredericletellier.foodinspector.data.source.local.db.CategoriesInProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.CategoryPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.LocalDbHelper;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;
import com.fredericletellier.foodinspector.data.source.local.db.ProductsInCategoryPersistenceContract;

public class FoodInspectorContentProvider extends ContentProvider {

    private static final int PRODUCT = 100;
    private static final int PRODUCT_ITEM = 101;
    private static final int PRODUCTSINCATEGORY_JOIN_PRODUCT = 102;
    private static final int CATEGORY = 200;
    private static final int CATEGORY_ITEM = 201;
    private static final int EVENT = 300;
    private static final int EVENT_ITEM = 301;
    private static final int PRODUCTSINCATEGORY = 400;
    private static final int PRODUCTSINCATEGORY_ITEM = 401;
    private static final int CATEGORIESINPRODUCT = 500;
    private static final int CATEGORIESINPRODUCT_ITEM = 501;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private LocalDbHelper mLocalDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ProductPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ProductPersistenceContract.ProductEntry.TABLE_NAME, PRODUCT);
        matcher.addURI(authority, ProductPersistenceContract.ProductEntry.TABLE_NAME + "/*", PRODUCT_ITEM);
        matcher.addURI(authority, ProductPersistenceContract.ProductEntry.PRODUCTSINCATEGORY_JOIN_PRODUCT, PRODUCTSINCATEGORY_JOIN_PRODUCT);
        matcher.addURI(authority, CategoryPersistenceContract.CategoryEntry.TABLE_NAME, CATEGORY);
        matcher.addURI(authority, CategoryPersistenceContract.CategoryEntry.TABLE_NAME + "/*", CATEGORY_ITEM);
        matcher.addURI(authority, EventPersistenceContract.EventEntry.TABLE_NAME, EVENT);
        matcher.addURI(authority, EventPersistenceContract.EventEntry.TABLE_NAME + "/*", EVENT_ITEM);
        matcher.addURI(authority, ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME, PRODUCTSINCATEGORY);
        matcher.addURI(authority, ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME + "/*", PRODUCTSINCATEGORY_ITEM);
        matcher.addURI(authority, CategoriesInProductPersistenceContract.CategoriesInProductEntry.TABLE_NAME, CATEGORIESINPRODUCT);
        matcher.addURI(authority, CategoriesInProductPersistenceContract.CategoriesInProductEntry.TABLE_NAME + "/*", CATEGORIESINPRODUCT_ITEM);

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
            case CATEGORY:
                return CategoryPersistenceContract.CONTENT_CATEGORY_TYPE;
            case CATEGORY_ITEM:
                return CategoryPersistenceContract.CONTENT_CATEGORY_ITEM_TYPE;
            case EVENT:
                return EventPersistenceContract.CONTENT_EVENT_TYPE;
            case EVENT_ITEM:
                return EventPersistenceContract.CONTENT_EVENT_ITEM_TYPE;
            case PRODUCTSINCATEGORY:
                return ProductsInCategoryPersistenceContract.CONTENT_PRODUCTSINCATEGORY_TYPE;
            case PRODUCTSINCATEGORY_ITEM:
                return ProductsInCategoryPersistenceContract.CONTENT_PRODUCTSINCATEGORY_ITEM_TYPE;
            case CATEGORIESINPRODUCT:
                return CategoriesInProductPersistenceContract.CONTENT_CATEGORIESINPRODUCT_TYPE;
            case CATEGORIESINPRODUCT_ITEM:
                return CategoriesInProductPersistenceContract.CONTENT_CATEGORIESINPRODUCT_ITEM_TYPE;
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
            case PRODUCTSINCATEGORY_JOIN_PRODUCT:
                //TODO JOIN two tables ProductsInCategories and Product
                retCursor = null;
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
            case PRODUCTSINCATEGORY:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PRODUCTSINCATEGORY_ITEM:
                String[] where_productsincategory = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME,
                        projection,
                        ProductsInCategoryPersistenceContract.ProductsInCategoryEntry._ID + " = ?",
                        where_productsincategory,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORIESINPRODUCT:
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CategoriesInProductPersistenceContract.CategoriesInProductEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CATEGORIESINPRODUCT_ITEM:
                String[] where_categoriesinproduct = {uri.getLastPathSegment()};
                retCursor = mLocalDbHelper.getReadableDatabase().query(
                        CategoriesInProductPersistenceContract.CategoriesInProductEntry.TABLE_NAME,
                        projection,
                        CategoriesInProductPersistenceContract.CategoriesInProductEntry._ID + " = ?",
                        where_categoriesinproduct,
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
            case PRODUCTSINCATEGORY:
                exists = db.query(
                        ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME,
                        new String[]{ProductsInCategoryPersistenceContract.ProductsInCategoryEntry._ID},
                        ProductsInCategoryPersistenceContract.ProductsInCategoryEntry._ID + " = ?",
                        new String[]{values.getAsString(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry._ID)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME, values,
                            ProductsInCategoryPersistenceContract.ProductsInCategoryEntry._ID + " = ?",
                            new String[]{values.getAsString(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry._ID)}
                    );
                    if (_id > 0) {
                        returnUri = ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.buildProductsInCategoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.buildProductsInCategoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            case CATEGORIESINPRODUCT:
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
            case CATEGORY:
                rowsDeleted = db.delete(
                        CategoryPersistenceContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EVENT:
                rowsDeleted = db.delete(
                        EventPersistenceContract.EventEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCTSINCATEGORY:
                rowsDeleted = db.delete(
                        ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORIESINPRODUCT:
                rowsDeleted = db.delete(
                        CategoriesInProductPersistenceContract.CategoriesInProductEntry.TABLE_NAME, selection, selectionArgs);
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
            case CATEGORY:
                rowsUpdated = db.update(CategoryPersistenceContract.CategoryEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case EVENT:
                rowsUpdated = db.update(EventPersistenceContract.EventEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case PRODUCTSINCATEGORY:
                rowsUpdated = db.update(ProductsInCategoryPersistenceContract.ProductsInCategoryEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            case CATEGORIESINPRODUCT:
                rowsUpdated = db.update(CategoriesInProductPersistenceContract.CategoriesInProductEntry.TABLE_NAME, values, selection,
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