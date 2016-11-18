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

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fredericletellier.foodinspector.Injection;
import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.countrycategories.CountryCategoriesFragment;
import com.fredericletellier.foodinspector.countrycategories.CountryCategoriesPresenter;
import com.fredericletellier.foodinspector.data.source.LoaderProvider;
import com.fredericletellier.foodinspector.util.ActivityUtils;

public class ProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_BARCODE = "PRODUCT_BARCODE";
    public LoaderProvider loaderProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested product barcode
        String productBarcode = getIntent().getStringExtra(EXTRA_PRODUCT_BARCODE);

        ProductFragment productFragment = (ProductFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameProduct);

        if (productFragment == null) {
            productFragment = ProductFragment.newInstance(productBarcode);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    productFragment, R.id.contentFrameProduct
            );
        }

        loaderProvider = new LoaderProvider(getApplicationContext());

        // Create the presenter
        new ProductPresenter(
                productBarcode,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                productFragment
        );

        CountryCategoriesFragment countryCategoriesFragment = (CountryCategoriesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameCountryCategories);

        if (countryCategoriesFragment == null) {
            countryCategoriesFragment = countryCategoriesFragment.newInstance(productBarcode);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    countryCategoriesFragment, R.id.contentFrameCountryCategories
            );
        }

        loaderProvider = new LoaderProvider(getApplicationContext());

        // Create the presenter
        new CountryCategoriesPresenter(
                productBarcode,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                countryCategoriesFragment
        );
    }

}
