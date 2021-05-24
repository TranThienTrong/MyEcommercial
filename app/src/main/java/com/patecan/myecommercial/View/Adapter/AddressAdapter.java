package com.patecan.myecommercial.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patecan.myecommercial.Database.MyFirestore;
import com.patecan.myecommercial.Model.Address;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.R;
import com.patecan.myecommercial.View.Activities.AddressListActivity;
import com.patecan.myecommercial.View.Activities.AddressSettingActivity;
import com.patecan.myecommercial.View.Activities.CheckoutActivity;
import com.patecan.myecommercial.View.Activities.SettingActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private ArrayList<Address> addressList = new ArrayList<>();
    private Context context;
    private MyFirestore myFirestore;

    public AddressAdapter() {
    }

    public AddressAdapter(Context context, MyFirestore myFirestore, ArrayList<Address> addressList) {
        this.context = context;
        this.myFirestore = myFirestore;
        this.addressList = addressList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Address address = this.addressList.get(position);
        if (address != null) {
            holder.tvFullName.setText(address.getFullName());
            holder.tvAddress.setText(address.getAddress());
            holder.tvPhone.setText(address.getPhoneNumber());
            holder.tvPlaceType.setText(address.getPlaceType());
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvFullName;
        TextView tvAddress;
        TextView tvPhone;
        TextView tvPlaceType;
        ImageButton imgBtnEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.item_tv_address_fullName);
            tvAddress = itemView.findViewById(R.id.item_tv_address_address);
            tvPhone = itemView.findViewById(R.id.item_tv_address_phone);
            tvPlaceType = itemView.findViewById(R.id.item_tv_address_placeType);

            imgBtnEdit = itemView.findViewById(R.id.item_imgBtn_edit_address);
            imgBtnEdit.setOnClickListener(this);

            if (((AddressListActivity) context).getFromCartListActivity()) {
                itemView.setClickable(true);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent((AddressListActivity) context, CheckoutActivity.class);
                        intent.putExtra("address_to_checkout", addressList.get(getAdapterPosition()));
                        ((AddressListActivity) context).startActivity(intent);
                    }
                });
            } else {
                itemView.setClickable(false);
            }

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_imgBtn_edit_address:
                    Intent intent = new Intent(context, AddressSettingActivity.class);
                    intent.putExtra("address_from_list", addressList.get(getAdapterPosition()));
                    context.startActivity(intent);
                    AddressAdapter.this.notifyDataSetChanged();
                    break;
            }

        }
    }
}
