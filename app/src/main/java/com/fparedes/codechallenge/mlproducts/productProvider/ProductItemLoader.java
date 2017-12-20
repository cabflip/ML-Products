package com.fparedes.codechallenge.mlproducts.productProvider;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.fparedes.codechallenge.mlproducts.Product;

import java.util.List;

/**
 * Created by Facundo A. Paredes on 19/12/2017.
 */

public class ProductItemLoader extends AsyncTaskLoader<List<Product>> {

    private static final String TAG = ProductItemLoader.class.getName();
    private final String mQuery;

    public ProductItemLoader(Context context, String query){
        super(context);
        mQuery = query;
    }

    @Override
    public List<Product> loadInBackground() {
        try {
            return ProductProvider.buildProductList(mQuery);
        } catch (Exception e) {
            Log.e(TAG, "Failed to fetch products data", e);
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }
}
