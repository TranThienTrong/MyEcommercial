package com.patecan.myecommercial.View.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patecan.myecommercial.R;


public class BaseFragment extends Fragment {

    Dialog mProgressDialog;

    public void showProgressDialog(Context context, String text) {
        mProgressDialog = new Dialog(context);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        ((TextView) (mProgressDialog.findViewById(R.id.tv_progress))).setText(text);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }
    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }
}