package com.example.refinedapplication.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class RestaurantDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurants.db";
    private static final int SCHEMA_VERSION = 2;

    public RestaurantDBHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        getWritableDatabase();
        Log.d("Database", "Calling constructor of open helper");
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, phone TEXT, web TEXT, onTable BOOLEAN, delivery BOOLEAN, takeaway BOOLEAN);");
        Log.d("Database", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("Database", "onUpdate");
    }

    public long inser(Restaurant restaurant){
        ContentValues contentValues;

        contentValues = new ContentValues();
        contentValues.put("name", restaurant.getName());
        contentValues.put("address", restaurant.getAddress());
        contentValues.put("phone", restaurant.getPhone());
        contentValues.put("web", restaurant.getWeb());
        contentValues.put("onTable", restaurant.isOnTable());
        contentValues.put("delivery", restaurant.isDelivery());
        contentValues.put("takeaway", restaurant.isTakeAway());

        long id = getWritableDatabase().insert("restaurants", null, contentValues);

        return id;
    }

    public ArrayList<Restaurant> getAllRestaurants(){
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        String query = "SELECT * FROM restaurants";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        cursor.moveToFirst();
        //
        while(!cursor.isAfterLast()){
            long id_ = cursor.getLong(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String phone = cursor.getString(3);
            String web = cursor.getString(4);
            boolean onTable = cursor.getInt(5) == 1;
            boolean delivery = cursor.getInt(6) == 1;
            boolean takeaway = cursor.getInt(7) == 1;
            Restaurant restaurant = new Restaurant(name, address, onTable, delivery, takeaway, web, phone);
            restaurant.setDelivery(delivery);
            restaurant.setOnTable(onTable);
            restaurant.setTakeAway(takeaway);
            restaurant.setId_(id_);
            restaurantList.add(restaurant);
            cursor.moveToNext();
        }
        return restaurantList;
    }

    public void updateRestaurant(Restaurant restaurant){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", restaurant.getName());
        contentValues.put("address", restaurant.getAddress());
        contentValues.put("phone", restaurant.getPhone());
        contentValues.put("web", restaurant.getWeb());
        contentValues.put("onTable", restaurant.isOnTable());
        contentValues.put("delivery", restaurant.isDelivery());
        contentValues.put("takeaway", restaurant.isTakeAway());

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(restaurant.getId_())};

        getWritableDatabase().update("restaurants", contentValues, whereClause, whereArgs);
    }

    public void deleteRestaurant(Restaurant restaurant){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(restaurant.getId_())};

        getWritableDatabase().delete("restaurants", whereClause, whereArgs);
    }

    public int restaurantCount(){
        String query = "SELECT  count(*) as count FROM restaurants";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        cursor.moveToFirst();
        int count = cursor.getInt(0);

        return count;
    }
}
