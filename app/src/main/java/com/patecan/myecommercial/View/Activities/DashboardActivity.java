package com.patecan.myecommercial.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.Util.Contract;

public class DashboardActivity extends BaseActivity {

    User user = null;
    MyFirestore myFirestore;

    public void loginSuccess(User user) {
        this.user = user;
        Log.e("User Dashboard", this.user.toString());
        // this.user = getIntent().getExtras().getParcelable("user");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.auth_screens_background));

        myFirestore = new MyFirestore(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_products, R.id.navigation_orders, R.id.navigation_sold_product)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        User user1 = getIntent().getExtras().getParcelable("user");
 //       Log.e("User Intent", "" + user1.toString());
    }

    @Override
    public void onBackPressed() {
        doubleBackToExit();
    }
}