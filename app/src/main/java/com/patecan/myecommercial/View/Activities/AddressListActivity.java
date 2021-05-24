package com.patecan.myecommercial.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Address;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.Util.Contract;
import com.patecan.myecommercial.View.Adapter.AddressAdapter;

import java.util.ArrayList;

public class AddressListActivity extends BaseActivity {

    boolean fromCartListActivity;
    Button btnAddAddress;
    ArrayList<Address> addressesList;
    MyFirestore myFirestore;
    RecyclerView recyclerView;
    AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        fromCartListActivity = false;
        myFirestore = new MyFirestore(this);
        recyclerView = findViewById(R.id.recycler_view_address_list);


        btnAddAddress = findViewById(R.id.btn_add_address);
        getAddressList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_address_list);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAddAddress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(AddressListActivity.this, AddressSettingActivity.class));
                    }
                }
        );

        if (getIntent().hasExtra("from_cart_list")){
            fromCartListActivity = getIntent().getBooleanExtra("from_cart_list",false);
        }
    }


    public boolean getFromCartListActivity(){
        return fromCartListActivity;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAddressList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (addressAdapter!=null){
            addressAdapter.notifyDataSetChanged();
        }
    }

    public void getAddressListSuccess(ArrayList<Address> addressList) {
        this.addressesList = addressList;
        this.dismissProgressDialog();
    }

    public void getAddressList() {
        myFirestore.getAddressListFromCloud();
    }

    public void showAddressList(ArrayList<Address> addressesList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        addressAdapter = new AddressAdapter(this, myFirestore, addressesList);
        recyclerView.setAdapter(addressAdapter);
    }

}