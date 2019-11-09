package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class MyProfile extends AppCompatActivity {
    String currentUser;
    Intent intentLoginPage;
    Intent intentMyProfile;
    TextView textView2;
    Intent intentMyListing;
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

        else if(item.getItemId()==R.id.myListing){
            startActivity(intentMyListing);
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        currentUser=ParseUser.getCurrentUser().getUsername().toString();
         intentLoginPage = new Intent(getApplicationContext(), MainActivity.class);
        intentMyProfile=new Intent(getApplicationContext(),MyProfile.class);
        textView2=(TextView)findViewById(R.id.textView2);
        textView2.setText("Username: " + currentUser);
        intentMyListing=new Intent(getApplicationContext(),MyListing.class);
        ImageView img= (ImageView) findViewById(R.id.imageView2);
        img.setImageResource(R.drawable.download);



    }
}
