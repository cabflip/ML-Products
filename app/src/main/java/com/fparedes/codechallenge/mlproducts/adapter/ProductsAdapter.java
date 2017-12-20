package com.fparedes.codechallenge.mlproducts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fparedes.codechallenge.mlproducts.Product;
import com.fparedes.codechallenge.mlproducts.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Facundo A. Paredes on 19/12/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int HEADER_COUNT = 1;
    private static final int MAX_ITEM_COUNT = 5;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<Product> mProductList;

    public ProductsAdapter(Context context, List<Product> productList){
        this.mContext = context;
        this.mProductList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_header_name_title)
        TextView nameTitle;
        @BindView(R.id.product_header_price_title)
        TextView priceTitle;

        HeaderViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_item_background)
        LinearLayout itemBackground;
        @BindView(R.id.product_item_name)
        TextView itemProductName;
        @BindView(R.id.product_item_price)
        TextView itemProductPrice;

        ProductViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.product_simple_list_header, parent, false);
                return new HeaderViewHolder(headerView);

            case TYPE_ITEM:
                View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_simple_list_item, parent, false);
                return new ProductViewHolder(itemView);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // It's a header
        if (position == 0) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.nameTitle.setText(mContext.getString(R.string.product_name_title));
            headerHolder.priceTitle.setText(mContext.getString(R.string.product_price_title));
            return;
        }

        ProductViewHolder productViewHolder = (ProductViewHolder) holder;

        // It's an item
        setBackgroundColor(productViewHolder, position);
        Product product = mProductList.get(position);
        productViewHolder.itemProductName.setText(product.getTitle());
        productViewHolder.itemProductPrice.setText(String.format("$%s", product.getPrice()));
    }

    @Override
    public int getItemCount() {
        // Returns only the first 5 + Headers
        return MAX_ITEM_COUNT + HEADER_COUNT;
    }

    private void setBackgroundColor(ProductViewHolder holder, int position){

        if (position == 1){
            // First row
            holder.itemBackground.setBackgroundColor(mContext.getColor(R.color.green));
            return;
        }

        if (position == (getItemCount() - 1) && mProductList.size() > 2){
            // Last row
            holder.itemBackground.setBackgroundColor(mContext.getColor(R.color.red));
            return;
        }

        // Return if there's only two items or less
        if (mProductList.size() <= 2)
            return;

        switch (position % 2){
            // Even row
            case 0:
                holder.itemBackground.setBackgroundColor(mContext.getColor(R.color.gray_even));
                break;
            // Odd row
            case 1:
                holder.itemBackground.setBackgroundColor(mContext.getColor(R.color.gray_odd));
                break;
        }
    }
}
