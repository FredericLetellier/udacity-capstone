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

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Product;

import java.util.List;

/**
 * Main entry point for accessing product data.
 */
public interface ProductDataSource {

    interface GetProductCallback {

        void onProductLoaded(Product product);

        void onError(Throwable throwable);

    }

    interface GetProductsCallback {

        void onProductsLoaded(List<Product> products);

        void onProductsUnfilled();

        void onError(Throwable throwable);

    }

    interface CheckExistProductCallback {

        void onProductExisted(long id);

        void onProductNotExisted();

    }

    interface AddProductCallback {

        void onProductAdded(String barcode);

        void onError();

    }

    interface UpdateProductCallback {

        void onProductUpdated(String barcode);

        void onError();

    }

    interface SaveProductCallback {

        void onProductSaved(String barcode);

        void onError();

    }

    interface ParseProductCallback {

        void onProductParsed();

        void onProductMustBeParsed(String parsableCategories);

        void onError();

    }

    interface UpdateProductBookmarkCallback {

        void onProductBookmarkUpdated();

        void onError();

    }

    void getProduct(@NonNull String barcode, @NonNull GetProductCallback getProductCallback);

    void getProducts(@NonNull String categoryKey, @NonNull String countryKey, @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts, @NonNull Integer numberOfProducts, @NonNull GetProductsCallback getProductsCallback);

    void checkExistProduct(@NonNull String barcode, @NonNull CheckExistProductCallback checkExistProductCallback);

    void addProduct(@NonNull Product product, @NonNull AddProductCallback addProductCallback);

    void updateProduct(@NonNull Product product, @NonNull UpdateProductCallback updateProductCallback);

    void saveProduct(@NonNull Product product, @NonNull SaveProductCallback saveProductCallback);

    void parseProduct(@NonNull String barcode, @NonNull ParseProductCallback parseProductCallback);

    void updateProductBookmark(@NonNull String barcode, @NonNull UpdateProductBookmarkCallback updateProductBookmarkCallback);

}
