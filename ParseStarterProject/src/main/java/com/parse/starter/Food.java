package com.parse.starter;

import android.graphics.Bitmap;

public class Food {

    // Store the id of the food image
    private Bitmap foodImageDrawable;
    // Store the name of the food
    private String foodName;
    // Store the description of food
    private String foodDescription;

    // Constructor that is used to create an instance of the Movie object
    public Food(Bitmap mImageDrawable, String mName, String mRelease) {
        this.foodImageDrawable = mImageDrawable;
        this.foodName = mName;
        this.foodDescription = mRelease;
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

}


