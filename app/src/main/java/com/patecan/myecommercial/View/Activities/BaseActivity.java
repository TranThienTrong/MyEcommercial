package com.patecan.myecommercial.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.patecan.myecommercial.R;

public class BaseActivity extends AppCompatActivity {

    Dialog mProgressDialog;
    boolean doubleBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showErrorMessage(String message, Boolean errorMessage) {

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

        if (errorMessage) {
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.snackBarError));
        } else {
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.snackBarSuccess));
        }

        snackbar.show();
    }


    public void showProgressDialog(Context context, String text) {
        mProgressDialog = new Dialog(context);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        ((TextView) (mProgressDialog.findViewById(R.id.tv_progress))).setText(text);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }


    public void doubleBackToExit() {
        if (doubleBackPressed) {
            finishAffinity();
            System.exit(0);
            return;
        }
        this.doubleBackPressed = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        }, 2000);

    }
}