package com.example.refinedapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.databinding.ActivityCategoryOperationsBinding;
import java.util.List;

public class CategoryOperationsActivity extends AppCompatActivity {
    ActivityCategoryOperationsBinding categoryOperationsBinding;
    RestaurantDBHelper restaurantDBHelper;
    RestaurantsListViewModel restaurantsListViewModel;
    ArrayAdapter<String> categoryAdapter;
    String categoryGlobal;
    List<Restaurant> restaurantFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryOperationsBinding = ActivityCategoryOperationsBinding.inflate(getLayoutInflater());
        setContentView(categoryOperationsBinding.getRoot());
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
        //Click listener for the addCategoryButton
        categoryOperationsBinding.addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory(categoryOperationsBinding.addCategory.getText().toString());
                categoryOperationsBinding.addCategory.setText(null);
            }
        });
        //Set the adapter for the modifyAutoCompleteTextView
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantsListViewModel.getRestaurantCategories());
        categoryOperationsBinding.modifyAutoCompleteTextView.setAdapter(categoryAdapter);
        //Click listener for the modifyAutoCompleteTextView
        categoryOperationsBinding.modifyAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryGlobal = adapterView.getItemAtPosition(i).toString();
//                categoryOperationsBinding.updateCategoryButton.setOnClickListener(v -> updateCategory(categoryGlobal, categoryOperationsBinding.updatedCategory.getText().toString()));
                categoryOperationsBinding.updateCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateCategory(categoryGlobal, categoryOperationsBinding.updatedCategory.getText().toString());
                        categoryOperationsBinding.updatedCategory.setText(null);
                    }
                });
            }
        });
        //Set the adapter for the deleteAutoCompleteTextView
        categoryOperationsBinding.deleteAutoCompleteTextView.setAdapter(categoryAdapter);
        //Click listener for the deleteAutoCompleteTextView
        categoryOperationsBinding.deleteAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryGlobal = adapterView.getItemAtPosition(i).toString();
                categoryOperationsBinding.deleteCategoryButton.setOnClickListener(v -> deleteCategory(categoryGlobal));
            }
        });
    }

    private void deleteCategory(String category){
        if(!category.equals("Default")){
            restaurantsListViewModel.removeRestaurantCategory(category);
            restaurantDBHelper.deleteCategory(category);
        }
    }

    private void updateCategory(String oldCategory, String newCategory){
        restaurantsListViewModel.updateRestaurantCategory(oldCategory, newCategory);
        restaurantDBHelper.updateCategory(oldCategory, newCategory);
    }

    private void addCategory(String category) {
        restaurantsListViewModel.addRestaurantCategory(category);
    }
}