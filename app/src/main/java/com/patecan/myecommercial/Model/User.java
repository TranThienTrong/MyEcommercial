package com.patecan.myecommercial.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public static User instance = new User();

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User() {
    }


    public static User getInstance() {
        return instance;
    }

    public void clearInstance() {
        this.id = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.image=null;
        this.mobile=null;
        this.gender=null;
        this.profileCompleted=0;;
    }

    String id;
    String firstName;
    String lastName;
    String email;
    String image;
    String mobile;
    String gender;
    int profileCompleted;

    public User(String id, String firstName, String lastName, String email, String image, String mobile, String gender, int profileCompleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.image = image;
        this.mobile = mobile;
        this.gender = gender;
        this.profileCompleted = profileCompleted;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(int profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.image = in.readString();
        this.mobile = in.readString();
        this.gender = in.readString();
        this.profileCompleted = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeString(this.email);
        parcel.writeString(this.image);
        parcel.writeString(this.mobile);
        parcel.writeString(this.gender);
        parcel.writeInt(this.profileCompleted);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", profileCompleted=" + profileCompleted +
                '}';
    }
}
