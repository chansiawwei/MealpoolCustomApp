/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener,View.OnKeyListener {

 boolean SignUpModeActive=true;
 Button signUpButton;
 TextView loginTextView;
    EditText passwordText;
    EditText usernameText;
    Intent intent;
    @Override
  public void onClick(View view) {
    if (view.getId()==R.id.loginTextView){
      if(SignUpModeActive){
          SignUpModeActive=false;
          signUpButton.setText("Login");
          loginTextView.setText("or, Sign Up");
      }
      else {
          SignUpModeActive=true;
          signUpButton.setText("Sign Up");
          loginTextView.setText("or, Login");
      }
    }else if(view.getId()==R.id.imageView || view.getId()==R.id.backgroundLayout){
        //Hide keyboard from user
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

      if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){

          signUpClicked(view);
      }
        return false;
    }

    public void signUpClicked(View view){


    if(usernameText.getText().toString().matches("")||passwordText.getText().toString().matches("")){
      Toast.makeText(this,"A username and a password are required. ",Toast.LENGTH_SHORT).show();

    }
    else {
        if (SignUpModeActive) {
            ParseUser user = new ParseUser();
            user.setUsername(usernameText.getText().toString());
            user.setPassword(passwordText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Sign Up", "Sucess");
                        intent.putExtra("Username",usernameText.getText().toString());
                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        else{

            ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null){
                        Log.i("Log In","Okay!");
                        intent.putExtra("Username",usernameText.getText().toString());
                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("Mealpool");
    loginTextView=(TextView) findViewById(R.id.loginTextView);
    loginTextView.setOnClickListener(this);

      usernameText=(EditText) findViewById(R.id.usernameTextView);
      passwordText=(EditText) findViewById(R.id.passwordTextView);
      ImageView imageView=(ImageView) findViewById(R.id.imageView);
      imageView.setAlpha(200);
      TextView logoTextView=(TextView) findViewById(R.id.logoTextView);

      RelativeLayout backgroundLayout=(RelativeLayout) findViewById(R.id.backgroundLayout);

      imageView.setOnClickListener(this);
      backgroundLayout.setOnClickListener(this);
      passwordText.setOnKeyListener(this);

      intent = new Intent(getApplicationContext(),BuyOrSellActivity.class);
      //check if already logged in

      if(ParseUser.getCurrentUser().getUsername()!=null){

          intent.putExtra("Username", ParseUser.getCurrentUser().getUsername());
          startActivity(intent);

          Toast.makeText(MainActivity.this, "User already Logged in.", Toast.LENGTH_SHORT).show();
      }

    signUpButton=(Button)findViewById(R.id.signupButton);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}