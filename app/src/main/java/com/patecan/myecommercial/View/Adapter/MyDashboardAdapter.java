package com.patecan.myecommercial.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Activities.ProductDetailActivity;

import java.util.ArrayList;

public class MyDashboardAdapter extends RecyclerView.Adapter<MyDashboardAdapter.MyViewHolder> {

    private ArrayList<Product> productList = new ArrayList<>();
    private Context context;

    public MyDashboardAdapter() {
    }

    public MyDashboardAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_product, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context).load(product.getImage()).into(holder.imgProductImage);
        holder.tvProductTitle.setText(product.getTitle());
        holder.tvProductPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgProductImage;
        TextView tvProductTitle;
        TextView tvProductPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImage = itemView.findViewById(R.id.item_image_dashboard_product);
            tvProductTitle = itemView.findViewById(R.id.item_product_dashboard_title);
            tvProductPrice = itemView.findViewById(R.id.item_product_dashboard_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product product = productList.get(getAdapterPosition());
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product", product);
                    context.startActivity(intent);
                }
            });
        }
        @Override
        public void onClick(View view) {
            Product product = productList.get(getAdapterPosition());
            switch (view.getId()) {
                case R.layout.item_dashboard_product:
                    break;
            }
        }

    }
}
