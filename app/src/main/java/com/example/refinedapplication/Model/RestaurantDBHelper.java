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
    private static final int SCHEMA_VERSION = 3;

    public RestaurantDBHelper(Context context){
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        getWritableDatabase();
        Log.d("Database", "Calling constructor of open helper");
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, phone TEXT, web TEXT, onTable BOOLEAN, delivery BOOLEAN, takeaway BOOLEAN, rating INTEGER, category TEXT);");
        Log.d("Database", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 > i) {
            // Drop the existing tables
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "restaurants");

            // Recreate the tables by calling onCreate
            onCreate(sqLiteDatabase);
        }
        Log.d("Database", "onUpgrade");
    }

    public long insert(Restaurant restaurant){
        ContentValues contentValues;

        contentValues = new ContentValues();
        contentValues.put("name", restaurant.getName());
        contentValues.put("address", restaurant.getAddress());
        contentValues.put("phone", restaurant.getPhone());
        contentValues.put("web", restaurant.getWeb());
        contentValues.put("onTable", restaurant.isOnTable());
        contentValues.put("delivery", restaurant.isDelivery());
        contentValues.put("takeaway", restaurant.isTakeAway());
        contentValues.put("rating", restaurant.getRating());
        contentValues.put("category", restaurant.getCategory());

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
            int rating = cursor.getInt(8);
            String category = cursor.getString(9);
            Restaurant restaurant = new Restaurant(id_, name, address, phone, web, onTable, delivery, takeaway, rating, category);
            restaurant.setDelivery(delivery);
            restaurant.setOnTable(onTable);
            restaurant.setTakeAway(takeaway);
            restaurant.setId_(id_);
            restaurant.setRating(rating);
            restaurant.setCategory(category);
            restaurantList.add(restaurant);
            cursor.moveToNext();
        }
        return restaurantList;
    }

    public ArrayList<Restaurant> getRestaurantsByCategory(String categoryName){
        ArrayList<Restaurant> filteredRestaurants = new ArrayList<>();
        String query;

        if((categoryName == null)){
            query = "SELECT * FROM restaurants";
        } else if ((categoryName.equals("Default"))) {
            query = "SELECT * FROM restaurants";
        } else{
            query = "SELECT * FROM restaurants WHERE category = '" + categoryName + "'";
        }

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
            int rating = cursor.getInt(8);
            String category = cursor.getString(9);
            Restaurant restaurant = new Restaurant(id_, name, address, phone, web, onTable, delivery, takeaway, rating, category);
            restaurant.setDelivery(delivery);
            restaurant.setOnTable(onTable);
            restaurant.setTakeAway(takeaway);
            restaurant.setId_(id_);
            restaurant.setRating(rating);
            restaurant.setCategory(category);
            filteredRestaurants.add(restaurant);
            cursor.moveToNext();
        }
        return filteredRestaurants;
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
        contentValues.put("rating", restaurant.getRating());
        contentValues.put("category", restaurant.getCategory());

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

    //Methods that handle operations on categories
    public void updateCategory(String oldCategory, String newCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", newCategory);

        String whereClause = "category = ?";
        String[] whereArgs = new String[]{String.valueOf(oldCategory)};

        getWritableDatabase().update("restaurants", contentValues, whereClause, whereArgs);
    }

    public void deleteCategory(String category){
        ContentValues contentValues = new ContentValues();
        contentValues.put("category", "Default");

        String whereClause = "category = ?";
        String[] whereArgs = new String[]{String.valueOf(category)};

        getWritableDatabase().update("restaurants", contentValues, whereClause, whereArgs);
    }
}
