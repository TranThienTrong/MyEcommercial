package com.patecan.myecommercial.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Address;
import com.patecan.myecommercial.R;

public class AddressSettingActivity extends BaseActivity implements View.OnClickListener {

    Button btnSaveAddress;
    EditText etFullName;
    EditText etPhoneNumber;
    EditText etAddress;
    EditText etNote;
    EditText etOtherPlace;

    RadioGroup radioGroupPlace;
    RadioButton rbHomePlace;
    RadioButton rbCompanyPlace;
    RadioButton rbOtherPlace;

    MyFirestore myFirestore;

    Address address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_setting);

        etFullName = findViewById(R.id.et_address_setting_fullName);
        etPhoneNumber = findViewById(R.id.et_address_setting_phoneNumber);
        etAddress = findViewById(R.id.et_address_setting_address);
        etNote = findViewById(R.id.et_address_setting_note);
        etOtherPlace = findViewById(R.id.et_address_setting_other_place);

        radioGroupPlace = findViewById(R.id.radioGroup_address_setting_place);
        rbHomePlace = findViewById(R.id.radioBtn_address_setting_home);
        rbCompanyPlace = findViewById(R.id.radioBtn_address_setting_company);
        rbOtherPlace = findViewById(R.id.radioBtn_address_setting_other);

        btnSaveAddress = findViewById(R.id.btn_address_setting_save_address);
        btnSaveAddress.setOnClickListener(this);

        radioGroupPlace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
                if (radioButtonId == R.id.radioBtn_address_setting_other) {
                    etOtherPlace.setVisibility(View.VISIBLE);
                } else {
                    etOtherPlace.setVisibility(View.GONE);
                }
            }
        });

        if (rbOtherPlace.isChecked()) {
            etOtherPlace.setVisibility(View.VISIBLE);
        }
        if (rbCompanyPlace.isChecked() || rbHomePlace.isChecked()) {
            etOtherPlace.setVisibility(View.GONE);
        } else {
            etOtherPlace.setVisibility(View.GONE);
        }


        myFirestore = new MyFirestore(this);


        if (getIntent().hasExtra("address_from_list")) {
            address = getIntent().getExtras().getParcelable("address_from_list");

            etFullName.setText(address.getFullName());
            etPhoneNumber.setText(address.getPhoneNumber());
            etAddress.setText(address.getAddress());
            etNote.setText(address.getNote());


            if (address.getPlaceType().equals("home")) {
                rbHomePlace.setChecked(true);
            } else if (address.getPlaceType().equals("company")) {
                rbCompanyPlace.setChecked(true);
            } else {
                rbOtherPlace.setChecked(true);
                etOtherPlace.setText(address.getPlaceType());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_address_setting_save_address:
                if (address == null) {
                    saveAddress();
                } else {
                    updateAddress();
                }

                break;
        }
    }

    private void updateAddress() {
        String id = address.getId();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String fullName = etFullName.getText().toString();
        String userAddress = etAddress.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String note = etNote.getText().toString();
        String placeType;

        if (rbHomePlace.isChecked()) {
            placeType = "place";
        } else if (rbCompanyPlace.isChecked()) {
            placeType = "company";
        } else {
            placeType = etOtherPlace.getText().toString();
        }

        Address myAddress = new Address(
                id,
                address.getUser_id(),
                fullName,
                phoneNumber,
                userAddress,
                note,
                placeType);

        myFirestore.updateAddressToCloud(myAddress);
    }

    private void saveAddress() {
        String id = null;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String fullName = etFullName.getText().toString();
        String address = etAddress.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String note = etNote.getText().toString();
        String placeType;

        if (rbHomePlace.isChecked()) {
            placeType = "place";
        } else if (rbCompanyPlace.isChecked()) {
            placeType = "company";
        } else {
            placeType = etOtherPlace.getText().toString();
        }

        Address myAddress = new Address(
                id,
                userId,
                fullName,
                phoneNumber,
                address,
                note,
                placeType);

        myFirestore.uploadAddressToCloud(myAddress);

    }

    public void uploadedSuccess() {
        dismissProgressDialog();
        finish();
    }

    public void updatedSuccess() {
        onBackPressed();
    }
}