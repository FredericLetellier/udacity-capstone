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

package com.fredericletellier.foodinspector.data.source.remote.API;

import android.content.res.Resources;

import com.fredericletellier.foodinspector.R;

import java.util.Locale;

public class EndpointBaseUrl {

    public static final String URL_BASE = "http://";
    public static final String URL_BARCODE = ".openfoodfacts.org/api/v0/product";
    public static final String URL_SEARCH = ".openfoodfacts.org/cgi";

    private static String language_code;
    private static String country_code;
    private static String middle_url;

    public EndpointBaseUrl() {
        //Only supported languages via strings.xml
        language_code = Resources.getSystem().getString(R.string.language_code);
        //All countries available
        country_code = Locale.getDefault().getCountry().toLowerCase();

        if (language_code.equals(country_code)){
            middle_url = language_code;
        }else{
            middle_url = country_code + "-" + language_code;
        }
    }

    public static String getEndpointBaseUrlBarcode() {
        return URL_BASE + middle_url + URL_BARCODE;
    }

    public static String getEndpointBaseUrlSearch() {
        return URL_BASE + middle_url + URL_SEARCH;
    }
}
