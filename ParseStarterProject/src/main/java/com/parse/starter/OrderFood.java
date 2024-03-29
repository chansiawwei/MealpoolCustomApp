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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class OrderFood extends AppCompatActivity {

    private ListView listView;
    private FoodListAdapter foodListAdapter;
    ArrayList<Food> foodList;
    Bitmap bitmap=null;
    TextView foodNameTextView;
    String foodObjectId;
    Intent intentLoginPage;
    Intent intentMyProfile;
    //Define Return Array here
    //---------------------------------

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
        setContentView(R.layout.activity_order_food);
        setTitle("Order Food Page");

        intentLoginPage = new Intent(getApplicationContext(),MainActivity.class);
        intentMyProfile=new Intent(getApplicationContext(),MyProfile.class);
        listView=(ListView) findViewById(R.id.orderFoodListView);
         foodList=new ArrayList<>();
         //ArrayList<String> foodListWithoutImage= new ArrayList<String>();

        foodListAdapter=new FoodListAdapter(this,foodList);



        //Execute Parse Server Query Here
        //--------------------------------------------------
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ListFood");
        query.whereNotEqualTo("User",ParseUser.getCurrentUser().getUsername().toString());
        Log.i("CUrrent user",ParseUser.getCurrentUser().getUsername().toString());
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null && objects.size() > 0){

                    for(ParseObject object :objects){

                  final String foodName=object.getString("FoodName");
                   final String foodDesc=object.getString("Description");
                   final String foodType=object.getString("Type");
                   final String foodPrice=object.getString("Price");

                     foodObjectId=object.getString("objectId");

                        ParseFile imageFile=(ParseFile) object.get("Image");

                        imageFile.getDataInBackground(new GetDataCallback() {

                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null &&data!=null ){
                           bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
                           //bitmap not null here but when reach add lsit, bitmap becomes nul
                                    //text value is set to the last object in loop
                                    foodList.add(new Food(bitmap, foodName , foodDesc));




                                    foodListAdapter.notifyDataSetChanged();
                                }

                            }
                        });


                    }

                    listView.setAdapter(foodListAdapter);
                }
                else {e.printStackTrace();}


            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ViewFoodDetails.class );

                intent.putExtra("foodName",  foodList.get(i).getFoodName());
                startActivity(intent);

            }
        });



  //      foodList.add(new Food(R.drawable.one, "After Earth" , "2011"));
//        foodList.add(new Food(R.drawable.two, "After Earth 2" , "2012"));
//        foodList.add(new Food(R.drawable.three, "After Earth 3" , "2013"));




    }
}
