package com.fparedes.codechallenge.mlproducts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Facundo A. Paredes on 19/12/2017.
 */

public class QueryResponse {

    private String siteId;
    private String query;
    @SerializedName("results")
    private List<Product> productList;

    public String getSiteId() {
        return siteId;
    }

    public String getQuery() {
        return query;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
