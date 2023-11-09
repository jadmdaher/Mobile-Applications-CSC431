package com.example.refinedapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.databinding.ActivityUpdateBinding;
import com.example.refinedapplication.databinding.ActivityViewBinding;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    private ActivityUpdateBinding updateActivityBinding;
    RestaurantsListViewModel restaurantsListViewModel;
    private ActivityViewBinding viewBinding;
    RestaurantDBHelper restaurantDBHelper;
    List<Restaurant> restaurantFromDB;
    ArrayAdapter<String> categoryAdapter;
    int updatedRating;
    String updatedCategory = "Default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("position", -1);
        updateActivityBinding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(updateActivityBinding.getRoot());
        //Retrieve the RestaurantDBHelper instance from the application class
        restaurantDBHelper = ((MyApp)getApplication()).getRestaurantDBHelper();
        //Fill the restaurantFromDB from the Database
        restaurantFromDB = restaurantDBHelper.getAllRestaurants();
        //Retrieve the RestaurantsListViewModel instance from the application class
        restaurantsListViewModel = ((MyApp)getApplication()).restaurantsListViewModel;
        //Set the restaurant categories from the database
        restaurantsListViewModel.setRestaurantCategories(restaurantFromDB);
        //Add the default category to the list of categories so list is not empty
        restaurantsListViewModel.addRestaurantCategory("Default");
        //Set the adapter for the autoCompleteTextView
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantsListViewModel.getRestaurantCategories());
        updateActivityBinding.formFields.updateAutoCompleteTextView.setAdapter(categoryAdapter);

        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(this, restaurantFromDB);
        viewBinding = ActivityViewBinding.inflate(getLayoutInflater());
        viewBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.rv.setAdapter(restaurantRecyclerViewAdapter);

        //Click listener for the updateAutoCompleteTextView
        updateActivityBinding.formFields.updateAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updatedCategory = adapterView.getItemAtPosition(i).toString();
            }
        });

        if(position >= 0){
            updateActivityBinding.updateButton.setOnClickListener(v->update_restaurant(position));
            loadFormData(position);
        }
        updateActivityBinding.formFields.updatedStarIcon1.setOnClickListener(v -> setStarRating(1));
        updateActivityBinding.formFields.updatedStarIcon2.setOnClickListener(v -> setStarRating(2));
        updateActivityBinding.formFields.updatedStarIcon3.setOnClickListener(v -> setStarRating(3));
        updateActivityBinding.formFields.updatedStarIcon4.setOnClickListener(v -> setStarRating(4));
        updateActivityBinding.formFields.updatedStarIcon5.setOnClickListener(v -> setStarRating(5));
    }

    public void loadFormData(int position){
        Restaurant restaurant = restaurantsListViewModel.getRestaurantsList().get(position);
        updateActivityBinding.formFields.updateName.setText(restaurant.getName());
        updateActivityBinding.formFields.updateAddress.setText(restaurant.getAddress());
        updateActivityBinding.formFields.updatePhone.setText(restaurant.getPhone());
        updateActivityBinding.formFields.updateWeb.setText(restaurant.getWeb());
        updateActivityBinding.formFields.updateOnTable.setChecked(restaurant.isOnTable());
        updateActivityBinding.formFields.updateDelivery.setChecked(restaurant.isDelivery());
        updateActivityBinding.formFields.updateTakeAway.setChecked(restaurant.isTakeAway());
        setStarRating(restaurant.getRating());
    }

    private void setStarRating(int rating) {
        updatedRating = rating;

        ImageView starIcon1 = updateActivityBinding.formFields.updatedStarIcon1;
        ImageView starIcon2 = updateActivityBinding.formFields.updatedStarIcon2;
        ImageView starIcon3 = updateActivityBinding.formFields.updatedStarIcon3;
        ImageView starIcon4 = updateActivityBinding.formFields.updatedStarIcon4;
        ImageView starIcon5 = updateActivityBinding.formFields.updatedStarIcon5;

        ImageView[] starIcons = new ImageView[]{
                starIcon1, starIcon2, starIcon3, starIcon4, starIcon5
        };

        for (int i = 0; i < starIcons.length; i++) {
            if (i < rating) {
                starIcons[i].setColorFilter(Color.parseColor("#FFD700"));
            } else {
                starIcons[i].setColorFilter(Color.parseColor("#FF999999"));
            }
        }
    }

    private void update_restaurant(int position) {
        String name, address, phone, web, category;
        int rating;
        Restaurant restaurant;
        name = updateActivityBinding.formFields.updateName.getText().toString();
        address = updateActivityBinding.formFields.updateAddress.getText().toString();
        phone = updateActivityBinding.formFields.updatePhone.getText().toString();
        web = updateActivityBinding.formFields.updateWeb.getText().toString();
        rating = updatedRating;
        category = updatedCategory;
        restaurant = new Restaurant(name, address, phone, web);
        restaurant.setOnTable(updateActivityBinding.formFields.updateOnTable.isChecked());
        restaurant.setDelivery(updateActivityBinding.formFields.updateDelivery.isChecked());
        restaurant.setTakeAway(updateActivityBinding.formFields.updateTakeAway.isChecked());
        setStarRating(rating);
        restaurant.setRating(rating);
        restaurant.setCategory(category);
        restaurant.setId_(restaurantFromDB.get(position).getId_());
        restaurantDBHelper.updateRestaurant(restaurant);
        restaurantsListViewModel.getRestaurantsList().set(position, restaurant);
        restaurantFromDB.set(position, restaurant);
        finish();
    }
}