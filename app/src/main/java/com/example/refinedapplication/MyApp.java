package com.example.refinedapplication;

import android.app.Application;
import androidx.lifecycle.ViewModelProvider;

import com.example.refinedapplication.Model.RestaurantsListViewModel;

public class MyApp extends Application {
    public static RestaurantsListViewModel restaurantsListViewModel;

    public RestaurantsListViewModel getRestaurantsListViewModel(){
        if(restaurantsListViewModel == null){
            restaurantsListViewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(RestaurantsListViewModel.class);
        }
        return restaurantsListViewModel;
    }
}