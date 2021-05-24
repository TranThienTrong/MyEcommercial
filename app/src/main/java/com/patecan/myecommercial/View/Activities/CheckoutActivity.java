package com.patecan.myecommercial.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Address;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.Model.Order;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Adapter.CheckoutProductAdapter;

import java.util.ArrayList;

public class CheckoutActivity extends BaseActivity implements View.OnClickListener {


    TextView tvFullName;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvNote;

    TextView tvSubTotal;
    TextView tvShippingCharge;
    TextView tvTotalAmount;

    Button btnPlaceOrder;
    int i=0;

    double subTotal;
    double shippingCharge;
    double totalAmount;
    ArrayList<CartItem> cartItemArrayList;
    Address address;
    Order myOrder;

    MyFirestore myFirestore;

    RecyclerView recyclerView;
    CheckoutProductAdapter checkoutProductAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView = findViewById(R.id.recycler_view_checkout_product_list);
        recyclerView.setNestedScrollingEnabled(false);

        tvFullName = findViewById(R.id.item_tv_checkout_fullName);
        tvAddress = findViewById(R.id.item_tv_checkout_address);
        tvPhone = findViewById(R.id.item_tv_checkout_phone);
        tvNote = findViewById(R.id.item_tv_checkout_note);

        btnPlaceOrder = findViewById(R.id.btn_place_order);


        tvSubTotal = findViewById(R.id.tv_checkout_subtotal);
        tvShippingCharge = findViewById(R.id.tv_checkout_shipping_charge);
        tvTotalAmount = findViewById(R.id.tv_checkout_total_amount);

        myFirestore = new MyFirestore(this);


        if (getIntent().hasExtra("address_to_checkout")) {
            address = getIntent().getExtras().getParcelable("address_to_checkout");
            tvFullName.setText(address.getFullName());
            tvAddress.setText(address.getAddress());
            tvPhone.setText(address.getPhoneNumber());
            tvNote.setText(address.getNote());

            myFirestore.checkAvaiableProduct(CheckoutActivity.this);
            btnPlaceOrder.setOnClickListener(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (checkoutProductAdapter != null) {
            checkoutProductAdapter.notifyDataSetChanged();
            //  myFirestore.checkAvaiableProduct(CheckoutActivity.this);
        }
    }

    public void checkProductAvailableSuccess(ArrayList<CartItem> cartItemArrayList) {

        this.cartItemArrayList = cartItemArrayList;
        this.subTotal = 0;
        this.shippingCharge = 10;
        for (CartItem item : cartItemArrayList) {
            Log.e("item", "" + item.getTitle());
            this.subTotal += Double.parseDouble(item.getPrice());
        }

        this.totalAmount = subTotal + shippingCharge;

        checkoutProductAdapter = new CheckoutProductAdapter(this, myFirestore, cartItemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(checkoutProductAdapter);

        tvSubTotal.setText(String.valueOf(subTotal));
        tvShippingCharge.setText(String.valueOf(shippingCharge));
        tvTotalAmount.setText(String.valueOf(totalAmount));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_place_order:
                placeOrder();
                break;
        }


    }

    private void placeOrder() {

        for (CartItem item : this.cartItemArrayList) {
            Order myOrder = new Order(
                    null,
                    User.getInstance().getId(),
                    item,
                    address,
                    cartItemArrayList.get(0).getTitle(),
                    cartItemArrayList.get(0).getImage(),
                    Double.toString(this.subTotal),
                    Double.toString(this.shippingCharge),
                    Double.toString(this.totalAmount),
                    null
            );
            Log.e("order", "placeOrder: ");
            myFirestore.uploadOrderToCloud(item,myOrder);
        }
    }

    public void uploadOrderToCloudSuccess(Order order) {

        Log.e("order", "uploadOrderToCloudSuccess: ");

        for (CartItem item : this.cartItemArrayList) {
            myFirestore.updateAllProductAfterPlaceOrder(CheckoutActivity.this, item);
        }

    }

    public void updateAllProductAfterPlaceOrderSuccess() {
        i++;

        if (i == this.cartItemArrayList.size()){
            Intent intent = new Intent(this, OrderSuccessSplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            return;
        }
      //  Log.e("order", "uploadAllProductAfterOrderToCloudSuccess: ");


    }
}