package com.example.refinedapplication.UI;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.UI.RestaurantRecyclerViewAdapter;
import com.example.refinedapplication.databinding.ActivityViewBinding;

public class ViewActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    private ActivityViewBinding viewBinding;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instantiate bindings
        viewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        //Set view of screen to be activity_view.xml
        setContentView(viewBinding.getRoot());
        //Create an instance of RestaurantsListViewModel
        restaurantsListViewModel = ((MyApp)getApplication()).restaurantsListViewModel;
        //Instantiate myRecyclerViewAdapter and specify constraints for UI elements
        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, restaurantsListViewModel.getRestaurantsList());
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
    }
    //(End) Activity Lifecycle Methods.
}