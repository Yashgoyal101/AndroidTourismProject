
package com.example.login_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Splashscreen extends AppCompatActivity {
    private  static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    TextView logo, tagline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Animations
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom);

        //Hooks
        logo= findViewById(R.id.logo);
        tagline= findViewById(R.id.tag_line);

        logo.setAnimation(topAnim);
        tagline.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splashscreen.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }
}