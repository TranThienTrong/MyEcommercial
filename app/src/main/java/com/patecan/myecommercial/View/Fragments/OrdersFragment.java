package com.patecan.myecommercial.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.R;

import java.util.ArrayList;


public class OrdersFragment extends BaseFragment {


    TextView textView;
    MyFirestore myFirestore;
    ArrayList<Product> productList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_oders, container, false);
        textView = root.findViewById(R.id.text_notifications);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setText("123");
    }

}