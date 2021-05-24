package com.patecan.myecommercial.View.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.SoldProduct;
import com.patecan.myecommercial.R;

import java.util.ArrayList;


public class SoldProductsFragment extends BaseFragment {


    MyFirestore myFirestore;

    public SoldProductsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myFirestore = new MyFirestore(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sold_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        myFirestore.getMySoldProduct(this);
    }

    public void getSoldProductSuccess(ArrayList<SoldProduct> soldProductArrayList){

        for (SoldProduct soldProduct:soldProductArrayList){
            Log.e("sold product", ""+soldProduct.getTitle());
        }

    }
}