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
import android.view.View;

import com.fredericletellier.foodinspector.Injection;
import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.product.countrycategories.CountryCategoriesFragment;
import com.fredericletellier.foodinspector.product.suggestions.SuggestionsFragment;
import com.fredericletellier.foodinspector.util.ActivityUtils;

public class ProductActivity extends AppCompatActivity implements ProductContract.ActivityView {

    public static final String ARGUMENT_PRODUCT_BARCODE = "PRODUCT_BARCODE";
    public static final String SUGGESTIONS_RANK_A = "A";
    public static final String SUGGESTIONS_RANK_B = "B";
    public static final String SUGGESTIONS_RANK_C = "C";
    public static final String SUGGESTIONS_RANK_D = "D";
    public static final String SUGGESTIONS_RANK_E = "E";

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
        productBarcode = getIntent().getStringExtra(ARGUMENT_PRODUCT_BARCODE);

        /* Create fragment Product */
        ProductFragment productFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameProduct);
        if (productFragment == null) {
            productFragment = ProductFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), productFragment, R.id.contentFrameProduct);
        }

        /* Create fragment CountryCategories */
        CountryCategoriesFragment countryCategoriesFragment = (CountryCategoriesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameCountryCategories);
        if (countryCategoriesFragment == null) {
            countryCategoriesFragment = CountryCategoriesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), countryCategoriesFragment, R.id.contentFrameCountryCategories
            );
        }

        /* Create fragment Suggestions, rank A */
        SuggestionsFragment suggestionsFragmentA = (SuggestionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameSuggestionsA);
        if (suggestionsFragmentA == null) {
            suggestionsFragmentA = SuggestionsFragment.newInstance(SUGGESTIONS_RANK_A);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), suggestionsFragmentA, R.id.contentFrameSuggestionsA);
        }

        /* Create fragment Suggestions, rank B */
        SuggestionsFragment suggestionsFragmentB = (SuggestionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameSuggestionsB);
        if (suggestionsFragmentB == null) {
            suggestionsFragmentB = SuggestionsFragment.newInstance(SUGGESTIONS_RANK_B);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), suggestionsFragmentB, R.id.contentFrameSuggestionsB);
        }

        /* Create fragment Suggestions, rank C */
        SuggestionsFragment suggestionsFragmentC = (SuggestionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameSuggestionsC);
        if (suggestionsFragmentC == null) {
            suggestionsFragmentC = SuggestionsFragment.newInstance(SUGGESTIONS_RANK_C);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), suggestionsFragmentC, R.id.contentFrameSuggestionsC);
        }

        /* Create fragment Suggestions, rank D */
        SuggestionsFragment suggestionsFragmentD = (SuggestionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameSuggestionsD);
        if (suggestionsFragmentD == null) {
            suggestionsFragmentD = SuggestionsFragment.newInstance(SUGGESTIONS_RANK_D);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), suggestionsFragmentD, R.id.contentFrameSuggestionsD);
        }

        /* Create fragment Suggestions, rank E */
        SuggestionsFragment suggestionsFragmentE = (SuggestionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameSuggestionsE);
        if (suggestionsFragmentE == null) {
            suggestionsFragmentE = SuggestionsFragment.newInstance(SUGGESTIONS_RANK_E);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),suggestionsFragmentE, R.id.contentFrameSuggestionsE);
        }

        // Create the presenter
        new ProductPresenter(
                productBarcode,
                Injection.provideFoodInspectorRepository(this),
                this,
                productFragment,
                countryCategoriesFragment,
                suggestionsFragmentA,
                suggestionsFragmentB,
                suggestionsFragmentC,
                suggestionsFragmentD,
                suggestionsFragmentE
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void hideCategoryView() {
        this.findViewById(R.id.contentFrameCountryCategories).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSuggestionsViewA() {
        this.findViewById(R.id.contentFrameSuggestionsA).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSuggestionsViewB() {
        this.findViewById(R.id.contentFrameSuggestionsB).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSuggestionsViewC() {
        this.findViewById(R.id.contentFrameSuggestionsC).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSuggestionsViewD() {
        this.findViewById(R.id.contentFrameSuggestionsD).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideSuggestionsViewE() {
        this.findViewById(R.id.contentFrameSuggestionsE).setVisibility(View.INVISIBLE);
    }

    @Override
    public void showCategoryView() {
        this.findViewById(R.id.contentFrameCountryCategories).setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionsViewA() {
        this.findViewById(R.id.contentFrameSuggestionsA).setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionsViewB() {
        this.findViewById(R.id.contentFrameSuggestionsB).setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionsViewC() {
        this.findViewById(R.id.contentFrameSuggestionsC).setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionsViewD() {
        this.findViewById(R.id.contentFrameSuggestionsD).setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionsViewE() {
        this.findViewById(R.id.contentFrameSuggestionsE).setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(ProductContract.Presenter presenter) {
        //no-op
    }
}
