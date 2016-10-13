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

package com.fredericletellier.foodinspector.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a remote db
 */
public class EventRemoteDataSource implements EventDataSource {

    private static EventRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private EventRemoteDataSource() {
    }

    public static EventRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventRemoteDataSource();
        }
        return INSTANCE;
    }

    //TODO COMPLETE
    //###REMOTE
	//Si il y a du réseau
	//	Je recupere les informations du produit via l'API
	//	Si erreur lors de la récupération
	//		Je met à jour le code erreur de l'evenement sans changer le timestamp
	//	Si recupération ok
	//		J'inscris le produit en base
	//		Je met à jour le code produit de l'evenement sans changer le timestamp
	//Si pas de réseau
	//	rien
    @Override
    public void getEvents(@NonNull List<Event> events, @Nullable GetEventsCallback callback){
        checkNotNull(events);

        for (Event event : events){

        }
    }

    //TODO COMPLETE
    //###REMOTE
	//Si il y a du réseau
	//	Je recupere les informations du produit via l'API
	//	Si erreur lors de la récupération
	//		Creation / Mise à jour du code erreur et timestamp de l'evenement
	//	Si recupération ok
	//		J'inscris le produit en base
	//		Creation / Mise à jour du code produit et timestamp de l'evenement
	//		callback = ok
	//Si pas de réseau
	//	Creation / Mise à jour du code erreur et timestamp de l'evenement
    @Override
    public void addEvent(@NonNull String productId, @NonNull AddEventCallback callback){
        checkNotNull(productId);
        checkNotNull(callback);

    }

    @Override
    public void updateFavoriteFieldEvent(@NonNull String productId){
        checkNotNull(productId);
        //no-op in remote
    }

}