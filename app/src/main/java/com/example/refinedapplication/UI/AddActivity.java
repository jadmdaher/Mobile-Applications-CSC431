package com.example.refinedapplication.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    private ActivityAddBinding addBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instantiate bindings
        addBinding = ActivityAddBinding.inflate(getLayoutInflater());
        //Set view of screen to be add_activity.xml
        setContentView(addBinding.getRoot());
        //Create an intance of RestaurantsListViewModel
        restaurantsListViewModel = ((MyApp)getApplication()).restaurantsListViewModel;

        addBinding.addButton.setOnClickListener(v -> addRestaurant());
    }

    public void addRestaurant(){
        String name = addBinding.nameID.getText().toString();
        String address = addBinding.addressID.getText().toString();
        boolean ontable = addBinding.onTableID.isChecked();
        boolean delivery = addBinding.deliveryID.isChecked();
        boolean takeaway = addBinding.takeAwayID.isChecked();
        String web = addBinding.webID.getText().toString();
        String phone = addBinding.phoneID.getText().toString();
        Restaurant restaurant = new Restaurant(name, address, web, phone, ontable, delivery, takeaway);
        restaurantsListViewModel.addRestaurant(restaurant);
        restaurantsListViewModel = ((MyApp)getApplication()).getRestaurantsListViewModel();
        finish();
    }
}