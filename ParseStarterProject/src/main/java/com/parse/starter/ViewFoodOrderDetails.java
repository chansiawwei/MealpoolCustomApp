package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ViewFoodOrderDetails extends AppCompatActivity {
    Intent intentLoginPage;
    Intent intentMyProfile;
    String foodName;
    ListView listview;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> newlist;
    ArrayList<String>  a;
    ArrayList<String> list;

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
        setContentView(R.layout.activity_view_food_order_details);
        setTitle("Order Details");

        intentLoginPage = new Intent(getApplicationContext(),MainActivity.class);
        intentMyProfile=new Intent(getApplicationContext(),MyProfile.class);
        listview=(ListView)findViewById(R.id.foodOrderListView);
        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodName");
        list = new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);


        Toast.makeText(this,foodName,Toast.LENGTH_SHORT).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ListFood");
        query.whereEqualTo("FoodName",foodName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null && objects.size() > 0){

                    for(ParseObject object :objects){

                        final JSONArray jsonArray=object.getJSONArray("FoodOrderByUsers");

                //"["lucas"]"

                        if (jsonArray != null) {
                            int len = jsonArray.length();
                            for (int i=0;i<len;i++){
                                try {
                                    list.add(jsonArray.get(i).toString());
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

//                         newlist = new ArrayList<String>();
//
//                        for (int i=0;i<list.size();i++){
//                          newlist.add(list.get(i).replaceAll("\\\\[|\\\\]^\"|\"$]",""));
//
//                        }


                    }


                    arrayAdapter.notifyDataSetChanged();

                    listview.setAdapter(arrayAdapter);


                }
                else {e.printStackTrace();}

            }
        });




    }
}
