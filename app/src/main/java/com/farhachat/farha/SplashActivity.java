package com.farhachat.farha;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity 
{
FirebaseUser firebaseUser;
ImageView imageView;
AnimationDrawable anim;
TextView farha;

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(firebaseUser != null)
                {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));

                }
                else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
                finish();
            }

        },2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.anim_img);
        if(imageView == null) throw new AssertionError();
        imageView.setBackgroundResource(R.drawable.animation_loading);
        anim = (AnimationDrawable) imageView.getBackground();
        anim.start();
        farha = findViewById(R.id.farha);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "Paint font.ttf");
        farha.setTypeface(custom_font);

    }
}
