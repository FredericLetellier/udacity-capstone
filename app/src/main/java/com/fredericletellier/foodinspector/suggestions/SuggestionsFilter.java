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

import com.fredericletellier.foodinspector.BuildConfig;

public class SuggestionsFilter {
    public final static String KEY_SUGGESTIONS_FILTER = BuildConfig.APPLICATION_ID + "SUGGESTIONS_FILTER";
    private SuggestionsFilterType suggestionsFilterType = SuggestionsFilterType.NUTRITION_GRADE_A;
    private Bundle filterExtras;

    protected SuggestionsFilter(Bundle extras) {
        this.filterExtras = extras;
        this.suggestionsFilterType = (SuggestionsFilterType) extras.getSerializable(KEY_SUGGESTIONS_FILTER);
    }

    public static SuggestionsFilter from(SuggestionsFilterType suggestionsFilterType){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SUGGESTIONS_FILTER, suggestionsFilterType);
        return new SuggestionsFilter(bundle);
    }

    public SuggestionsFilterType getSuggestionsFilterType() {
        return suggestionsFilterType;
    }

    public Bundle getFilterExtras() {
        return filterExtras;
    }
}
