package com.patecan.myecommercial.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    String productId;
    String productOwnerId;
    Product product;
    MyFirestore myFirestore;

    ImageView ivProductImage;
    TextView tvProductTitle;
    TextView tvProductPrice;
    TextView tvProductDesc;
    TextView tvProductQuantity;
    Button btnAddToCart;
    public ImageButton btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myFirestore = new MyFirestore(this);
        Toolbar toolbar = findViewById(R.id.toolBar_product_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.auth_screens_background));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ivProductImage = findViewById(R.id.img_product_detail_image);
        tvProductTitle = findViewById(R.id.et_product_detail_title);
        tvProductPrice = findViewById(R.id.et_product_detail_price);
        tvProductDesc = findViewById(R.id.et_product_detail_desc);
        tvProductQuantity = findViewById(R.id.et_product_detail_quantity);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnGoToCart = findViewById(R.id.btn_go_to_cart);

        btnGoToCart.setVisibility(View.GONE);
        btnGoToCart.setOnClickListener(this);

        if (getIntent().hasExtra("product")) {
            product = getIntent().getParcelableExtra("product");
            Log.e("product id", "" + product.getId());
            productId = product.getId();
            //showProductDetail(product);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myFirestore.getProductDetail(productId);
    }

    public void showProductDetail(Product product) {
        Glide.with(this).load(product.getImage()).into(ivProductImage);
        tvProductTitle.setText(product.getTitle());
        tvProductPrice.setText(product.getPrice());
        tvProductDesc.setText(product.getDesc());


        if (product.getStock_quantity() <= 0) {
            btnAddToCart.setVisibility(View.GONE);
            tvProductQuantity.setText("OUT OF STOCK");

        } else if (product.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            btnAddToCart.setVisibility(View.GONE);
        } else {
            tvProductQuantity.setText(Integer.toString(product.getStock_quantity()));
            btnAddToCart.setVisibility(View.VISIBLE);
            btnAddToCart.setOnClickListener(this);
        }


    }

    private void addToCart() {
        CartItem item = new CartItem(
                null,
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                product.getUser_id(),
                product.getId(),
                product.getImage(),
                product.getTitle(),
                product.getPrice(),
                product.getStock_quantity(),
                1
        );
        myFirestore.addCartItemToCloud(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_cart:
                addToCart();
                break;
            case R.id.btn_go_to_cart:
                startActivity(new Intent(this, CartListActivity.class));
                break;
        }

    }


    public void addToCartSuccess() {
        dismissProgressDialog();
    }
}
