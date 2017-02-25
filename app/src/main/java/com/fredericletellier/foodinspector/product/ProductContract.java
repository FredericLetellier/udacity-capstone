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

import com.fredericletellier.foodinspector.BasePresenter;
import com.fredericletellier.foodinspector.BaseView;
import com.fredericletellier.foodinspector.data.Category;
import com.fredericletellier.foodinspector.data.Product;

import java.util.List;


public interface ProductContract {

    interface ActivityView extends BaseView<Presenter> {
        void hideCategoryView();
        void hideSuggestionsViewA();
        void hideSuggestionsViewB();
        void hideSuggestionsViewC();
        void hideSuggestionsViewD();
        void hideSuggestionsViewE();

        void showCategoryView();
        void showSuggestionsViewA();
        void showSuggestionsViewB();
        void showSuggestionsViewC();
        void showSuggestionsViewD();
        void showSuggestionsViewE();
    }

    interface ProductView extends BaseView<Presenter> {
        void setLoadingIncator(Boolean active);
        void showProduct(Product product);
        void showError();
    }

    interface CategoryView extends BaseView<Presenter> {
        void setLoadingIncator(Boolean active);
        void showCategories(List<Category> categoryList);
    }

    interface SuggestionsView extends BaseView<Presenter> {
        void setLoadingIncator(Boolean active);
        void showProducts(List<Product> products);
    }

    interface Presenter extends BasePresenter {
        void startProduct();
        void startCountryCategory();
        void startSuggestion(String rank);
        void chooseNewCategory(String parsableCategory);
    }
}