package com.patecan.myecommercial.View.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.patecan.myecommercial.Util.Contract;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.R;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class UserProfileActivity extends BaseActivity implements View.OnClickListener {
    User user;
    MyFirestore myFirestore;
    Uri imageSelectedUri;
    String userProfileImageURL = null;

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPhone;
    Button btnSave;
    RadioButton radioBtnGenderMale;
    RadioButton radioBtnGenderFemale;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_user_profile);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        myFirestore = new MyFirestore(this);

        etFirstName = findViewById(R.id.et_first_name_user_profile);
        etLastName = findViewById(R.id.et_last_name_user_profile);
        etEmail = findViewById(R.id.et_email_user_profile);
        etPhone = findViewById(R.id.et_mobile_user_profile);
        btnSave = findViewById(R.id.btn_save_user_profile);
        imgAvatar = findViewById(R.id.img_avatar_user_profile);
        radioBtnGenderMale = findViewById(R.id.radioBtn_male);
        radioBtnGenderFemale = findViewById(R.id.radioBtn_female);


        //   user = getIntent().getExtras().getParcelable(Contract.USER_PARCELABLE);


        etFirstName.setText(User.getInstance().getFirstName());
        etLastName.setText(User.getInstance().getLastName());
        etEmail.setText(User.getInstance().getEmail());

        if (User.getInstance().getMobile() != null && User.getInstance().getGender() != null && User.getInstance().getImage() != null){
            etPhone.setText(User.getInstance().getMobile());

            if (User.getInstance().getGender().equals("male")) {
                radioBtnGenderMale.setChecked(true);
                radioBtnGenderFemale.setChecked(false);
            } else if (User.getInstance().getGender().equals("female")) {
                radioBtnGenderFemale.setChecked(true);
                radioBtnGenderMale.setChecked(false);
            }
        }

        if (User.getInstance().getImage() != null) {
            Log.e("img uri", "" + User.getInstance().getImage());
            Glide.with(this).load(Uri.parse(User.getInstance().getImage())).into(imgAvatar);
        }


        etFirstName.setEnabled(false);
        etLastName.setEnabled(false);
        etEmail.setEnabled(false);
        etPhone.setEnabled(true);

        imgAvatar.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar_user_profile:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Contract.showGallery(UserProfileActivity.this);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 69);
                }
                break;
            case R.id.btn_save_user_profile:
                if (validateUserProfile()) {
                    myFirestore.uploadImage(imageSelectedUri);
                    updateUser();
                }
                break;
        }
    }

    public boolean setImageURLFromStorage(String imgUrl) {
        Log.e("URL 2", "" + imgUrl);
        userProfileImageURL = imgUrl;
        User.getInstance().setImage(userProfileImageURL);
        return true;
    }

    void updateUser() {
        if (userProfileImageURL != null) {
            HashMap<String, Object> updateUser = new HashMap<>();
            Log.e("URL 3", "" + userProfileImageURL);
            updateUser.put("mobile", etPhone.getText().toString());
            updateUser.put("image", userProfileImageURL);
            if (radioBtnGenderMale.isChecked()) {
                updateUser.put("gender", "male");
            } else {
                updateUser.put("gender", "female");
            }
            updateUser.put(Contract.FIELD_COMPLETED, 1);
            myFirestore.updateUser(updateUser);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contract.GALLERY_RESULT_CODE && resultCode == RESULT_OK) {
            imageSelectedUri = data.getData();
            imgAvatar.setImageURI(imageSelectedUri);
        } else {
            Log.e("avatar", "false");
        }
    }


    private boolean validateUserProfile() {
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            showErrorMessage("Empty Phone Number", true);
            return false;
        } else if (radioBtnGenderMale.isChecked() == false && radioBtnGenderFemale.isChecked() == false) {
            showErrorMessage("Please Choose Your Gender", true);
            return false;

        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 69) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showErrorMessage("Granted", false);
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }


}