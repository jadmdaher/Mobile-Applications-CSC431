package com.example.refinedapplication.UI;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.databinding.ActivityViewBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    private ActivityViewBinding viewBinding;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    RestaurantDBHelper restaurantDBHelper;
    List<Restaurant> restaurantFromDB;
    ArrayAdapter<String> categoryAdapter;
    String categoryGlobal;
    private RestaurantRecyclerViewAdapter filterRestaurantRecyclerViewAdapter;
    List<Restaurant> filteredRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve the RestaurantDBHelper instance from the application class
        restaurantDBHelper = ((MyApp)getApplication()).getRestaurantDBHelper();
        //Fill the restaurantFromDB from the Database
        restaurantFromDB = restaurantDBHelper.getAllRestaurants();
//        filteredRestaurants = restaurantDBHelper.getAllRestaurants();
        //Instantiate bindings
        viewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        //Set view of screen to be activity_view.xml
        setContentView(viewBinding.getRoot());
        //Retrieve the RestaurantsListViewModel instance from the application class
        restaurantsListViewModel = ((MyApp)getApplication()).restaurantsListViewModel;
        //Set the restaurant categories from the database
        restaurantsListViewModel.setRestaurantCategories(restaurantFromDB);
        //Add the default category to the list of categories so list is not empty
        restaurantsListViewModel.addRestaurantCategory("Default");
        //Set the restaurant list from the database
        restaurantsListViewModel.setRestaurantsList(restaurantFromDB);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantsListViewModel.getRestaurantCategories());
        viewBinding.filterAutoCompleteTextView.setAdapter(categoryAdapter);

//        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, restaurantFromDB);

        //Click listener for the autoCompleteTextView
        viewBinding.filterAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryGlobal = adapterView.getItemAtPosition(i).toString();
                restaurantRecyclerViewAdapter.setRestaurantList(restaurantDBHelper.getRestaurantsByCategory(categoryGlobal));
                restaurantRecyclerViewAdapter.notifyDataSetChanged();
//                filterRestaurantsByCategory(restaurantRecyclerViewAdapter.getRestaurantList(), categoryGlobal);
            }
        });
        //Instantiate myRecyclerViewAdapter and specify constraints for UI elements
        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, restaurantDBHelper.getRestaurantsByCategory(categoryGlobal));
//        restaurantRecyclerViewAdapter.notifyDataSetChanged();
//        filterRestaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, filteredRestaurants);
//        if((categoryGlobal == null) || (categoryGlobal.equals("Default"))){
//            viewBinding.rv.setAdapter(restaurantRecyclerViewAdapter);
//        }else{
////            filterRestaurantsByCategory(restaurantFromDB, categoryGlobal);
//            viewBinding.rv.setAdapter(filterRestaurantRecyclerViewAdapter);
//        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.rv.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            viewBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        }
        viewBinding.rv.setAdapter(restaurantRecyclerViewAdapter);
    }

    //(Start) Activity Lifecycle Methods:
    @Override
    protected void onResume() {
        super.onResume();
        restaurantRecyclerViewAdapter.notifyDataSetChanged();
        Log.d("activity_lifecycle", "ViewActivity resumed");
    }
    //(End) Activity Lifecycle Methods.

    public void filterRestaurantsByCategory(/*RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter, */List<Restaurant> filteredRestaurants, String category){

        if(!(category == null) || !(category.equals("Default"))){
            for(Restaurant restaurant : filteredRestaurants){
                if(!restaurant.getCategory().equals(category)){
                    filteredRestaurants.remove(restaurant);
                }
            }
            restaurantRecyclerViewAdapter.notifyDataSetChanged();
        }

//        restaurantRecyclerViewAdapter.setRestaurantList(filteredRestaurants);
//        restaurantRecyclerViewAdapter.notifyDataSetChanged();
    }
}