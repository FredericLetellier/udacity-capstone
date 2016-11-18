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

package com.fredericletellier.foodinspector.suggestions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.fredericletellier.foodinspector.countrycategories.CountryCategoriesFragment;

public class SuggestionsFragment extends Fragment implements SuggestionsContract.View {

    @NonNull
    private static final String ARGUMENT_PRODUCT_BARCODE = "PRODUCT_BARCODE";
    private static final String ARGUMENT_COUNTRY_CATEGORY = "COUNTRY_CATEGORY";

    public SuggestionsFragment() {
        // Requires empty public constructor
    }

    public static SuggestionsFragment newInstance(String productBarcode, String countryCategory) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PRODUCT_BARCODE, productBarcode);
        arguments.putString(ARGUMENT_COUNTRY_CATEGORY, countryCategory);
        SuggestionsFragment fragment = new SuggestionsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void setPresenter(SuggestionsContract.Presenter presenter) {

    }
}
