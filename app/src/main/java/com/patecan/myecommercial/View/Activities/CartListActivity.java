package com.patecan.myecommercial.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Adapter.CartItemAdapter;
import com.patecan.myecommercial.View.Adapter.MyProductAdapter;

import java.util.ArrayList;

public class CartListActivity extends BaseActivity {

    MyFirestore myFirestore;

    RecyclerView recyclerView;
    public CartItemAdapter myCartItemAdapter;

    ArrayList<CartItem> cartItemList;
    ArrayList<Product> productList;


    TextView tvSubTotal;
    TextView tvShippingCharge;
    TextView tvTotalAmount;
    Button btnGoToCheckOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_cart_list);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view_cart_list);
        myFirestore = new MyFirestore(this);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        tvSubTotal = findViewById(R.id.tv_cart_list_info_subtotal);
        tvShippingCharge = findViewById(R.id.tv_cart_list_info_shipping_charge);
        tvTotalAmount = findViewById(R.id.tv_cart_list_info_total_amount);
        btnGoToCheckOut = findViewById(R.id.btn_go_to_checkout);

        btnGoToCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartListActivity.this,AddressListActivity.class);
                intent.putExtra("from_cart_list", true);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductList();
    }


    public void showCartItemList(ArrayList<CartItem> cartItemList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myCartItemAdapter);

        double subTotal = 0.0;
        double shippingCharge = 10.0;


        for (CartItem cartItem : cartItemList) {
            int quantity = cartItem.getCart_quantity();

            if (quantity > 0) {
                double price = Double.parseDouble(cartItem.getPrice());
                subTotal += (price * quantity);
            }

        }

        tvSubTotal.setText(Double.toString(subTotal));
        tvShippingCharge.setText(Double.toString(shippingCharge));
        tvTotalAmount.setText(Double.toString(subTotal + shippingCharge));
    }


    public void getProductDetail(ArrayList<Product> productList) {
        this.productList = productList;
        myFirestore.getShowCartListItems();
    }

    public void getItemInCartList(ArrayList<CartItem> cartItemList) {

        for (Product product : this.productList) {
            for (CartItem cartItem : cartItemList) {
                if (product.getId().equals(cartItem.getProduct_id())) {
                   // cartItem.setStock_quantity(product.getStock_quantity());

                    if (product.getStock_quantity() == 0) {
                        cartItem.setCart_quantity(0);
                    }
                }
            }
        }

        this.cartItemList = cartItemList;

        this.myCartItemAdapter = new CartItemAdapter(this, myFirestore, cartItemList);
        myCartItemAdapter.notifyDataSetChanged();
        showCartItemList(cartItemList);
    }


    public void getProductList() {
        myFirestore.getAllProduct();
    }







    public void removedItemInCartSuccess(){
        onResume();
        this.dismissProgressDialog();
    }

    public void updateItemInCartSuccess(){
        onResume();
        this.dismissProgressDialog();
    }
}