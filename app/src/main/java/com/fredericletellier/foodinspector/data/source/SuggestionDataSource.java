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

package com.fredericletellier.foodinspector.data.source;


import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Suggestion;

/**
 * Main entry point for accessing suggestion data.
 */
public interface SuggestionDataSource {

    interface GetSuggestionCallback {

        void onSuggestionLoaded(Suggestion suggestion);

        void onError(Exception exception);

    }

    interface AddSuggestionCallback {

        void onSuggestionAdded(Suggestion suggestion);

        void onError(Exception exception);

    }

    interface UpdateSuggestionCallback {

        void onSuggestionUpdated(Suggestion suggestion);

        void onError(Exception exception);

    }

    interface SaveSuggestionCallback {

        void onSuggestionSaved(Suggestion suggestion);

        void onError(Exception exception);

    }

    void getSuggestion(@NonNull Suggestion suggestion, @NonNull GetSuggestionCallback getSuggestionCallback);

    void addSuggestion(@NonNull Suggestion suggestion, @NonNull AddSuggestionCallback addSuggestionCallback);

    void updateSuggestion(@NonNull Suggestion suggestion, @NonNull UpdateSuggestionCallback updateSuggestionCallback);

    void saveSuggestion(@NonNull Suggestion suggestion, @NonNull SaveSuggestionCallback saveSuggestionCallback);

}
