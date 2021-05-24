package com.patecan.myecommercial.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Activities.ProductDetailActivity;

import java.util.ArrayList;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder>{

    private ArrayList<Product> productList = new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private MyFirestore myFirestore;

    public MyProductAdapter() {
    }

    public MyProductAdapter(Context context, Fragment fragment, MyFirestore myFirestore, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.fragment = fragment;
        this.myFirestore = myFirestore;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);

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
        ImageButton imgBtnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductImage = itemView.findViewById(R.id.item_image_product);
            tvProductTitle = itemView.findViewById(R.id.item_product_title);
            tvProductPrice = itemView.findViewById(R.id.item_product_price);
            imgBtnDelete = itemView.findViewById(R.id.item_imgBtn_delete_product);

            imgBtnDelete.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            Product product = productList.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.item_imgBtn_delete_product:
                    deleteProduct(product);
                    break;
                case R.layout.item_product:
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product", product);
                    context.startActivity(intent);
                    break;
            }
        }

        private void deleteProduct(final Product product) {
            AlertDialog.Builder aleartDialogBuilder = new AlertDialog.Builder(context);
            View confirmPopup = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
            aleartDialogBuilder.setView(confirmPopup);

            Button btnConfirm = confirmPopup.findViewById(R.id.popup_delete_btnYes);
            Button btnCancel = confirmPopup.findViewById(R.id.popup_delete_btnCancel);

            // Popup the Delete Dialog
            final AlertDialog dialog = aleartDialogBuilder.create();
            dialog.show();

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myFirestore.deleteProduct(MyProductAdapter.this, product.getId());

                    productList.remove(productList.get(productList.indexOf(product)));
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

    }
}
