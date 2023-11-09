package com.example.refinedapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable{
    //Category variable with setters and getters
    private String category;

    public String getCategory() { return category; }
    public String setCategory(String category) { return this.category = category; }

    //Rating variable with setters and getters
    private int rating;

    public int getRating() { return rating; }
    public int setRating(int rating) { return this.rating = rating; }

    //ID variable with setters and getters
    private long id_;

    public long getId_() {
        return id_;
    }
    public void setId_(long id_) {
        this.id_ = id_;
    }

    //Name variable with setters and getters
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Address variable with setters and getters
    private String address;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    //Phone variable with setters and getters
    private String phone;

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    //Web variable with setters and getters
    private String web;

    public String getWeb() { return web; }

    public void setWeb(String web) { this.web = web; }

    //onTable variable with setters and getters
    private boolean onTable;

    public boolean isOnTable() {
        return onTable;
    }
    public void setOnTable(boolean onTable) {
        this.onTable = onTable;
    }

    //Delivery variable with setters and getters
    private boolean delivery;

    public boolean isDelivery() {
        return delivery;
    }
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    //takeAway variable with setters and getters
    private boolean takeAway;

    public boolean isTakeAway() {
        return takeAway;
    }
    public void setTakeAway(boolean takeAway) {
        this.takeAway = takeAway;
    }

    //Constructor
    public Restaurant(long id_, String name, String address, String phone, String web, boolean onTable, boolean delivery, boolean takeAway, int rating, String category) {
        this.id_ = id_;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.web = web;
        this.onTable = onTable;
        this.delivery = delivery;
        this.takeAway = takeAway;
        this.rating = rating;
        this.category = category;
    }

    public Restaurant(String name, String address, String phone, String web, boolean onTable, boolean delivery, boolean takeAway, int rating, String category) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.web = web;
        this.onTable = onTable;
        this.delivery = delivery;
        this.takeAway = takeAway;
        this.rating = rating;
        this.category = category;
    }

    public Restaurant(String name, String address, String phone, String web) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.web = web;
    }

    @Override
    public String toString() {
        return "Restaurant name: " + name + ", " +
                "address: " + address + ", " +
                "ontable: " + onTable + ", " +
                "delivery: " + delivery + ", " +
                "takeaway: " + takeAway + ", " +
                "phone: " + phone + ", " +
                "web: " + web;
    }

    public Restaurant(Parcel in) {
        name = in.readString();
        address = in.readString();
        phone=in.readString();
        web = in.readString();
        onTable = in.readByte() != 0;
        delivery = in.readByte() != 0;
        takeAway = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in){
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(web);
        dest.writeByte((byte) (onTable ? 1 : 0));
        dest.writeByte((byte) (delivery ? 1 : 0));
        dest.writeByte((byte) (takeAway ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
