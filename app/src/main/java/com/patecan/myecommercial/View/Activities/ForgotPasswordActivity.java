package com.patecan.myecommercial.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.patecan.myecommercial.R;


public class ForgotPasswordActivity extends BaseActivity {
    Toolbar toolbar;
    EditText etEmailForgotPassword;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        toolbar = (Toolbar) findViewById(R.id.toolBar_forgot_password);
        setToolbar();
        etEmailForgotPassword = findViewById(R.id.et_email_forgot_password);
        btnSubmit = findViewById(R.id.btn_submit_fogot_password);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


    }

    void setToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    void resetPassword() {
        String email = etEmailForgotPassword.getText().toString().trim();
        if (email.isEmpty()) {
            showErrorMessage("Please input email", true);
        } else {
            showProgressDialog(this,"Submitting...");
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    dismissProgressDialog();
                    if (task.isSuccessful()) {
                        showErrorMessage("Email Sent", false);
                    } else {
                        showErrorMessage("Cannot Find Email", true);
                    }
                }

            });
        }
    }
}