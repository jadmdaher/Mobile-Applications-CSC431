package com.example.refinedapplication.Model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantsListViewModel extends ViewModel {
    private List<Restaurant> restaurantsList = new ArrayList<>();
    private int counter = 0;

    public void addRestaurant(Restaurant restaurant){
        restaurantsList.add(restaurant);
        counter++;
    }

    public List<Restaurant> getRestaurantsList() {
        List<Restaurant> immutableRestaurantsList = Collections.unmodifiableList(restaurantsList);
        return immutableRestaurantsList;
    }

    public int size(){
        return counter;
    }
}
