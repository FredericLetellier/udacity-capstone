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

package com.fredericletellier.foodinspector.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductBarcode {

    @SerializedName("status_verbose")
    @Expose
    private String status_verbose;
    @SerializedName("product")
    @Expose
    private Product product = new Product();
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * 
     * @return
     *     The status_verbose
     */
    public String getStatus_verbose() {
        return status_verbose;
    }

    /**
     *
     * @return
     *     The product
     */
    public Product getProduct() {
        return product;
    }

    /**
     *
     * @return
     *     The products
     */
    public Product getProducts() {
        return product;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

}
