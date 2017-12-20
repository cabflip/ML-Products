package com.fparedes.codechallenge.mlproducts.productProvider;

import android.util.Log;

import com.fparedes.codechallenge.mlproducts.Product;
import com.fparedes.codechallenge.mlproducts.QueryResponse;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Facundo A. Paredes on 19/12/2017.
 */

public class ProductProvider {

    private static final String TAG = ProductProvider.class.getName();
    private static final String SERVICE_URL = "https://api.mercadolibre.com/sites/MLA/search?q=";

    private static QueryResponse queryResponse;
    private static List<Product> productList;

    static List<Product> buildProductList(String query) {
        String contactsJSonString = new ProductProvider().parseQuery(query);
        if (contactsJSonString == null) {
            Log.d(TAG, "Failed to parse the json.");
            return null;
        }

        Gson gson = new Gson();

        queryResponse = gson.fromJson(contactsJSonString, QueryResponse.class);

        // Order products by price
        productList = queryResponse.getProductList();
        Collections.sort(productList, Comparator.comparingDouble(Product::getPrice));

        return productList;
    }

    private String parseQuery(String query){
        try {
            URL url = new URL(SERVICE_URL + query);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            Log.d(TAG, "Query failed.", e);
            return null;
        }
    }
}
