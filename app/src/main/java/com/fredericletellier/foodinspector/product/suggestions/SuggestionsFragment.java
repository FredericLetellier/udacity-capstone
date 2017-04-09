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

package com.fredericletellier.foodinspector.product.suggestions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.product.ProductActivity;
import com.fredericletellier.foodinspector.product.ProductContract;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SuggestionsFragment extends Fragment implements ProductContract.SuggestionsView {

    @NonNull
    private static final String ARGUMENT_SUGGESTIONS_RANK = "SUGGESTIONS_RANK";

    private ProductContract.Presenter mPresenter;

    protected View mProgressView;
    private RecyclerView mSuggestionRecyclerView;



    public SuggestionsFragment() {
        // Requires empty public constructor
    }

    public static SuggestionsFragment newInstance(String rank) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_SUGGESTIONS_RANK, rank);
        SuggestionsFragment fragment = new SuggestionsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(ProductContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startSuggestion(this.getArguments().getString(ARGUMENT_SUGGESTIONS_RANK));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.suggestions_frag, container, false);

        mProgressView = root.findViewById(android.R.id.progress);
        TextView mTxNutritionGrade = (TextView) root.findViewById(R.id.txNutritionGrade);
        mSuggestionRecyclerView = (RecyclerView) root.findViewById(R.id.rvSuggestions);

        mTxNutritionGrade.setText(this.getArguments().getString(ARGUMENT_SUGGESTIONS_RANK));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mSuggestionRecyclerView.setHasFixedSize(true);
        mSuggestionRecyclerView.setLayoutManager(linearLayoutManager);
        mSuggestionRecyclerView.setAdapter(null);

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
    public void showProducts(List<Product> products) {
        mSuggestionRecyclerView.setAdapter(new SuggestionAdapter(products));
    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView ivImageSmall;
        public TextView tvTitle;
        public TextView tvSubtitle;

        public SuggestionViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            ivImageSmall = (ImageView) view.findViewById(R.id.ivImageSmall);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) view.findViewById(R.id.tvSubtitle);

            cardView.setTag(this);
        }
    }

    public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {
        private List<Product> list;

        public SuggestionAdapter(List<Product> data) {
            list = data;
        }

        @Override
        public SuggestionViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false);
            return new SuggestionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SuggestionViewHolder holder, int position) {

            Product product = list.get(position);

            Picasso.with(getContext())
                    .load(product.getmImageFrontSmallUrl())
                    .into(holder.ivImageSmall);

            holder.ivImageSmall.setContentDescription(R.string.image_of_suggestion + product.getmProductName());

            holder.tvTitle.setText(product.getmProductName());
            holder.tvSubtitle.setText(product.getmQuantity());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuggestionViewHolder holder = (SuggestionViewHolder)(v.getTag());
                    String barcode = list.get(holder.getAdapterPosition()).getmBarcode();

                    Intent intent = new Intent(getContext(), ProductActivity.class);
                    intent.putExtra(ProductActivity.ARGUMENT_PRODUCT_BARCODE, barcode);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
