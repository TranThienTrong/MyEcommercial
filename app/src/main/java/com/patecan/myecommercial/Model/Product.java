package com.patecan.myecommercial.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    String id;
    String user_id;
    String user_name;
    String image;
    String title;
    int stock_quantity;
    String price;
    String desc;

    public Product(){}
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Product(String id, String user_id, String user_name, String image, String title, int stock_quantity, String price, String desc) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.image = image;
        this.title = title;
        this.stock_quantity = stock_quantity;
        this.price = price;
        this.desc = desc;
    }

    public Product(Parcel in) {
        this.id = in.readString();
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.stock_quantity = in.readInt();
        this.price = in.readString();
        this.desc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.user_id);
        parcel.writeString(this.user_name);
        parcel.writeString(this.image);
        parcel.writeString(this.title);
        parcel.writeInt(this.stock_quantity);
        parcel.writeString(this.price);
        parcel.writeString(this.desc);
    }


}
