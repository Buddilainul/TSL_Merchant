package com.buddila.tslmerchant;

import static com.buddila.tslmerchant.MerchantTypeSelection.userType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    View MainLayout;
    ImageButton backBtn;
    Button loginButton , registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Transparent status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        MainLayout = findViewById(R.id.mainLayout);
        backBtn = findViewById(R.id.backbutton);
        loginButton = (Button) findViewById(R.id.login_button_main);
        registerButton = (Button) findViewById(R.id.register_button_main);

        //Background color
        if (userType.equals("cabServices")){
            MainLayout.setBackgroundColor(Color.parseColor("#A7DF85"));
        }else if (userType.equals("hotelServices")){
            MainLayout.setBackgroundColor(Color.parseColor("#99E5CF"));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogInScreen();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.equals("cabServices")){
                    openCabRegisterScreen();
                }else if (userType.equals("hotelServices")){
                    openHotelRegisterScreen();
                }
            }
        });

        //Back Button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    public void openLogInScreen(){
        Intent intent = new Intent (this, LoginScreen.class);
        startActivity(intent);
    }
    public void openCabRegisterScreen(){
        Intent intent = new Intent (this, CabRegisterScreen.class);
        startActivity(intent);
    }
    public void openHotelRegisterScreen(){
        Intent intent = new Intent (this, HotelRegisterScreen.class);
        startActivity(intent);
    }
}