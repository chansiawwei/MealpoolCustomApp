package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ListFood extends AppCompatActivity {
    String currentUser;
    EditText foodNameEditText;
    EditText descriptionEditText;
    EditText priceEditText;
    EditText typeEditText;
    String deliveryMethod;
    CheckBox delivery;
    CheckBox localPickup;
    Bitmap bitmap;
    ParseFile file;
    String errMessage;
    ImageView imageview;
    Intent intentMainPage;
    Intent intentLoginPage;
    Intent intentMyProfile;
    int numberOfOrder=0;
    ArrayList<String> foodOrderByUsers;


    public  void getPhoto(){
        Intent intentToGallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentToGallery,1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1 ){
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    public void submitButtonClicked(View view){
        errMessage="";
        Log.i("Button","Submit button clicked");
        Log.i("Food Name",foodNameEditText.getText().toString());

        ParseObject object= new ParseObject("ListFood");
        if(foodNameEditText.getText().toString().equals("")) {
            errMessage+= "Food Name cannot be empty .\n";
        }

        if(descriptionEditText.getText().toString().equals("")) {
            errMessage+= "Description cannot be empty .\n";
        }

        if (typeEditText.getText().toString() .equals("") ) {
            errMessage+= "Food type cannot be empty .\n";
        }

        if (priceEditText.getText().toString() .equals("") ) {
            errMessage+= "Price cannot be empty .\n";
        }

        if(file==null) {
            errMessage+= "Food image cannot be empty .\n";

        }

        if(errMessage=="") {
            //make the arraylist to empyty list no user added to cart
         foodOrderByUsers=new ArrayList<String>();


            object.put("FoodName", foodNameEditText.getText().toString());
            object.put("Description", descriptionEditText.getText().toString());
            object.put("Price",priceEditText.getText().toString());
            object.put("Type",typeEditText.getText().toString());
            object.put("DeliveryMethod",deliveryMethod);
            object.put("NumberOfOrder",numberOfOrder);
            object.put("User", currentUser);
            object.put("Image", file);
            object.put("FoodOrderByUsers", foodOrderByUsers);



            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null) {
                        Log.i("Parse Result", "Successful!");

                    } else {
                        Log.i("Parse Result", "Failed " + ex.toString());
                    }
                }
            });

            foodNameEditText.getText().clear();
            descriptionEditText.getText().clear();
            typeEditText.getText().clear();
            priceEditText.getText().clear();

            imageview.setImageDrawable(null);
            Toast.makeText(ListFood.this, "Successfully listed food!", Toast.LENGTH_LONG).show();
            startActivity(intentMainPage);

        }
        else{
            Log.i("Form ERROR",errMessage);
            Toast.makeText(ListFood.this, errMessage, Toast.LENGTH_LONG).show();
        }
    }


    public void uploadButtonClicked(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else {
                getPhoto();
            }
        }


    }



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_delivery:
                if (checked) {

                   deliveryMethod="Delivery";
                }
            else{
                    deliveryMethod="";

                }

            case R.id.checkbox_localpickup:
                if (checked){
                    deliveryMethod="LocalPickup";

                }

            else{
                    deliveryMethod="";
                }

                if(localPickup.isChecked() && delivery.isChecked()){
                    deliveryMethod="Delivery,LocalPickup";
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage=data.getData();

    if(requestCode==1 && resultCode==RESULT_OK && data !=null){
        try {
             bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
            imageview= (ImageView)findViewById(R.id.foodImage);
            imageview.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] byteArray=stream.toByteArray();
             file=new ParseFile("image.png", byteArray);




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        setTitle("List Food Page");

        foodNameEditText=(EditText)findViewById(R.id.foodNameEditText);
        descriptionEditText=(EditText)findViewById(R.id.descriptionEditText);
        typeEditText=(EditText)findViewById(R.id.typeEditText);
        priceEditText=(EditText)findViewById(R.id.priceEditText);
        delivery=(CheckBox)findViewById(R.id.checkbox_delivery) ;
        localPickup=(CheckBox)findViewById(R.id.checkbox_localpickup) ;
        intentMainPage = new Intent(getApplicationContext(),BuyOrSellActivity.class);
        intentLoginPage = new Intent(getApplicationContext(),MainActivity.class);
        intentMyProfile=new Intent(getApplicationContext(),MyProfile.class);


        currentUser = getIntent().getStringExtra("Username");
       Log.i("Current User List Page",currentUser);





        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
