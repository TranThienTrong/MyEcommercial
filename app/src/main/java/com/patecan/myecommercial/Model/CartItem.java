package com.patecan.myecommercial.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    String id;
    String user_id;
    String owner_id;

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    String product_id;
    String image;
    String title;
    String price;
    int stock_quantity;
    int cart_quantity;

    public CartItem() {

    }

    public CartItem(String id, String user_id, String owner_id, String product_id, String image, String title, String price, int stock_quantity, int cart_quantity) {
        this.id = id;
        this.user_id = user_id;
        this.owner_id = owner_id;
        this.product_id = product_id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.cart_quantity = cart_quantity;
    }

    public CartItem(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        product_id = in.readString();
        image = in.readString();
        title = in.readString();
        price = in.readString();
        stock_quantity = in.readInt();
        cart_quantity = in.readInt();
    }

    public CartItem(String user_id, String product_id, String image, String title, String price, int stock_quantity, int cart_quantity) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.cart_quantity = cart_quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.user_id);
        parcel.writeString(this.product_id);
        parcel.writeString(this.image);
        parcel.writeString(this.title);
        parcel.writeString(this.price);
        parcel.writeInt(this.stock_quantity);
        parcel.writeInt(this.cart_quantity);
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public int getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(int cart_quantity) {
        this.cart_quantity = cart_quantity;
    }
}
