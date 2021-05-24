package com.patecan.myecommercial.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTvName;
    TextView mTvEmail;
    ImageView mImgAvatar;
    ImageButton mImgBtnEdit;
    Button btnLogout;
    Button btnAddressBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_setting);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mTvName = findViewById(R.id.tv_name_setting);
        mTvEmail = findViewById(R.id.tv_email_setting);
        mImgAvatar = findViewById(R.id.img_avatar_setting);
        mImgBtnEdit = findViewById(R.id.imgBtn_edit_setting);
        btnLogout = findViewById(R.id.btn_logout_setting);
        btnAddressBook = findViewById(R.id.btn_address_book_setting);

        Glide.with(this).load(User.getInstance().getImage()).into(mImgAvatar);
        mTvEmail.setText(User.getInstance().getEmail());
        mTvName.setText(User.getInstance().getFirstName() + " " + User.getInstance().getLastName());

        mImgBtnEdit.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnAddressBook.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout_setting:

                FirebaseAuth.getInstance().signOut();
                User.getInstance().clearInstance();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
                break;
            case R.id.imgBtn_edit_setting:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;
            case R.id.btn_address_book_setting:
                startActivity(new Intent(this, AddressListActivity.class));
                break;
        }
    }
}