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

package com.fredericletellier.foodinspector.data.source.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Search {

    @SerializedName("page_size")
    @Expose
    private String page_size;
    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<Product>();
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("skip")
    @Expose
    private Integer skip;

    /**
     * 
     * @return
     *     The page_size
     */
    public String getPage_size() {
        return page_size;
    }

    /**
     * 
     * @return
     *     The products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * 
     * @return
     *     The page
     */
    public String getPage() {
        return page;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @return
     *     The skip
     */
    public Integer getSkip() {
        return skip;
    }

}
