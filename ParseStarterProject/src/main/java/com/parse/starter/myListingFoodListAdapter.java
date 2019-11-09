package com.parse.starter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class myListingFoodListAdapter extends ArrayAdapter<myListingFoodModel> {

    private Context fcontext;
    private List<myListingFoodModel> myListingFoodList=new ArrayList<>();


    public myListingFoodListAdapter(@NonNull Context context, ArrayList<myListingFoodModel> list) {
        super(context,0, list);
        fcontext=context;
        myListingFoodList=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem=convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(fcontext).inflate(R.layout.mylistinglayout,parent,false);

        myListingFoodModel currentFood = myListingFoodList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.foodImageView);
        image.setImageBitmap(currentFood.getFoodImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.foodNameTextView);
        name.setText(currentFood.getFoodName());

        TextView description = (TextView) listItem.findViewById(R.id.foodDescriptionTextView);
        description.setText(currentFood.getFoodDescription());

        TextView numberOfOrder=(TextView) listItem.findViewById(R.id.numberOfOrderTextView);

        numberOfOrder.setText("Number Of Order :"+ String.valueOf(currentFood.getNumberOfOrder()));


        return listItem;


    }
}
