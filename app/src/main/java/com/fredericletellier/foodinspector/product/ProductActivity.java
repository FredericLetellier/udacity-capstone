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
import com.fredericletellier.foodinspector.suggestions.SuggestionsFilter;
import com.fredericletellier.foodinspector.suggestions.SuggestionsFilterType;
import com.fredericletellier.foodinspector.suggestions.SuggestionsFragment;
import com.fredericletellier.foodinspector.suggestions.SuggestionsPresenter;
import com.fredericletellier.foodinspector.util.ActivityUtils;

public class ProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_BARCODE = "PRODUCT_BARCODE";
    private static final String CURRENT_COUNTRY_CATEGORY = "CURRENT_COUNTRY_CATEGORY";
    public LoaderProvider loaderProvider;
    public String productBarcode;
    public String countryCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested product barcode
        productBarcode = getIntent().getStringExtra(EXTRA_PRODUCT_BARCODE);

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

        // Create the presenter
        new CountryCategoriesPresenter(
                productBarcode,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                countryCategoriesFragment
        );

        if (savedInstanceState != null) {
            countryCategory = (String) savedInstanceState.getSerializable(CURRENT_COUNTRY_CATEGORY);
            initSuggestions();
        }
    }

    private void initSuggestions(){
        SuggestionsFragment suggestionsFragmentA = (SuggestionsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameSuggestionsA);

        if (suggestionsFragmentA == null) {
            suggestionsFragmentA = suggestionsFragmentA.newInstance(productBarcode, countryCategory);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    suggestionsFragmentA, R.id.contentFrameSuggestionsA
            );
        }

        SuggestionsFilter suggestionsFilterA = SuggestionsFilter.from(SuggestionsFilterType.NUTRITION_GRADE_A);

        // Create the presenter
        new SuggestionsPresenter(
                productBarcode,
                countryCategory,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                suggestionsFragmentA,
                suggestionsFilterA
        );

        SuggestionsFragment suggestionsFragmentB = (SuggestionsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameSuggestionsB);

        if (suggestionsFragmentB == null) {
            suggestionsFragmentB = suggestionsFragmentB.newInstance(productBarcode, countryCategory);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    suggestionsFragmentB, R.id.contentFrameSuggestionsB
            );
        }

        SuggestionsFilter suggestionsFilterB = SuggestionsFilter.from(SuggestionsFilterType.NUTRITION_GRADE_B);

        // Create the presenter
        new SuggestionsPresenter(
                productBarcode,
                countryCategory,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                suggestionsFragmentB,
                suggestionsFilterB
        );

        SuggestionsFragment suggestionsFragmentC = (SuggestionsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameSuggestionsC);

        if (suggestionsFragmentC == null) {
            suggestionsFragmentC = suggestionsFragmentC.newInstance(productBarcode, countryCategory);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    suggestionsFragmentC, R.id.contentFrameSuggestionsC
            );
        }

        SuggestionsFilter suggestionsFilterC = SuggestionsFilter.from(SuggestionsFilterType.NUTRITION_GRADE_C);

        // Create the presenter
        new SuggestionsPresenter(
                productBarcode,
                countryCategory,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                suggestionsFragmentC,
                suggestionsFilterC
        );

        SuggestionsFragment suggestionsFragmentD = (SuggestionsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameSuggestionsD);

        if (suggestionsFragmentD == null) {
            suggestionsFragmentD = suggestionsFragmentD.newInstance(productBarcode, countryCategory);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    suggestionsFragmentD, R.id.contentFrameSuggestionsD
            );
        }

        SuggestionsFilter suggestionsFilterD = SuggestionsFilter.from(SuggestionsFilterType.NUTRITION_GRADE_D);

        // Create the presenter
        new SuggestionsPresenter(
                productBarcode,
                countryCategory,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                suggestionsFragmentD,
                suggestionsFilterD
        );

        SuggestionsFragment suggestionsFragmentE = (SuggestionsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrameSuggestionsE);

        if (suggestionsFragmentE == null) {
            suggestionsFragmentE = suggestionsFragmentA.newInstance(productBarcode, countryCategory);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    suggestionsFragmentE, R.id.contentFrameSuggestionsE
            );
        }

        SuggestionsFilter suggestionsFilterE = SuggestionsFilter.from(SuggestionsFilterType.NUTRITION_GRADE_E);

        // Create the presenter
        new SuggestionsPresenter(
                productBarcode,
                countryCategory,
                loaderProvider,
                getSupportLoaderManager(),
                Injection.provideFoodInspectorRepository(this),
                suggestionsFragmentE,
                suggestionsFilterE
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
