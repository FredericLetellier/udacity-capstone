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

package com.fredericletellier.foodinspector.product.countrycategories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.events.EventsContract;
import com.fredericletellier.foodinspector.product.ProductContract;

import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class CountryCategoriesFragment extends Fragment implements ProductContract.CategoryView {

    private ProductContract.Presenter mPresenter;

    protected View mProgressView;
    private Spinner mSpinner;


    public CountryCategoriesFragment() {
        // Requires empty public constructor
    }

    public static CountryCategoriesFragment newInstance() {
        return new CountryCategoriesFragment();
    }

    @Override
    public void setPresenter(ProductContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startCountryCategory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.countrycategories_frag, container, false);

        mProgressView = root.findViewById(android.R.id.progress);
        mSpinner = (Spinner) root.findViewById(R.id.spCountryCategories);

        return root;
    }

    @Override
    public void setLoadingIncator(Boolean active) {
        if (active){
            //show loading indicator
            mProgressView.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_in));
            mProgressView.setVisibility(View.VISIBLE);

        } else {
            // hide loading indicator
            mProgressView.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_out));
            mProgressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCategories(List<Category> categoryList) {
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this.getContext(), android.R.layout.simple_spinner_item, categoryList);
        // Specify the layout to use when the list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(dataAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                mPresenter.chooseNewCategory(category.getCategoryKey());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //no-op
            }
        });
    }
}
