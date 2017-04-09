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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Product;
import com.squareup.picasso.Picasso;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductFragment extends Fragment implements ProductContract.ProductView {

    private static final String TAG = ProductFragment.class.getName();

    private ProductContract.Presenter mPresenter;

    protected View mProgressView;
    private ImageView mIvImageBig;
    private TextView mTxProductName;
    private TextView mTxGenericName;
    private TextView mTxBrands;
    private TextView mTxQuantity;
    private ImageView mIvNutritionGrade;


    public ProductFragment() {
        // Requires empty public constructor
    }

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }


    @Override
    public void setPresenter(ProductContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startProduct();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_frag, container, false);

        mProgressView = root.findViewById(android.R.id.progress);
        mIvImageBig = (ImageView) root.findViewById(R.id.ivImageBig);
        mTxProductName = (TextView) root.findViewById(R.id.txProductName);
        mTxGenericName = (TextView) root.findViewById(R.id.txGenericName);
        mTxBrands = (TextView) root.findViewById(R.id.txBrands);
        mTxQuantity = (TextView) root.findViewById(R.id.txQuantity);
        mIvNutritionGrade = (ImageView) root.findViewById(R.id.ivNutritionGrade);
        return root;
    }

    @Override
    public void setLoadingIndicator(Boolean active) {
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
    public void showProduct(Product product) {
        Picasso.with(this.getContext())
                .load(product.getmImageFrontUrl())
                .into(mIvImageBig);

        mIvImageBig.setContentDescription(R.string.image_of_product + product.getmProductName());
        mTxProductName.setText(product.getmProductName());
        mTxGenericName.setText(product.getmGenericName());
        mTxBrands.setText(product.getmBrands());
        mTxQuantity.setText(product.getmQuantity());

        String rank = product.getmNutritionGrades();
        switch (rank){
            case ProductActivity.SUGGESTIONS_RANK_A:
                mIvNutritionGrade.setImageResource(R.mipmap.nutriscore_a);
                break;
            case ProductActivity.SUGGESTIONS_RANK_B:
                mIvNutritionGrade.setImageResource(R.mipmap.nutriscore_b);
                break;
            case ProductActivity.SUGGESTIONS_RANK_C:
                mIvNutritionGrade.setImageResource(R.mipmap.nutriscore_c);
                break;
            case ProductActivity.SUGGESTIONS_RANK_D:
                mIvNutritionGrade.setImageResource(R.mipmap.nutriscore_d);
                break;
            case ProductActivity.SUGGESTIONS_RANK_E:
                mIvNutritionGrade.setImageResource(R.mipmap.nutriscore_e);
                break;
            default:
                break;
        }
        mIvNutritionGrade.setContentDescription(R.string.image_of_rank + rank);
    }

    @Override
    public void showError() {
        Log.w(TAG, "Product cannot be load");
        Snackbar.make(getView(), R.string.error_product_cannot_be_load, Snackbar.LENGTH_LONG).show();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
