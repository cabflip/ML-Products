package com.fparedes.codechallenge.mlproducts;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fparedes.codechallenge.mlproducts.adapter.ProductsAdapter;
import com.fparedes.codechallenge.mlproducts.productProvider.ProductItemLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Product>> {

    @BindView(R.id.query_et)
    EditText queryET;
    @BindView(R.id.query_btn)
    Button queryBtn;

    @BindView(R.id.products_list)
    RecyclerView productsRecyclerView;

    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.progress_indicator)
    ProgressBar loadingView;
    @BindView(R.id.results_label)
    TextView resultsLabel;

    ProductsAdapter productsAdapter;
    boolean loaderInitialized = false;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Products RecyclerView
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        productsRecyclerView.setLayoutManager(llManager);
        productsRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, llManager.getOrientation()));

        queryBtn.setOnClickListener(view -> searchProduct());
    }

    private void searchProduct() {
        // Hide keyboard
        queryET.clearFocus();
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null)
            in.hideSoftInputFromWindow(queryET.getWindowToken(), 0);

        // Save the query
        query = queryET.getText().toString();

        // Show loading view
        loadingView.setVisibility(View.VISIBLE);
        // Hide views
        resultsLabel.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        productsRecyclerView.setVisibility(View.GONE);
        // Init the loader
        if (loaderInitialized)
            getLoaderManager().restartLoader(0, null, this);
        else
            getLoaderManager().initLoader(0, null, this);

        loaderInitialized = true;
    }

    private void populateProducts(List<Product> products){
        if (products == null || products.isEmpty())
            return;

        productsAdapter = new ProductsAdapter(getApplicationContext(), products);
        productsRecyclerView.setAdapter(productsAdapter);
    }

    @Override
    public Loader<List<Product>> onCreateLoader(int i, Bundle bundle) {
        return new ProductItemLoader(getApplicationContext(), query);
    }

    @Override
    public void onLoadFinished(Loader<List<Product>> loader, List<Product> products) {
        // Hide loadingView
        loadingView.setVisibility(View.GONE);

        boolean hasProducts = products != null && !products.isEmpty();

        // Show or hide views regarding products query result
        emptyView.setVisibility(hasProducts ? View.GONE : View.VISIBLE);
        productsRecyclerView.setVisibility(hasProducts ? View.VISIBLE : View.GONE);
        resultsLabel.setVisibility(hasProducts ? View.VISIBLE : View.GONE);

        if (hasProducts) {
            resultsLabel.setText(String.format("%s%s", getString(R.string.results_field), query));
            // Populate list with products
            populateProducts(products);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Product>> loader) {

    }
}
