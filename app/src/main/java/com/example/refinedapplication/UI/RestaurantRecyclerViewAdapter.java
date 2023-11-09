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
import android.view.ViewGroup;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.Model.RestaurantDBHelper;
import com.example.refinedapplication.MyApp;
import com.example.refinedapplication.R;
import com.example.refinedapplication.databinding.ItemViewBinding;
import android.content.Context;
import android.widget.ImageView;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantListVh> {
    private List<Restaurant> restaurantList;
    private RestaurantDBHelper restaurantDBHelper;
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
        restaurantsListViewModel.getRestaurantDBHelper();
        restaurantDBHelper = ((MyApp)context.getApplicationContext()).getRestaurantDBHelper();
        holder.itemViewBinding.rowName.setText(restaurant.getName());
        holder.itemViewBinding.addressEditText.setText(restaurant.getAddress());
        holder.itemViewBinding.phoneEditText.setText(restaurant.getPhone());
        holder.itemViewBinding.webEditText.setText(restaurant.getWeb());
        setRating(holder, restaurant.getRating());
        holder.itemViewBinding.categoryEditText.setText(restaurant.getCategory());

        //Update service icons' colors based on their values
        if(restaurant.isOnTable()){;
            holder.tableIcon.setImageResource(R.drawable.table);
        }else{
            holder.tableIcon.setImageResource(R.drawable.table_disabled);
        }

        if(restaurant.isDelivery()){
            holder.dishIcon.setImageResource(R.drawable.delivery_icon);
        }else{
            holder.dishIcon.setImageResource(R.drawable.delivery_icon_disabled);
        }

        if(restaurant.isTakeAway()){
            holder.motorcycleIcon.setImageResource(R.drawable.takeaway_icon);
        }else{
            holder.motorcycleIcon.setImageResource(R.drawable.takeaway_icon_disabled);
        }

        holder.itemViewBinding.burgerButton.setOnClickListener(v->holder.itemViewBinding.myDrawerLayout.openDrawer(holder.itemViewBinding.navView));

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
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void callPhone(RestaurantListVh holder, String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    public void browseWeb(RestaurantListVh holder, String webAddress){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + webAddress));
        context.startActivity(intent);
    }

    public void deleteRestaurant(RestaurantListVh holder, int position){
        Context context = holder.itemView.getContext();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Alert!");
        alert.setMessage("Are you sure you want to delete this restaurant?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                restaurantDBHelper.deleteRestaurant(restaurantList.get(position));
                restaurantsListViewModel.delete(position);
//                restaurantList.remove(position);
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

    public void setRating(RestaurantListVh holder, int rating){
        ImageView starIcon1 = holder.itemViewBinding.star1;
        ImageView starIcon2 = holder.itemViewBinding.star2;
        ImageView starIcon3 = holder.itemViewBinding.star3;
        ImageView starIcon4 = holder.itemViewBinding.star4;
        ImageView starIcon5 = holder.itemViewBinding.star5;

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
        }
    }
}
