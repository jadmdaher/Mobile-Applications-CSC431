package com.example.refinedapplication.UI;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refinedapplication.Model.Restaurant;
import com.example.refinedapplication.databinding.ItemViewBinding;
import android.content.Context;
import android.graphics.Color;
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
        holder.itemViewBinding.rowName.setText(restaurant.getName());
        holder.itemViewBinding.addressEditText.setText(restaurant.getAddress());
        holder.itemViewBinding.phoneEditText.setText(restaurant.getPhone());
        holder.itemViewBinding.webEditText.setText(restaurant.getWeb());

        // Update service icons' colors based on their values
        int onTableColor = restaurant.isOnTable() ? Color.DKGRAY : Color.LTGRAY;
        int deliveryColor = restaurant.isDelivery() ? Color.DKGRAY : Color.LTGRAY;
        int takeawayColor = restaurant.isTakeAway() ? Color.DKGRAY : Color.LTGRAY;

        holder.itemViewBinding.tableIcon.setColorFilter(onTableColor);
        holder.itemViewBinding.dishIcon.setColorFilter(deliveryColor);
        holder.itemViewBinding.motorcycleIcon.setColorFilter(takeawayColor);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class RestaurantListVh extends RecyclerView.ViewHolder {
        ItemViewBinding itemViewBinding;

        RestaurantListVh(ItemViewBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            this.itemViewBinding = itemViewBinding;
        }
    }
}
