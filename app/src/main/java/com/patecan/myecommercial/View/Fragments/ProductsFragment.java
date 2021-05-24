package com.patecan.myecommercial.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Activities.AddProductActivity;
import com.patecan.myecommercial.View.Adapter.MyProductAdapter;

import java.util.ArrayList;


public class ProductsFragment extends BaseFragment {


    RecyclerView recyclerView;
    public MyProductAdapter myAdapter;
    ArrayList<Product> productList;
    TextView textView;
    MyFirestore myFirestore;


    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

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
        inflater.inflate(R.menu.product_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_product:
                startActivity(new Intent(getActivity(), AddProductActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view_fragment_products);
    }

    @Override
    public void onResume() {
        super.onResume();
        myFirestore.showProductList(ProductsFragment.this);
    }

    public void showProductList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myAdapter);
    }
}