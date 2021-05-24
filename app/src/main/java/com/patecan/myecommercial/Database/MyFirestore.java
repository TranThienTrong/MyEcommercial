package com.patecan.myecommercial.Database;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Any;
import com.patecan.myecommercial.Model.Address;
import com.patecan.myecommercial.Model.CartItem;
import com.patecan.myecommercial.Model.Order;
import com.patecan.myecommercial.Model.Product;
import com.patecan.myecommercial.Model.SoldProduct;
import com.patecan.myecommercial.View.Activities.AddProductActivity;
import com.patecan.myecommercial.View.Activities.AddressListActivity;
import com.patecan.myecommercial.View.Activities.AddressSettingActivity;
import com.patecan.myecommercial.View.Activities.BaseActivity;
import com.patecan.myecommercial.View.Activities.CartListActivity;
import com.patecan.myecommercial.View.Activities.CheckoutActivity;
import com.patecan.myecommercial.View.Activities.DashboardActivity;
import com.patecan.myecommercial.Model.User;
import com.patecan.myecommercial.Util.Contract;
import com.patecan.myecommercial.View.Activities.LoginActivity;
import com.patecan.myecommercial.View.Activities.ProductDetailActivity;
import com.patecan.myecommercial.View.Activities.UserProfileActivity;
import com.patecan.myecommercial.View.Adapter.CartItemAdapter;
import com.patecan.myecommercial.View.Fragments.DashboardFragment;
import com.patecan.myecommercial.View.Fragments.ProductsFragment;
import com.patecan.myecommercial.View.Adapter.MyDashboardAdapter;
import com.patecan.myecommercial.View.Adapter.MyProductAdapter;
import com.patecan.myecommercial.View.Fragments.SoldProductsFragment;

import java.util.ArrayList;
import java.util.HashMap;


public class MyFirestore extends BaseActivity {

    FirebaseFirestore db;
    Context context;
    FirebaseUser currentUser;
    User myUser;

    public MyFirestore(Context context) {
        this.context = context;
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.db = FirebaseFirestore.getInstance();
  
    }

    public MyFirestore() {
    }


    /**
     * ***************************************** @User  ******************************************
     */
    public void registerUser(User user) {
        showProgressDialog(context, "Adding to Database");

        db.collection(Contract.USER)
                .document(user.getId())
                .set(user, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("Database", "Add Successful");
                        ((AppCompatActivity) context).finish();
                        dismissProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.e("Database", "Add Fail");
                        dismissProgressDialog();
                    }
                });
    }


    public void setUser(User user) {
        Log.e("user", "" + user.toString());
        User.getInstance().setId(user.getId());
        User.getInstance().setFirstName(user.getFirstName());
        User.getInstance().setLastName(user.getLastName());
        User.getInstance().setEmail(user.getEmail());
        User.getInstance().setMobile(user.getMobile());
        User.getInstance().setImage(user.getImage());
        User.getInstance().setGender(user.getGender());
    }


    public void userSignIn(String email, String password) {
        ((LoginActivity) context).showProgressDialog(context, "Logging in...");
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            getUserFromCloud(userId);
                        } else {
                            ((LoginActivity) context).showErrorMessage("Wrong email/password", true);
                        }
                    }
                });
    }


    public void getUserFromCloud(String id) {
        FirebaseFirestore.getInstance().collection(Contract.USER)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        setUser(document.toObject(User.class));
                        moveToDashboard();
                    }
                });
    }


    public void signInSuccess() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userId != null) {
            db.collection(Contract.USER)
                    .document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            setUser(document.toObject(User.class));
                            moveToDashboard();
                        }
                    });
        } else {
            Log.e("firestore", "Cannot Find ID ");
        }


    }

    void moveToDashboard() {
        new DashboardActivity().loginSuccess(User.getInstance());
        Intent intent = new Intent(context, DashboardActivity.class);
        intent.putExtra("user", User.getInstance());
        context.startActivity(intent);
        ((LoginActivity) context).dismissProgressDialog();
    }

    public void updateUser(HashMap<String, Object> newUserData) {
        db.collection(Contract.USER)
                .document(User.getInstance().getId())
                .update(newUserData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("update", "Successful");
                        context.startActivity(new Intent(context, DashboardActivity.class));
                        ((Activity) context).finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("update", "Fail");
                    }
                });
    }


    public void uploadImage(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if (context instanceof UserProfileActivity) {
            // References to user_profile_image+time.jpg
            final StorageReference imagesRef = storageRef.child(
                    Contract.USER_PROFILE_IMAGE + System.currentTimeMillis() + "." + Contract.getFileExtension((Activity) context, imageUri));

            imagesRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.e("URL 1", "" + uri.toString());
                                    ((UserProfileActivity) context).setImageURLFromStorage(uri.toString());
                                }
                            });
                        }
                    });
        } else if (context instanceof AddProductActivity) {
            final StorageReference imagesRef = storageRef.child(
                    Contract.PRODUCT_IMAGE + "_" + System.currentTimeMillis() + "." + Contract.getFileExtension((Activity) context, imageUri));

            imagesRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.e("URL PRODUCT 1", "" + uri.toString());
                                    ((AddProductActivity) context).setImageURLFromStorage(uri.toString());
                                }
                            });
                        }
                    });
        }
    }

    /**
     * ***************************************** @Product  ******************************************
     */

    public void uploadProductToCloud(Product product) {

        db.collection(Contract.PRODUCT)
                .document()
                .set(product, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((AddProductActivity) context).finish();
                        dismissProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Database Product", "Add Fail");
                        dismissProgressDialog();
                    }
                });
    }


    public void addProductToCloud(final Product product) {
        showProgressDialog(context, "Adding...");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        final StorageReference imagesRef = storageRef.child(
                Contract.PRODUCT_IMAGE + "_" + System.currentTimeMillis() + "." + Contract.getFileExtension((Activity) context, Uri.parse(product.getImage())));

        imagesRef.putFile(Uri.parse(product.getImage()))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("URL PRODUCT 1", "" + uri.toString());
                                product.setImage(uri.toString());
                                uploadProductToCloud(product);
                            }
                        });
                    }
                });

    }


    public ArrayList<Product> getProductList(final Fragment fragment) {

        showProgressDialog(fragment.getActivity(), "Getting Products...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        final ArrayList<Product> productList = new ArrayList<>();

        if (fragment instanceof ProductsFragment) {
            database.collection("products")
                    .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            Log.e("Product List", "" + queryDocumentSnapshots.getDocuments().toString());
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Product currProduct = document.toObject(Product.class);
                                currProduct.setId(document.getId());
                                productList.add(currProduct);
                            }
                            dismissProgressDialog();
                        }
                    });
        } else if (fragment instanceof DashboardFragment) {
            database.collection("products")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Product currProduct = document.toObject(Product.class);
                                Log.e("product", "" + currProduct.getTitle());
                            }
                            dismissProgressDialog();
                        }
                    });
        }


        return productList;
    }


    public void updateProductId(String productId) {

        HashMap<String, Object> itemCartHashMap = new HashMap<>();
        itemCartHashMap.put("id", productId);
        db.collection("products")
                .document(productId)
                .update(itemCartHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });
    }

    public void showProductList(final Fragment fragment) {
        showProgressDialog(fragment.getActivity(), "Getting Product...");
        final ArrayList<Product> productList = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        if (fragment instanceof ProductsFragment) {

            database.collection("products")
                    .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            Log.e("Product List", "" + queryDocumentSnapshots.getDocuments().toString());
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Product currProduct = document.toObject(Product.class);
                                currProduct.setId(document.getId());
                                updateProductId(currProduct.getId());
                                productList.add(currProduct);
                            }
                            dismissProgressDialog();

                            showProgressDialog(fragment.getActivity(), "Showing Product...");
                            Log.e("Show Product", "showProductList: ");
                            ((ProductsFragment) fragment).myAdapter = new MyProductAdapter(fragment.getActivity(), fragment, MyFirestore.this, productList);
                            ((ProductsFragment) fragment).myAdapter.notifyDataSetChanged();
                            ((ProductsFragment) fragment).showProductList();
                            dismissProgressDialog();
                        }
                    });
        }

        if (fragment instanceof DashboardFragment) {

            database.collection("products")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Product currProduct = document.toObject(Product.class);
                                currProduct.setId(document.getId());
                                updateProductId(currProduct.getId());
                                productList.add(currProduct);
                                Log.e("product", "" + currProduct.getTitle());
                            }
                            dismissProgressDialog();

                            showProgressDialog(fragment.getActivity(), "Showing Product...");
                            Log.e("Show Product", "showProductList: ");
                            ((DashboardFragment) fragment).myAdapter = new MyDashboardAdapter(fragment.getActivity(), productList);
                            ((DashboardFragment) fragment).myAdapter.notifyDataSetChanged();
                            ((DashboardFragment) fragment).showProductList();
                            dismissProgressDialog();


                        }
                    });
        }

    }


    public void getProductDetail(String id) {
        showProgressDialog(context, "Getting Product...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("products")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        ((ProductDetailActivity) context).showProductDetail(documentSnapshot.toObject(Product.class));
                        dismissProgressDialog();
                    }
                });


    }

    public void deleteProduct(final RecyclerView.Adapter adapter, String id) {
        showProgressDialog(context, "Deleting...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        if (adapter instanceof MyProductAdapter) {
            database.collection(Contract.PRODUCT).document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            adapter.notifyDataSetChanged();
                            dismissProgressDialog();
                        }
                    });
        } else if (adapter instanceof CartItemAdapter) {

        }

    }

    /**
     * ***************************************** @Cart ******************************************
     */

    public void addCartItemToCloud(CartItem item) {
        ((ProductDetailActivity) context).showProgressDialog(context, "Adding to Cart...");
        db.collection(Contract.CART_ITEM)
                .document()
                .set(item, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Contract.CURRENT_CART_ITEM++;
                        ((ProductDetailActivity) context).btnGoToCart.setVisibility(View.VISIBLE);
                        ((ProductDetailActivity) context).dismissProgressDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    public void checkIfItemAvaiable(Product product) {

        db.collection("products")
                .document(product.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.toObject(Product.class).getStock_quantity() > 0) {

                        }
                        ;

                    }
                });

    }


    public void checkAvaiableProduct(final CheckoutActivity checkoutActivity) {
        final ArrayList<Product> productArrayList = new ArrayList<>();
        final ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
        db.collection("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Product product = document.toObject(Product.class);
                            productArrayList.add(product);
                        }

                        db.collection("cart_item")
                                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                            CartItem cartItem = document.toObject(CartItem.class);
                                            cartItemArrayList.add(cartItem);
                                        }

                                        ArrayList<CartItem> afterCheck = checkCartItemStock(productArrayList, cartItemArrayList);

                                        for (CartItem item : afterCheck) {
                                            Log.e("item 1", "" + item.getTitle());
                                        }

                                        checkoutActivity.checkProductAvailableSuccess(afterCheck);


                                    }
                                });
                    }
                });
    }


    ArrayList<CartItem> checkCartItemStock(ArrayList<Product> productArrayList, ArrayList<CartItem> cartItemArrayList) {

        ArrayList<CartItem> afterCheck = new ArrayList<>();
        for (Product product : productArrayList) {
            for (CartItem item : cartItemArrayList) {
                if (item.getProduct_id().equals(product.getId())) {
                    if (item.getCart_quantity() <= product.getStock_quantity()) {
                        afterCheck.add(item);
                    }
                }
            }
        }
        return afterCheck;
    }

    public void getShowCartListItems() {
        final ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
        db.collection(Contract.CART_ITEM)
                .whereEqualTo("user_id", User.getInstance().getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            final CartItem cartItem = document.toObject(CartItem.class);
                            cartItem.setId(document.getId());
                            cartItemArrayList.add(cartItem);
                            updateCartItemId(document.getId());

                        }

                        ((CartListActivity) context).getItemInCartList(cartItemArrayList);
                    }
                });
    }


    public void getProductQuantity(String id) {
        db.collection(Contract.CART_ITEM)
                .whereEqualTo("products", id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            setProductQuantity(document.toObject(CartItem.class).getStock_quantity());
                        }
                    }
                });
    }

    public void getAllProduct() {
        ((CartListActivity) context).showProgressDialog(context, "Waiting...");
        final ArrayList<Product> cartItemArrayList = new ArrayList<>();
        db.collection("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Product cartItem = document.toObject(Product.class);
                            cartItem.setId(document.getId());
                            cartItemArrayList.add(cartItem);
                        }
                        ((CartListActivity) context).getProductDetail(cartItemArrayList);
                        ((CartListActivity) context).dismissProgressDialog();
                    }
                });
    }

    int setProductQuantity(int quantity) {
        return quantity;
    }


    public void deleteCartItem(final RecyclerView.Adapter adapter, String id) {
        ((CartListActivity) context).showProgressDialog(context, "Deleting...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection(Contract.CART_ITEM)
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        adapter.notifyDataSetChanged();
                        ((CartListActivity) context).dismissProgressDialog();
                        ((CartListActivity) context).removedItemInCartSuccess();
                    }
                });


    }

    public void instantUpdateItemQuantity(String cartId, HashMap<String, Object> itemCartHashMap) {
        db.collection(Contract.CART_ITEM)
                .document(cartId)
                .update(itemCartHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ((CartListActivity) context).updateItemInCartSuccess();
                    }
                });
    }


    public void updateCartItemId(String cartId) {

        HashMap<String, Object> itemCartHashMap = new HashMap<>();
        itemCartHashMap.put("id", cartId);
        db.collection(Contract.CART_ITEM)
                .document(cartId)
                .update(itemCartHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });
    }


    /**
     * ***************************************** @Address ******************************************
     */

    public void uploadAddressToCloud(Address addressInfo) {
        ((AddressSettingActivity) context).showProgressDialog(context, "Adding to Cart...");
        db.collection(Contract.ADDRESS)
                .document()
                .set(addressInfo, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ((AddressSettingActivity) context).uploadedSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void getAddressListFromCloud() {
        ((AddressListActivity) context).showProgressDialog(context, "Getting Address...");
        db.collection(Contract.ADDRESS)
                .whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Address> addressList = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Address address = documentSnapshot.toObject(Address.class);
                            address.setId(documentSnapshot.getId());
                            updateAddressId(documentSnapshot.getId());
                            addressList.add(address);
                        }

                        ((AddressListActivity) context).getAddressListSuccess(addressList);
                        ((AddressListActivity) context).showAddressList(addressList);
                    }
                });
    }


    public void updateAddressId(String addressId) {

        HashMap<String, Object> itemCartHashMap = new HashMap<>();
        itemCartHashMap.put("id", addressId);
        db.collection("address")
                .document(addressId)
                .update(itemCartHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });
    }

    public void updateAddressToCloud(Address myAddress) {


        db.collection("address")
                .document(myAddress.getId())
                .set(myAddress, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ((AddressSettingActivity) context).updatedSuccess();
                    }
                });
    }

    /**
     * ***************************************** @Order ******************************************
     */

    public void uploadOrderToCloud(final CartItem item, Order order) {

        db.collection("orders")
                .document()
                .set(order, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadOrderToCloudAndSetId(item);
                    }
                });
    }

    public void uploadOrderToCloudAndSetId(final CartItem item) {
        final ArrayList<Order> orderArrayList = new ArrayList<>();
        db.collection("orders")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Order order = null;
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            order = document.toObject(Order.class);
                            order.setId(document.getId());
                            updateOrderId(order.getId());
                            orderArrayList.add(order);
                        }
                        uploadSoldProductToCloud(item,order);
                        ((CheckoutActivity) context).uploadOrderToCloudSuccess(order);
                    }
                });
    }

   public void uploadSoldProductToCloud(CartItem item, final Order myOrder){

            SoldProduct soldProduct = new SoldProduct(
                    null,
                    item.getOwner_id(),
                    item.getTitle(),
                    item.getPrice(),
                    item.getCart_quantity(),
                    item.getImage(),
                    myOrder.getId(),
                    null,
                    myOrder.getSubTotalAmount(),
                    myOrder.getShippingCharge(),
                    myOrder.getTotalAmount(),
                    myOrder.getAddress()
            );

            db.collection("sold_products")
                    .document()
                    .set(soldProduct, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ((CheckoutActivity)context).uploadOrderToCloudSuccess(myOrder);
                        }
                    });



    }

    public void updateOrderId(String orderId) {

        HashMap<String, Object> itemCartHashMap = new HashMap<>();
        itemCartHashMap.put("id", orderId);
        db.collection("orders")
                .document(orderId)
                .update(itemCartHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });
    }

    int i =0;

    public void updateAllProductAfterPlaceOrder(final CheckoutActivity checkoutActivity, CartItem item) {
        WriteBatch writter = db.batch();

        // Update Product Quantity


//

        i++;
        Log.e("sold_product", ""+i );

        HashMap<String, Object> productHashmap = new HashMap<>();
        productHashmap.put("stock_quantity", item.getStock_quantity() - item.getCart_quantity());
        DocumentReference documentReference2 = db.collection("products").document(item.getProduct_id());
        writter.update(documentReference2,productHashmap);
        // Delete Cart Item After Purchase

        DocumentReference documentReference3 = db.collection("cart_item").document(item.getId());
        writter.delete(documentReference3);


        writter.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                checkoutActivity.updateAllProductAfterPlaceOrderSuccess();
            }
        });
    }

    /**
     * ***************************************** @Sold Product ******************************************
     */

    public void getMySoldProduct(final Fragment fragment){
        final ArrayList<SoldProduct> soldProductArrayList = new ArrayList<>();
        db.collection("sold_products")
                .whereEqualTo("owner_id", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            SoldProduct soldProduct = documentSnapshot.toObject(SoldProduct.class);
                            soldProductArrayList.add(soldProduct);
                        }

                        (  (SoldProductsFragment) fragment).getSoldProductSuccess(soldProductArrayList);
                    }

                });
    }
}
