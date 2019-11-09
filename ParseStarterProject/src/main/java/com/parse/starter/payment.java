package com.parse.starter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.StripeActivity;

import java.util.HashMap;

public class payment extends AppCompatActivity {
    public static final String PUBLISHABLE_KEY = "pk_test_ODKIDARthymDJIExYgqImpoU001ZgIcJbo";
    private Card card;
    private ProgressDialog progress;
    private Button purchase;
    CardInputWidget cardInputWidget;
    StripeActivity mErrorDialogHandler;
    Stripe stripe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Create a demo test credit Card
        // You can pass the payment form data to create a Real Credit card
        // But you need to implement youself.

//        Card card = new Card(
//                "4242424242424242", //card number
//                12, //expMonth
//                2030,//expYear
//                "123"//cvc
//        );
        cardInputWidget=(CardInputWidget)findViewById(R.id.card_input_widget99);
    stripe=new Stripe(getApplicationContext(),PUBLISHABLE_KEY);


        progress = new ProgressDialog(this);
        purchase = (Button) findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy();
            }
        });

    }
    private void buy(){
        final Card cardToSave = cardInputWidget.getCard();
        if (cardToSave == null) {

            //mErrorDialogHandler.showError("Invalid Card Data");
            Toast.makeText(payment.this, "Invalid Card Data", Toast.LENGTH_LONG).show();
            return;
        }
        if (cardToSave!=null) {
            boolean validation = cardToSave.validateCard();
            if (validation) {
                startProgress("Validating Credit Card");
                // new Stripe(this).createToken(
                stripe.createToken(
                        cardToSave,

                        //new TokenCallback() {
                        new ApiResultCallback<Token>() {
                            @Override
                            public void onError(Exception error) {
                                Toast.makeText(payment.this,
                                        "Stripe -" + error.toString(),
                                        Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(Token token) {
                                finishProgress();
                                charge(token);
                            }
                        }

                );
            } else if (!cardToSave.validateNumber()) {
                Toast.makeText(payment.this,
                        "Stripe - The card number that you entered is invalid",
                        Toast.LENGTH_LONG).show();
            } else if (!cardToSave.validateExpiryDate()) {
                Toast.makeText(payment.this,
                        "Stripe - The expiration date that you entered is invalid",
                        Toast.LENGTH_LONG).show();
            } else if (!cardToSave.validateCVC()) {
                Toast.makeText(payment.this,
                        "Stripe - The CVC code that you entered is invalid",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(payment.this,
                        "Stripe - The card details that you entered are invalid",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    private void charge(Token cardToken){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ItemName", "test");
        params.put("cardToken", cardToken.getId());
        params.put("name","Dominic Wong");
        params.put("email","dominwong4@gmail.com");
        params.put("address","HIHI");
        params.put("zip","99999");
        params.put("city_state","CA");
        startProgress("Purchasing Item");

        ParseCloud.callFunctionInBackground("purchaseItem", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response,  com.parse.ParseException e) {
                finishProgress();
                if (e == null) {
                    Log.d("Cloud Response", "There were no exceptions! " + response.toString());
                    Toast.makeText(getApplicationContext(),
                            "Item Purchased Successfully ",
                            Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Cloud Response", "Exception: " + e);
                    Toast.makeText(getApplicationContext(),
                            e.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void startProgress(String title){
        progress.setTitle(title);
        progress.setMessage("Please Wait");
        progress.show();
    }
    private void finishProgress(){
        progress.dismiss();
    }
}
