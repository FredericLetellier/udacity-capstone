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

public class ProductModel {

    @SerializedName("status_verbose")
    @Expose
    private String statusVerbose;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     *
     * @return
     *     The statusVerbose
     */
    public String getStatusVerbose() {
        return statusVerbose;
    }

    /**
     *
     * @param statusVerbose
     *     The status_verbose
     */
    public void setStatusVerbose(String statusVerbose) {
        this.statusVerbose = statusVerbose;
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
     * @param product
     *     The product
     */
    public void setProduct(Product product) {
        this.product = product;
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
     * @param status
     *     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

}
