package com.example.refinedapplication;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;

public class MyApp extends Application {
    public static RestaurantsListViewModel restaurantsListViewModel;
    public static RestaurantDBHelper restaurantDBHelper;
    private static Context context;

    public RestaurantDBHelper getRestaurantDBHelper(){ return restaurantDBHelper; }
    public void setRestaurantDBHelper(RestaurantDBHelper restaurantDBHelper){ this.restaurantDBHelper = restaurantDBHelper; }
    public static Context getContext(){ return context; }

    public RestaurantsListViewModel getRestaurantsListViewModel(){
        if(restaurantsListViewModel == null){
            restaurantsListViewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(RestaurantsListViewModel.class);
        }
        return restaurantsListViewModel;
    }

//    public void onCreate(){
//        super.onCreate();
//        context = getApplicationContext();
//    }
//
//    public static Context getAppContext() {
//        return MyApp.context;
//    }
}