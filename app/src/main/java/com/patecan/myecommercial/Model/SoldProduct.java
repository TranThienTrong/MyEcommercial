package com.patecan.myecommercial.Model;

public class SoldProduct {
    String id;
    String owner_id;
    String title;
    String price;
    int sold_quantity;
    String image;
    String order_id;
    String order_date;
    String subTotal;
    String shippingCharge;
    String totalAmount;
    Address address;

    public SoldProduct(){}

    public SoldProduct(String id, String owner_id, String title, String price, int sold_quantity, String image, String order_id, String order_date, String subTotal, String shippingCharge, String totalAmount, Address address) {
        this.id = id;
        this.owner_id = owner_id;
        this.title = title;
        this.price = price;
        this.sold_quantity = sold_quantity;
        this.image = image;
        this.order_id = order_id;
        this.order_date = order_date;
        this.subTotal = subTotal;
        this.shippingCharge = shippingCharge;
        this.totalAmount = totalAmount;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String user_id) {
        this.owner_id = user_id;
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

    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
