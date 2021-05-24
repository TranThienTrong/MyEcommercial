package com.patecan.myecommercial.View.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.Util.Contract;

import java.util.HashMap;

public class AddProductActivity extends BaseActivity implements View.OnClickListener {

    ImageButton imgBtnAddImageProduct;
    ImageView imgProduct;
    Button btnAddProduct;
    EditText etProductTitle;
    EditText etProductPrice;
    EditText etProductDesc;
    EditText etProductQuantity;

    MyFirestore myFirestore;
    Uri imageSelectedUri = null;
    String userProfileImageURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        myFirestore = new MyFirestore(AddProductActivity.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_add_product);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgBtnAddImageProduct = findViewById(R.id.imgBtn_add_image_product);
        imgProduct = findViewById(R.id.img_product_add_product);
        btnAddProduct = findViewById(R.id.btn_add_product);
        etProductTitle = findViewById(R.id.et_product_title_add_product);
        etProductPrice = findViewById(R.id.et_product_price_add_product);
        etProductDesc = findViewById(R.id.et_product_desc_add_product);
        etProductQuantity = findViewById(R.id.et_product_quantity_add_product);

        btnAddProduct.setOnClickListener(this);
        imgBtnAddImageProduct.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_add_image_product:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Contract.showGallery(AddProductActivity.this);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 69);
                }
                break;
            case R.id.btn_add_product:
                if (validateProductInfo()) {
                    String username = User.getInstance().getFirstName() + User.getInstance().getLastName();
                    Product product = new Product(
                            null,
                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            username, imageSelectedUri.toString(),
                            etProductTitle.getText().toString(),
                            Integer.parseInt(etProductQuantity.getText().toString()),
                            etProductPrice.getText().toString(),
                            etProductDesc.getText().toString());
                    myFirestore.addProductToCloud(product);
//                    if (userProfileImageURL==null){
//                        myFirestore.uploadImage(imageSelectedUri);
//                    }
                }
                break;
        }
    }


    void addProduct() {

        if (userProfileImageURL != null) {
            String username = User.getInstance().getFirstName() + User.getInstance().getLastName();
            Product product = new Product(
                    null,
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    username,
                    userProfileImageURL,
                    etProductTitle.getText().toString(),
                    Integer.parseInt(etProductQuantity.getText().toString()),
                    etProductPrice.getText().toString(),
                    etProductDesc.getText().toString());
            myFirestore.addProductToCloud(product);
        }
    }


    private boolean validateProductInfo() {
        if (TextUtils.isEmpty(etProductTitle.getText().toString().trim())) {
            showErrorMessage("Empty Title", true);
            return false;
        } else if (TextUtils.isEmpty(etProductPrice.getText().toString().trim())) {
            showErrorMessage("Empty Price", true);
            return false;
        } else if (TextUtils.isEmpty(etProductDesc.getText().toString().trim())) {
            showErrorMessage("Empty Desc", true);
            return false;
        } else if (TextUtils.isEmpty(etProductQuantity.getText().toString().trim())) {
            showErrorMessage("Empty Quantity", true);
            return false;
        } else if (TextUtils.isEmpty(imageSelectedUri.toString())) {
            showErrorMessage("Blank Image", true);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contract.GALLERY_RESULT_CODE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                imageSelectedUri = data.getData();
                imgProduct.setImageURI(imageSelectedUri);
                imgBtnAddImageProduct.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit_24));
            }
        } else {
            Log.e("avatar", "false");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 69) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean setImageURLFromStorage(String imgUrl) {
        Log.e("URL PRODUCT 3", "" + imgUrl);
        userProfileImageURL = imgUrl;
        return true;
    }
}