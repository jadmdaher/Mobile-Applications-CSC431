package com.example.refinedapplication.UI;

import static com.example.refinedapplication.MyApp.restaurantsListViewModel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.R;
import com.example.refinedapplication.databinding.ActivityAddBinding;
import com.example.refinedapplication.databinding.ItemViewBinding;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantListVh> {
    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantRecyclerViewAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public RestaurantListVh onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewBinding itemViewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RestaurantListVh(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(RestaurantListVh holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
//        boolean permissionToCall = context.checkSelfPermission("android.permission.CALL_PHONE") == PackageManager.PERMISSION_GRANTED;
        holder.itemViewBinding.rowName.setText(restaurant.getName());
        holder.itemViewBinding.addressEditText.setText(restaurant.getAddress());
        holder.itemViewBinding.phoneEditText.setText(restaurant.getPhone());
        holder.itemViewBinding.webEditText.setText(restaurant.getWeb());

        //Update service icons' colors based on their values
        if(restaurant.isOnTable()){;
            holder.tableIcon.setImageResource(R.drawable.ic_table_fg);
        }else{
            holder.tableIcon.setImageResource(R.drawable.ic_table_fg_disabled);
        }

        if(restaurant.isDelivery()){
            holder.dishIcon.setImageResource(R.drawable.ic_dish_foreground);
        }else{
            holder.dishIcon.setImageResource(R.drawable.ic_dish_foreground_disabled);
        }

        if(restaurant.isTakeAway()){
            holder.motorcycleIcon.setImageResource(R.drawable.ic_motorcycle_foreground);
        }else{
            holder.motorcycleIcon.setImageResource(R.drawable.ic_motorcycle_foreground_disabled);
        }

        holder.itemViewBinding.burgerButton.setOnClickListener(v->holder.itemViewBinding.myDrawerLayout.openDrawer(holder.itemViewBinding.navView));
//        if(permissionToCall) {
//            holder.itemViewBinding.callButton.setOnClickListener(v -> callPhone(holder, restaurant.getPhone()));
//        }

        holder.itemViewBinding.callButton.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Request the CALL_PHONE permission here, and handle the result in onRequestPermissionsResult
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 123);
            } else {
                // Permission is already granted, make the phone call
                callPhone(holder, restaurant.getPhone());
            }
        });

        holder.itemViewBinding.browseButton.setOnClickListener(v->browseWeb(holder, restaurant.getWeb()));
        holder.itemViewBinding.deleteButton.setOnClickListener(v->deleteRestaurant(holder, position));
        holder.itemViewBinding.updateButton.setOnClickListener(v->updateRestaurant(holder, position));

//        holder.itemViewBinding.callButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String phoneNumber = restaurant.getPhone();
//                Context context = holder.itemView.getContext();
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + phoneBook[position]));
//                context.startActivity(intent);
//            }
//        });
//
//        holder.itemViewBinding.browseButton.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String webAddress = restaurant.getWeb();
//                Context context = holder.itemView.getContext();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://" + webBook[position]));
//                context.startActivity(intent);
//            }
//        }));

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void callPhone(RestaurantListVh holder, String phoneNumber){
        //String phoneNumber = holder.itemViewBinding.phoneEditText.getText().toString();
        context = holder.itemView.getContext();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + holder.itemViewBinding.phoneEditText.getText().toString()));
        context.startActivity(intent);
    }

//    public void callPhone(String phoneNumber) {
//        // Create an intent to make a phone call
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" + phoneNumber));
//
//        // Start the phone call intent
//        context.startActivity(intent);
//    }

    public void browseWeb(RestaurantListVh holder, String webAddress){
        //String webAddress = holder.itemViewBinding.webEditText.getText().toString();
        context = holder.itemView.getContext();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + holder.itemViewBinding.webEditText.getText().toString()));
        context.startActivity(intent);
    }

//    public void browseWeb(String webAddress) {
//        // Create an intent to open a website in a browser
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + webAddress));
//
//        // Start the browser intent
//        context.startActivity(intent);
//    }

    public void deleteRestaurant(RestaurantListVh holder, int position){
        Context context = holder.itemView.getContext();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Alert!");
        alert.setMessage("Are you sure you want to delete this restaurant?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                restaurantsListViewModel.delete(position);
                RestaurantRecyclerViewAdapter.this.notifyItemRemoved(position);
                RestaurantRecyclerViewAdapter.this.notifyItemRangeChanged(position, restaurantsListViewModel.getRestaurantsList().size());
                dialogInterface.dismiss();
            }});
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                holder.itemViewBinding.myDrawerLayout.closeDrawer(GravityCompat.END);
                dialogInterface.dismiss();
            }});
        alert.show();
    }

    public void updateRestaurant(RestaurantListVh holder, int position) {
        holder.itemViewBinding.myDrawerLayout.closeDrawer(GravityCompat.END);
        Context context = holder.itemView.getContext();
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    class RestaurantListVh extends RecyclerView.ViewHolder {
        ItemViewBinding itemViewBinding;
        ImageView tableIcon;
        ImageView dishIcon;
        ImageView motorcycleIcon;

        RestaurantListVh(ItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemViewBinding = itemViewBinding;

            tableIcon = itemViewBinding.tableIcon;
            dishIcon = itemViewBinding.dishIcon;
            motorcycleIcon = itemViewBinding.motorcycleIcon;

//            itemViewBinding.callButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = itemView.getContext();
//                    Intent intent = new Intent(Intent.ACTION_DIAL);
//                    intent.setData(Uri.parse("tel:" + itemViewBinding.phoneEditText.getText().toString()));
//                    context.startActivity(intent);
//                }
//            });
//
//            itemViewBinding.browseButton.setOnClickListener((new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context = itemView.getContext();
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("https://" + itemViewBinding.webEditText.getText().toString()));
//                    context.startActivity(intent);
//                }
//            }));
        }
    }
}
