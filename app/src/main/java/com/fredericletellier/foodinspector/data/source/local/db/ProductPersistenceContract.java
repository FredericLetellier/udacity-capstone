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
 * The contract used for the db to save the product locally.
 */
public final class ProductPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String CONTENT_PRODUCT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + ProductEntry.TABLE_NAME;
    public static final String CONTENT_PRODUCT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + ProductEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ProductPersistenceContract() {}

    public static Uri getBaseProductUri(String productId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_PRODUCT_ITEM_TYPE + SEPARATOR + productId);
    }

    /* Inner class that defines the table contents */
    public static abstract class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_PRODUCT_NAME = "productname";
        public static final String COLUMN_NAME_GENERIC_NAME = "genericname";
        public static final String COLUMN_NAME_MAIN_BRAND = "mainbrand";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_NUTRITION_GRADE = "nutritiongrade";
        public static final String COLUMN_NAME_CATEGORIES = "categories";
        public static final Uri CONTENT_PRODUCT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] PRODUCT_COLUMNS = new String[]{
                ProductPersistenceContract.ProductEntry._ID,
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_GENERIC_NAME,
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_MAIN_BRAND,
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_QUANTITY,
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_NUTRITION_GRADE,
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_CATEGORIES};

        public static Uri buildProductUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_PRODUCT_URI, id);
        }

        public static Uri buildProductUriWith(String id) {
            Uri uri = CONTENT_PRODUCT_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildProductUri() {
            return CONTENT_PRODUCT_URI.buildUpon().build();
        }

    }

}

