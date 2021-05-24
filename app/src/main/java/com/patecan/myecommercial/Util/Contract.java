package com.patecan.myecommercial.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatActivity;

public class Contract {

    public static final String SHOP_PREFERENCES = "MyShopPref";
    public static final String USERNAME_PREFERENCES = "logged_in_username";
    public static final String USER_PARCELABLE = "user_parcelable";
    public static final int GALLERY_RESULT_CODE = 10;
    public static final int REQUEST_ADDRESS_CODE = 123;

    // Collection Name
    public static final String USER = "users";
    public static final String PRODUCT = "products";
    public static final String CART_ITEM = "cart_item";

    // Document Field
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_MOBILE = "mobile";
    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_COMPLETED = "profileCompleted";

    public static final String USER_PROFILE_IMAGE = "user_profile_image";
    public static final String PRODUCT_IMAGE = "product_image";
    public static final String ADDRESS = "address";

    /* ------------------------- CART ----------------------------- */


    public static int CURRENT_CART_ITEM = 0;
    public static void showGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, GALLERY_RESULT_CODE);
    }

    public static String getFileExtension(Activity activity, Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.getContentResolver().getType(uri));
    }
}
