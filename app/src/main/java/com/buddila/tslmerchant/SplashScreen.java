package com.buddila.tslmerchant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    //Variables
    Animation topanim;
    ImageView logoimage , lImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        lImage = findViewById(R.id.cover_splash);
        lImage.setAnimation(AnimationUtils.loadAnimation(this, R.anim.logoanim));

        //Animations
        topanim = AnimationUtils.loadAnimation(this,R.anim.top_anim);

        //Hooks
        logoimage = findViewById(R.id.logo_splash);

        logoimage.setAnimation(topanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this , MerchantTypeSelection.class);
                startActivity(intent);
            }
        },3000);

    }
}