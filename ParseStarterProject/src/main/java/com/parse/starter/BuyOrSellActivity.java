package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class BuyOrSellActivity extends AppCompatActivity {
    Intent intentListFood;
    Intent intentOrderFood;
  Intent intentLoginPage;
    String currentUser;
    public void listFoodClicked(View view)
    {
        Toast.makeText(BuyOrSellActivity.this, "List Button clicked", Toast.LENGTH_SHORT).show();
        intentListFood.putExtra("Username", currentUser);
        startActivity(intentListFood);

    }
    public void orderFoodClicked(View view ){
       // Toast.makeText(BuyOrSellActivity.this, "Order Button clicked", Toast.LENGTH_SHORT).show();
        intentOrderFood.putExtra("Username", currentUser);
        startActivity(intentOrderFood);




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


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_or_sell);
        setTitle("Home Page");

        intentListFood = new Intent(getApplicationContext(),ListFood.class);
        intentOrderFood = new Intent(getApplicationContext(),OrderFood.class);
      intentLoginPage = new Intent(getApplicationContext(),MainActivity.class);

       currentUser = getIntent().getStringExtra("Username");
        Log.i("Current User",currentUser);




    }
}
