package com.patecan.myecommercial.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.Util.Contract;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TextView tvRegister;
    TextView tvForgotPassword;
    EditText etEmailLogin;
    EditText etPasswordLogin;
    Button btnLogin;
    MyFirestore myFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvRegister = findViewById(R.id.tv_register);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        etEmailLogin = findViewById(R.id.et_email_login);
        etPasswordLogin = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        myFirestore = new MyFirestore(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
            case R.id.btn_login:
                loginUser();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean validateLogin() {
        if (TextUtils.isEmpty(etEmailLogin.getText().toString().trim())) {
            showErrorMessage("Empty Email", true);
            return false;
        } else if (TextUtils.isEmpty(etPasswordLogin.getText().toString().trim())) {
            showErrorMessage("Empty Password", true);
            return false;
        } else {
            return true;
        }
    }

    private void loginUser() {
        if (validateLogin()) {
            final String email = etEmailLogin.getText().toString();
            final String password = etPasswordLogin.getText().toString();
            myFirestore.userSignIn(email, password);
        }
    }

    void moveToDashboard() {
        new DashboardActivity().loginSuccess(User.getInstance());
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", User.getInstance());
        this.startActivity(intent);
        finish();
        dismissProgressDialog();
    }


    void signIn(String email, String password) {

    }
}