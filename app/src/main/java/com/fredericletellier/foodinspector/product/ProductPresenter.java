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

package com.fredericletellier.foodinspector.product;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.source.FoodInspectorRepository;
import com.fredericletellier.foodinspector.data.source.LoaderProvider;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductPresenter implements ProductContract.Presenter, FoodInspectorRepository.LoadDataCallback,
        LoaderManager.LoaderCallbacks<Cursor>  {

    public final static int TASK_LOADER = 2;

    @NonNull
    private final FoodInspectorRepository mFoodInspectorRepository;
    private ProductContract.View mProductView;
    private LoaderProvider mLoaderProvider;
    private LoaderManager mLoaderManager;
    private Product mProduct;
    private String mProductBarcode;

    public ProductPresenter(@NonNull String productBarcode,
                               @NonNull LoaderProvider loaderProvider,
                               @NonNull LoaderManager loaderManager,
                               @NonNull FoodInspectorRepository foodInspectorRepository,
                               @NonNull ProductContract.View productView) {
        mProductBarcode = checkNotNull(productBarcode, "productBarcode cannot be null!");
        mLoaderProvider = checkNotNull(loaderProvider, "loaderProvider cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "loaderManager cannot be null!");
        mFoodInspectorRepository = checkNotNull(foodInspectorRepository, "foodInspectorRepository cannot be null!");
        mProductView = checkNotNull(productView, "productView cannot be null!");
        mProductView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDataLoaded(Cursor data) {

    }

    @Override
    public void onDataEmpty() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onDataReset() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
