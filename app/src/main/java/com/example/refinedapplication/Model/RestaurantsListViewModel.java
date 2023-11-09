package com.example.refinedapplication.Model;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantsListViewModel extends ViewModel {
    private List<Restaurant> restaurantsList = new ArrayList<>();
    private Set<String> restaurantCategories = new HashSet<>();
    private List<String> categoriesList = new ArrayList<>();
    private RestaurantDBHelper restaurantDBHelper;
    private int counter = 0;

    public void setRestaurantsList(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }

    public List<Restaurant> getRestaurantsList() {
        return restaurantsList;
    }

    public void addRestaurant(Restaurant restaurant){
        restaurantsList.add(restaurant);
        counter++;
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

    public void setRestaurantCategories(List<Restaurant> restaurantList){
        for(Restaurant restaurant : restaurantList){
            restaurantCategories.add(restaurant.getCategory());
        }

        for(String category : restaurantCategories){
            for(int i = 0; i < categoriesList.size(); i++){
                if(categoriesList.contains(category)){
                    return;
                }
            }
            categoriesList.add(category);
        }
    }

    public List<String> getRestaurantCategories(){
        return categoriesList;
    }

    public void addRestaurantCategory(String category){
        for(int i = 0; i < categoriesList.size(); i++){
            if(categoriesList.contains(category)){
                return;
            }
        }
        categoriesList.add(category);
    }

    public void removeRestaurantCategory(String category){
        categoriesList.remove(category);;
    }

    public void updateRestaurantCategory(String oldCategory, String newCategory){
        categoriesList.set(categoriesList.indexOf(oldCategory), newCategory);
    }

    public RestaurantDBHelper getRestaurantDBHelper(){ return restaurantDBHelper; }
    public void setRestaurantDBHelper(RestaurantDBHelper restaurantDBHelper){ this.restaurantDBHelper = restaurantDBHelper; }
}
