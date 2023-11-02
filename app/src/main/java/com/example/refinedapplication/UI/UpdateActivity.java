package com.example.refinedapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.R;
import com.example.refinedapplication.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {
    private ActivityUpdateBinding updateActivityBinding;
    RestaurantsListViewModel restaurantsListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("position", -1);
        updateActivityBinding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(updateActivityBinding.getRoot());
        restaurantsListViewModel = ((MyApp)getApplication()).restaurantsListViewModel;
        if(position >= 0){
            updateActivityBinding.updateButton.setOnClickListener(v->update_restaurant(position));
            loadFormData(position);
        }
    }

    public void loadFormData(int position){
        Restaurant restaurant = restaurantsListViewModel.getRestaurantsList().get(position);
        updateActivityBinding.formFields.name.setText(restaurant.getName());
        updateActivityBinding.formFields.address.setText(restaurant.getAddress());
        updateActivityBinding.formFields.phone.setText(restaurant.getPhone());
        updateActivityBinding.formFields.web.setText(restaurant.getWeb());
        updateActivityBinding.formFields.onTable.setChecked(restaurant.isOnTable());
        updateActivityBinding.formFields.delivery.setChecked(restaurant.isDelivery());
        updateActivityBinding.formFields.takeAway.setChecked(restaurant.isTakeAway());
    }

    private void update_restaurant(int position) {
        String name, address, phone, web;
        Restaurant restaurant;
        name = updateActivityBinding.formFields.name.getText().toString();
        address = updateActivityBinding.formFields.address.getText().toString();
        phone = updateActivityBinding.formFields.phone.getText().toString();
        web = updateActivityBinding.formFields.web.getText().toString();
        restaurant = new Restaurant(name, address, phone, web);
        restaurant.setOnTable(updateActivityBinding.formFields.onTable.isChecked());
        restaurant.setDelivery(updateActivityBinding.formFields.delivery.isChecked());
        restaurant.setTakeAway(updateActivityBinding.formFields.takeAway.isChecked());
        restaurantsListViewModel.getRestaurantsList().set(position, restaurant);
        finish();
    }
}