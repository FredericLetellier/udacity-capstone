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

package com.fredericletellier.foodinspector.data.source;

import android.content.ContentValues;

import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;

public class ProductValues {

    public static ContentValues from(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductPersistenceContract.ProductEntry._ID, product.getId());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_BARCODE, product.getBarcode());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSED, product.isParsed() ? 1 : 0);
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_BOOKMARKED, product.isBookmarked() ? 1 : 0);
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_GENERIC_NAME, product.getGenericName());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, product.getProductName());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_QUANTITY, product.getQuantity());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_NUTRITION_GRADES, product.getNutritionGrades());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSABLE_CATEGORIES, product.getParsableCategories());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_BRANDS, product.getBrands());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_IMAGE_FRONT_SMALL_URL, product.getImageFrontSmallUrl());
        values.put(ProductPersistenceContract.ProductEntry.COLUMN_NAME_IMAGE_FRONT_URL, product.getImageFrontUrl());
        return values;
    }

}
