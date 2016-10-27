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

package com.fredericletellier.foodinspector.events;

import android.os.Bundle;

import com.fredericletellier.foodinspector.BuildConfig;

public class EventsFilter {

    public final static String KEY_EVENTS_FILTER = BuildConfig.APPLICATION_ID + "EVENTS_FILTER";
    private EventsFilterType eventsFilterType = EventsFilterType.ALL_EVENTS;
    private Bundle filterExtras;

    protected EventsFilter(Bundle extras) {
        this.filterExtras = extras;
        this.eventsFilterType = (EventsFilterType) extras.getSerializable(KEY_EVENTS_FILTER);
    }

    public static EventsFilter from(EventsFilterType eventsFilterType){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EVENTS_FILTER, eventsFilterType);
        return new EventsFilter(bundle);
    }

    public EventsFilterType getEventsFilterType() {
        return eventsFilterType;
    }

    public Bundle getFilterExtras() {
        return filterExtras;
    }
}