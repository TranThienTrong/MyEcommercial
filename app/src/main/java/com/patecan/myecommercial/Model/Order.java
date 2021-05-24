package com.patecan.myecommercial.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    String id;
    String user_id;
    CartItem item;
    Address address;
    String title;
    String image;
    String orderedDate;
    String subTotalAmount;
    String shippingCharge;
    String totalAmount;



    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public CartItem getItem() {
        return item;
    }

    public void setItem(CartItem item) {
        this.item = item;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(String subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Order(){}
    public Order(String id, String user_id, CartItem item, Address address, String title, String image, String subTotalAmount, String shippingCharge, String totalAmount, String orderedDate) {
        this.id = id;
        this.user_id = user_id;
        this.item = item;
        this.address = address;
        this.title = title;
        this.image = image;
        this.subTotalAmount = subTotalAmount;
        this.shippingCharge = shippingCharge;
        this.totalAmount = totalAmount;
        this.orderedDate = orderedDate;
    }

    public Order(Parcel in){
        this.id = in.readString();
        this.user_id = in.readString();
        //this.item = in.readArrayList();
        this.title = in.readString();
        this.image = in.readString();
        this.subTotalAmount = in.readString();
        this.shippingCharge = in.readString();
        this.totalAmount = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
