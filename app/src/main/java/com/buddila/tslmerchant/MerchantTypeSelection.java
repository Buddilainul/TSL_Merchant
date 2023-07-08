package com.buddila.tslmerchant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MerchantTypeSelection extends AppCompatActivity {

    //Declaring variable
    LinearLayout cabSelection , hotelSelection;
    public static String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_type_selection);

        //Transparent status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Initialise variable
        cabSelection = findViewById(R.id.cabselection);
        hotelSelection = findViewById(R.id.hotelselection);

        cabSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = "cabServices";
                openMainScreen();
            }
        });

        hotelSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType = "hotelServices";
                openMainScreen();
            }
        });
    }
    public void openMainScreen(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}