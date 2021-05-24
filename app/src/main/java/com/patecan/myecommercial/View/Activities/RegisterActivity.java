package com.patecan.myecommercial.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.R;

public class RegisterActivity extends BaseActivity {
    TextView tvLogin;
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    Button btnRegister;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    MyFirestore myFirestore;
    AuthResult mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvLogin = findViewById(R.id.tv_login);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();
        tvLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                validateRegisterDetail();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
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

    }


    private boolean validateRegisterDetail() {
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            showErrorMessage("Empty Email", true);
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            showErrorMessage("Empty Password", true);
            return false;
        } else {

            final String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUser = mAuth.getCurrentUser();

                                User user = new User(
                                        mUser.getUid(),
                                        etFirstName.getText().toString(),
                                        etLastName.getText().toString(),
                                        email,
                                        null,
                                        null,
                                        null,
                                        0
                                );


                                myFirestore.registerUser(user);

                            } else {
                                showErrorMessage("Create Fail", true);
                                Log.e("register", "Cannot create user");
                            }
                        }
                    });


            return true;
        }

    }
}