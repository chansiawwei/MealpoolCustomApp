package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewFoodDetails extends AppCompatActivity {
    String foodName;
    String foodDesc;
    String foodType;
    String foodPrice;
    Bitmap bitmap;
    ImageView foodDetailImageView;
    TextView foodNameTextView;
    TextView foodDescTextView;
    TextView foodTypeTextView;
    TextView foodPriceTextView;
    Intent intentLoginPage;
    Intent intentMyProfile;
    Intent intentPaymentPage;

    int numberOfOrder;
    ArrayList<String> foodOrderByUsers;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.logout){
            //logout user
            ParseUser.logOut();
            startActivity(intentLoginPage);

        }
        else if(item.getItemId()==R.id.myProfile){
            startActivity(intentMyProfile);
        }


        return super.onOptionsItemSelected(item);
    }
    //  Add quanitty field to allow user to select quantity
    //

    public void checkoutButtonClicked(View view){
        Toast.makeText(ViewFoodDetails.this, "CHECKOUT BUTTON CLICKED", Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ListFood");
        query.whereEqualTo("FoodName",foodName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size() > 0){
                    for(ParseObject object :objects){
                        numberOfOrder= object.getInt("NumberOfOrder");
                        numberOfOrder+=1;

                        object.add("FoodOrderByUsers",Arrays.asList(ParseUser.getCurrentUser().getUsername().toString()));

                        object.put("NumberOfOrder",numberOfOrder);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException ex) {
                                if (ex == null) {
                                    Log.i("Parse Result", "Successful!");
                                    startActivity(intentPaymentPage);

                                } else {
                                    Log.i("Parse Result", "Failed " + ex.toString());


                                }
                            }
                        });
                    }

                }
                else {e.printStackTrace();}
            }
        });
        numberOfOrder+=1;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_details);
        setTitle("Food Details");

        foodDetailImageView=(ImageView) findViewById(R.id.foodDetailImageView);
        foodNameTextView=(TextView)findViewById(R.id.foodDetailNameTextView);
        foodDescTextView=(TextView)findViewById(R.id.foodDetailDescTextView);
        foodTypeTextView=(TextView)findViewById(R.id.foodDetailTypeTextView);
        foodPriceTextView=(TextView)findViewById(R.id.foodDetailPriceTextView);


        intentLoginPage = new Intent(getApplicationContext(),MainActivity.class);
        intentMyProfile=new Intent(getApplicationContext(),MyProfile.class);
        intentPaymentPage=new Intent(getApplicationContext(),payment.class);
        //Get the foodName that user clicked
        Intent intent = getIntent();
         foodName = intent.getStringExtra("foodName");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("ListFood");
        query.whereEqualTo("FoodName",foodName);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null && objects.size() > 0){

                    for(ParseObject object :objects){

                        final String foodDesc=object.getString("Description");
                        final String foodType=object.getString("Type");
                        final String foodPrice=object.getString("Price");
                        final String sellerUsername=object.getString("User");


                        foodNameTextView.setText(foodName.toString());
                        foodDescTextView.setText(foodDesc.toString());
                        foodTypeTextView.setText(foodType.toString());
                        foodPriceTextView.setText(foodPrice.toString());



                        ParseFile file = (ParseFile) object.get("Image");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null &&data!=null ){
                                    bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
                                foodDetailImageView.setImageBitmap(bitmap);

                                }
                            }
                        });
                    }


                }
                else {e.printStackTrace();}
            }
        });





        // foodNameTextView.setText(foodName.toString());
        //foodDescTextView.setText(foodDesc.toString());
        //foodPriceTextView.setText(foodPrice.toString());
        // foodTypeTextView.setText(foodType.toString());
    }


}
