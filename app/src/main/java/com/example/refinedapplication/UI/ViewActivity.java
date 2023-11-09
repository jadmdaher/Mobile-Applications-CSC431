package com.example.refinedapplication.UI;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.databinding.ActivityViewBinding;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    private DrawerLayout drawerLayout;
    private ImageView buttonImageView;
    private ActivityViewBinding viewBinding;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    private ImageView callButton;
    RestaurantDBHelper restaurantDBHelper;
    List<Restaurant> restaurantFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantDBHelper = ((MyApp)getApplication()).getRestaurantDBHelper();
        restaurantFromDB = restaurantDBHelper.getAllRestaurants();
        //Instantiate bindings
        viewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        //Set view of screen to be activity_view.xml
        setContentView(viewBinding.getRoot());
        //Create an instance of RestaurantsListViewModel
        restaurantsListViewModel = ((MyApp)getApplication()).restaurantsListViewModel;
        //Set the restaurant list from the database
        restaurantsListViewModel.setRestaurantsList(restaurantFromDB);
        //Instantiate myRecyclerViewAdapter and specify constraints for UI elements
        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, restaurantFromDB);
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
}