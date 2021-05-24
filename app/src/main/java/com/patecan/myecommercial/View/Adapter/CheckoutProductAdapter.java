package com.patecan.myecommercial.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;

import java.util.ArrayList;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.MyViewHolder> {

    private ArrayList<CartItem> productList = new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private MyFirestore myFirestore;

    public CheckoutProductAdapter() {
    }

    public CheckoutProductAdapter(Context context, MyFirestore myFirestore, ArrayList<CartItem> productList) {
        this.context = context;
        this.productList = productList;
        this.myFirestore = myFirestore;
    }


    @NonNull
    @Override
    public CheckoutProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout_product, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartItem product = productList.get(position);

        if (product != null) {
            Glide.with(context).load(product.getImage()).into(holder.imgProductImage);
            holder.tvProductTitle.setText(product.getTitle());
            holder.tvProductPrice.setText(product.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgProductImage;
        TextView tvProductTitle;
        TextView tvProductPrice;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImage = itemView.findViewById(R.id.item_checkout_image_product);
            tvProductTitle = itemView.findViewById(R.id.item_checkout_product_title);
            tvProductPrice = itemView.findViewById(R.id.item_checkout_product_price);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
