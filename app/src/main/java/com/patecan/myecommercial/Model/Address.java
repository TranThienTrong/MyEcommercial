package com.patecan.myecommercial.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    String id;
    String user_id;
    String fullName;
    String phoneNumber;
    String address;
    String note;
    String placeType;

    public Address(String id, String user_id, String fullName, String phoneNumber, String address, String note, String placeType) {
        this.id = id;
        this.user_id = user_id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.note = note;
        this.placeType = placeType;
    }

    public Address() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    protected Address(Parcel in) {

        this.id = in.readString();
        this.user_id = in.readString();
        this.fullName = in.readString();
        this.phoneNumber = in.readString();
        this.address = in.readString();
        this.note = in.readString();
        this.placeType = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.user_id);
        parcel.writeString(this.fullName);
        parcel.writeString(this.phoneNumber);
        parcel.writeString(this.address);
        parcel.writeString(this.note);
        parcel.writeString(this.placeType);
    }
}
