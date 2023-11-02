package com.example.refinedapplication.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.refinedapplication.Model.RestaurantsListViewModel;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.R;
import com.example.refinedapplication.UI.RestaurantRecyclerViewAdapter;
import com.example.refinedapplication.databinding.ActivityViewBinding;
import com.example.refinedapplication.databinding.ItemViewBinding;

public class ViewActivity extends AppCompatActivity {
    RestaurantsListViewModel restaurantsListViewModel;
    private DrawerLayout drawerLayout;
    private ImageView buttonImageView;
    private ActivityViewBinding viewBinding;
    private RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    private ImageView callButton;

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
        //Drawer functionality
//        drawerLayout = findViewById(R.id.myDrawerLayout);
//        buttonImageView = findViewById(R.id.burgerButton);
//
//        buttonImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(drawerLayout.isDrawerOpen(findViewById(R.id.navView))){
//                    drawerLayout.closeDrawer(findViewById(R.id.navView));
//                }else{
//                    drawerLayout.openDrawer(findViewById(R.id.navView));
//                }
//            }
//        });

    }

    //(Start) Activity Lifecycle Methods:
    @Override
    protected void onResume() {
        super.onResume();
        restaurantRecyclerViewAdapter.notifyDataSetChanged();
    }
    //(End) Activity Lifecycle Methods.

//    public void callPhone(View view){
//        callButton = findViewById(R.layout.item_view.);
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" ));
//        startActivity(intent);
//    }

//    //(Start) Menu related methods:
//    //The onCreate method for the main_menu.xml file
//    @Override
//    public boolean onCreateOptionsMenu (Menu menu){
//        getMenuInflater().inflate(R.menu.drawer,menu);
//        return true;
//    }
//
//    /*
//     * Method used to specify which view
//     * the user see when a certain item is
//     * selected from the menu items
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if(item.getItemId() == R.id.edit){
//        }
//
//        if(item.getItemId() == R.id.delete){
//        }
//
//        return true;
//    }
//    //(End) Menu related methods.
}