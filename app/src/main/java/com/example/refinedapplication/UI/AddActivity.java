package com.example.refinedapplication.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.R;
import com.example.refinedapplication.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    RestaurantDBHelper restaurantDBHelper;
    private ActivityAddBinding addBinding;
    int ratingGlobal;

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
        addBinding.starIcon1.setOnClickListener(v -> setStarRating(1));
        addBinding.starIcon2.setOnClickListener(v -> setStarRating(2));
        addBinding.starIcon3.setOnClickListener(v -> setStarRating(3));
        addBinding.starIcon4.setOnClickListener(v -> setStarRating(4));
        addBinding.starIcon5.setOnClickListener(v -> setStarRating(5));
    }

    public void addRestaurant(){
        String name = addBinding.nameID.getText().toString();
        String address = addBinding.addressID.getText().toString();
        String phone = addBinding.phoneID.getText().toString();
        String web = addBinding.webID.getText().toString();
        boolean ontable = addBinding.onTableID.isChecked();
        boolean delivery = addBinding.deliveryID.isChecked();
        boolean takeaway = addBinding.takeAwayID.isChecked();
        int rating = ratingGlobal;
        String category = addBinding.categoryID.getText().toString();
        Restaurant restaurant = new Restaurant(name, address, phone, web, ontable, delivery, takeaway, rating, category);
        restaurantsListViewModel.addRestaurant(restaurant);
        restaurantsListViewModel = ((MyApp)getApplication()).getRestaurantsListViewModel();
        restaurantDBHelper = ((MyApp)getApplication()).getRestaurantDBHelper();
        restaurantDBHelper.insert(restaurant);
        finish();
    }

    private void setStarRating(int rating) {
        ratingGlobal = rating;

        ImageView starIcon1 = findViewById(R.id.star_icon1);
        ImageView starIcon2 = findViewById(R.id.star_icon2);
        ImageView starIcon3 = findViewById(R.id.star_icon3);
        ImageView starIcon4 = findViewById(R.id.star_icon4);
        ImageView starIcon5 = findViewById(R.id.star_icon5);

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

}