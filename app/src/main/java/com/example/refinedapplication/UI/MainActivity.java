package com.example.refinedapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.R;
import com.example.refinedapplication.databinding.ActivityMainBinding;
import com.example.refinedapplication.databinding.ActivityViewBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    private ActivityMainBinding mainBinding;
    private ActivityViewBinding viewBinding;
    private ArrayAdapter<Restaurant> restaurantArrayAdapter;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    private RestaurantDBHelper restaurantDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instantiate mainBinding
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        //Create an instance of RestaurantsListViewModel
        restaurantsListViewModel = new ViewModelProvider(this).get(RestaurantsListViewModel.class);
        ((MyApp)getApplication()).restaurantsListViewModel = restaurantsListViewModel;
        //Add some restaurants
        //addSomeRestaurants();
        //Set view of screen to be activity_main.xml
//        setContentView(mainBinding.getRoot());

        restaurantDBHelper = new RestaurantDBHelper(this);
        ((MyApp)getApplication()).setRestaurantDBHelper(restaurantDBHelper);

        //Set Text
        mainBinding.totalRestaurant.setText("Total Restaurants: " + restaurantDBHelper.restaurantCount());

        restaurantsListViewModel.setRestaurantsList(restaurantDBHelper.getAllRestaurants());

        //Initialize restaurantArrayAdapter
        restaurantArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantDBHelper.getAllRestaurants());

        List<Restaurant> restaurantList = restaurantDBHelper.getAllRestaurants();
        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, restaurantList);
        viewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        viewBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.rv.setAdapter(restaurantRecyclerViewAdapter);

//        restaurantDBHelper.updateRestaurant(new Restaurant("Burger King", "Address 1", "Phone 1", "Web 1", true, false, false, 1, "fast food"));

        //Set view of screen to be activity_main.xml
        setContentView(mainBinding.getRoot());
        //Activity Lifecycle
        Log.d("activity_lifecycle", "MainActivity created");
        //Database creation
    }

    //(Start) Menu related methods:
    //The onCreate method for the main_menu.xml file
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    /*
     * Method used to specify which view
     * the user see when a certain item is
     * selected from the menu items
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home_menu){
        }

        if(item.getItemId() == R.id.add_menu){
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.view_menu){
            Intent intent = new Intent(this, ViewActivity.class);
            startActivity(intent);
        }

        return true;
    }
    //(End) Menu related methods.

    //(Start) Activity Lifecycle Methods:
    public void onStart() {
        super.onStart();
        Log.d("activity_lifecycle", "MainActivity started");
    }

    public void onResume() {
        super.onResume();
        // Update the "Total Restaurant" TextView
        if (mainBinding != null) {
            int totalRestaurants = restaurantDBHelper.restaurantCount();
            mainBinding.totalRestaurant.setText(String.valueOf(totalRestaurants));
            restaurantArrayAdapter.notifyDataSetChanged();
        }
        Log.d("activity_lifecycle", "MainActivity resumed");
    }

    public void onPause() {
        super.onPause();
        Log.d("activity_lifecycle", "MainActivity paused");
    }

    public void onStop() {
        super.onStop();
        Log.d("activity_lifecycle", "MainActivity stopped");
    }

    public void onRestart(){
        super.onRestart();
        Log.d("activity_lifecycle", "MainActivity restarted");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d("activity_lifecycle", "MainActivity destroyed");
    }
    //(End) Activity Lifecycle Methods.
}