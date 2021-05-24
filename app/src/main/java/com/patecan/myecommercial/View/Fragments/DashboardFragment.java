package com.patecan.myecommercial.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Activities.CartListActivity;
import com.patecan.myecommercial.View.Activities.SettingActivity;
import com.patecan.myecommercial.View.Adapter.MyDashboardAdapter;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {

    MyFirestore myFirestore;

    RecyclerView recyclerView;
    public MyDashboardAdapter myAdapter;
    ArrayList<Product> productList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        myFirestore = new MyFirestore(getActivity());
        productList = new ArrayList<>();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dashboard_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.action_cart:
                startActivity(new Intent(getActivity(), CartListActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_fragment_dashboard);
    }

    @Override
    public void onResume() {
        super.onResume();
        myFirestore.showProductList(DashboardFragment.this);
    }

    public void showProductList() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(myAdapter);
    }
}