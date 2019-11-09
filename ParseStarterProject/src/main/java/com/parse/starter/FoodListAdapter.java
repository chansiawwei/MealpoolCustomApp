package com.parse.starter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {

    private Context fcontext;
    private List<Food> foodList=new ArrayList<>();


    public FoodListAdapter(@NonNull Context context,  ArrayList<Food> list) {
        super(context,0, list);
        fcontext=context;
        foodList=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem=convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(fcontext).inflate(R.layout.layoutlist,parent,false);

        Food currentFood = foodList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.foodImageView);
        image.setImageBitmap(currentFood.getFoodImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.foodNameTextView);
        name.setText(currentFood.getFoodName());

        TextView foodDesc = (TextView) listItem.findViewById(R.id.foodDescriptionTextView);
        foodDesc.setText(currentFood.getFoodDescription());

        return listItem;


    }
}