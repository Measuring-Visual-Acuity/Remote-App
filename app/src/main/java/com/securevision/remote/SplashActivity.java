package com.securevision.remote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        button = findViewById(R.id.getStarted);

        animation = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        button.setAnimation(animation);



        //Glide.with(SplashActivity.this).load("http://random6.xyz/appEye.gif").into(imageView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setVisibility(View.VISIBLE);
                button.setClickable(true);
            }
        },1500);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Hello kartiik.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}