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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Activities.ProductDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder> {

    private ArrayList<CartItem> cartItemList = new ArrayList<>();
    private Context context;
    private MyFirestore myFirestore;

    public CartItemAdapter() {
    }

    public CartItemAdapter(Context context, MyFirestore myFirestore, ArrayList<CartItem> productList) {
        this.context = context;
        this.cartItemList = productList;
        this.myFirestore = myFirestore;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);

        if (cartItem != null) {
            Glide.with(context).load(cartItem.getImage()).into(holder.imgProductImage);
            holder.tvProductTitle.setText(cartItem.getTitle());
            holder.tvProductPrice.setText(cartItem.getPrice());
            holder.tvProductQuantity.setText(String.valueOf(cartItem.getCart_quantity()));


            if (cartItem.getCart_quantity() == 0) {
                holder.imgBtnRemoveItem.setVisibility(View.GONE);
                holder.imgBtnAddItem.setVisibility(View.GONE);

                holder.tvProductQuantity.setText("OUT OF STOCK");
                holder.tvProductQuantity.setTextColor(ContextCompat.getColor(context, R.color.pink));
            } else {
                holder.imgBtnRemoveItem.setVisibility(View.VISIBLE);
                holder.imgBtnAddItem.setVisibility(View.VISIBLE);
                holder.tvProductQuantity.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgProductImage;
        TextView tvProductTitle;
        TextView tvProductPrice;
        TextView tvProductQuantity;
        ImageButton imgBtnDelete;
        ImageButton imgBtnRemoveItem;
        ImageButton imgBtnAddItem;
        CartItem cartItem;
        int itemQuantity;
        ArrayList<CartItem> cartItemArrayList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductImage = itemView.findViewById(R.id.item_cart_image);
            tvProductTitle = itemView.findViewById(R.id.tv_item_cart_title);
            tvProductPrice = itemView.findViewById(R.id.tv_item_cart_price);
            imgBtnDelete = itemView.findViewById(R.id.item_cart_imgBtn_delete);
            tvProductQuantity = itemView.findViewById(R.id.tv_item_cart_quantity);
            imgBtnRemoveItem = itemView.findViewById(R.id.imgBtn_item_cart_remove);
            imgBtnAddItem = itemView.findViewById(R.id.imgBtn_item_cart_add);

            imgBtnDelete.setOnClickListener(this);
            imgBtnRemoveItem.setOnClickListener(this);
            imgBtnAddItem.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_cart_imgBtn_delete:
                    deleteItem(cartItem);
                    break;
                case R.layout.item_product:
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product", cartItem);
                    context.startActivity(intent);
                    break;
                case R.id.imgBtn_item_cart_remove:
                    decreaseCartItemQuantity();
                    break;
                case R.id.imgBtn_item_cart_add:
                    increaseCartItemQuantity();
                    break;
            }
        }

        private void deleteItem(final CartItem cartItem) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            View confirmPopup = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
            alertDialogBuilder.setView(confirmPopup);

            Button btnConfirm = confirmPopup.findViewById(R.id.popup_delete_btnYes);
            Button btnCancel = confirmPopup.findViewById(R.id.popup_delete_btnCancel);

            // Popup the Delete Dialog
            final AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myFirestore.deleteCartItem(CartItemAdapter.this, cartItem.getId());
                    cartItemList.remove(cartItemList.get(cartItemList.indexOf(cartItem)));
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

        private void decreaseCartItemQuantity() {

            itemQuantity = cartItemList.get(getAdapterPosition()).getCart_quantity();

            HashMap<String, Object> itemCartHashMap = new HashMap<>();
            if (itemQuantity <= 1) {
                imgBtnRemoveItem.setEnabled(false);
            } else {
                itemQuantity--;
                tvProductQuantity.setText(String.valueOf(itemQuantity));
                itemCartHashMap.put("cart_quantity", itemQuantity);
                myFirestore.instantUpdateItemQuantity(cartItemList.get(getAdapterPosition()).getId(), itemCartHashMap);
            }
        }

        private void increaseCartItemQuantity() {

            itemQuantity = cartItemList.get(getAdapterPosition()).getCart_quantity();

            HashMap<String, Object> itemCartHashMap = new HashMap<>();
            if (itemQuantity >= cartItemList.get(getAdapterPosition()).getStock_quantity()) {
                imgBtnAddItem.setEnabled(false);
            } else {
                itemQuantity++;
                tvProductQuantity.setText(String.valueOf(itemQuantity));
                itemCartHashMap.put("cart_quantity", itemQuantity);
                myFirestore.instantUpdateItemQuantity(cartItemList.get(getAdapterPosition()).getId(), itemCartHashMap);
            }
        }

    }


}
