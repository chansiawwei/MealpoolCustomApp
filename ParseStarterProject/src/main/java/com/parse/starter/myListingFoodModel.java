package com.parse.starter;

import android.graphics.Bitmap;

public class myListingFoodModel {

    // Store the id of the food image
    private Bitmap foodImageDrawable;
    // Store the name of the food
    private String foodName;
    // Store the description of food
    private String foodDescription;

    //Store the number of Order of food
    private int numberOfOrder;

    // Constructor that is used to create an instance of the Movie object
    public myListingFoodModel(Bitmap fImageDrawable, String fName, String fDescription,int fNumberOfOrder) {
        this.foodImageDrawable = fImageDrawable;
        this.foodName = fName;
        this.foodDescription = fDescription;
        this.numberOfOrder=fNumberOfOrder;
    }

    public Bitmap getFoodImageDrawable() {
        return foodImageDrawable;
    }

    public void setFoodImageDrawable(Bitmap foodImageDrawable) {
        this.foodImageDrawable = foodImageDrawable;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

}
