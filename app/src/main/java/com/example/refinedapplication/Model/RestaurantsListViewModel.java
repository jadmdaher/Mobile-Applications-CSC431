package com.example.refinedapplication.Model;

import androidx.lifecycle.ViewModel;

import com.example.refinedapplication.MyApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantsListViewModel extends ViewModel {
    private List<Restaurant> restaurantsList = new ArrayList<>();
    private RestaurantDBHelper restaurantDBHelper;
    private int counter = 0;

    public void setRestaurantsList(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }

    public void addRestaurant(Restaurant restaurant){
        restaurantsList.add(restaurant);
        counter++;
    }

    public List<Restaurant> getRestaurantsList() {
        //List<Restaurant> immutableRestaurantsList = Collections.unmodifiableList(restaurantsList);
        return restaurantsList;
    }

    public Restaurant getRestaurant(int position){
        return restaurantsList.get(position);
    }

    public int size(){
        return counter;
    }

    public void delete(int position) {
        restaurantsList.remove(position);
        counter--;
    }

    public RestaurantDBHelper getRestaurantDBHelper(){ return restaurantDBHelper; }
    public void setRestaurantDBHelper(RestaurantDBHelper restaurantDBHelper){ this.restaurantDBHelper = restaurantDBHelper; }
}
