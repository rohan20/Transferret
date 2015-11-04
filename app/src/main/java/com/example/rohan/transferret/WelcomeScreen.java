package com.example.rohan.transferret;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Timer;

public class WelcomeScreen extends AppCompatActivity {

    Toolbar t;
    TextView textViewWelcomeTo;
    TextView textViewTransferret;
    CountDownTimer timer;
    Button buttonExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

//        t = (Toolbar)findViewById(R.id.toolbar);
//        setTitle("Transferret");

        buttonExplore = (Button)findViewById(R.id.bExplore);
        buttonExplore.setVisibility(View.INVISIBLE);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);

        timer = new CountDownTimer(2000, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish()
            {
                buttonExplore.setVisibility(View.VISIBLE);
                buttonExplore.setAnimation(in);
            }
        };

        timer.start();



        textViewWelcomeTo = (TextView)findViewById(R.id.tvWelcomeTo);
        textViewTransferret = (TextView)findViewById(R.id.tvTransferret);

        Typeface bernhc = Typeface.createFromAsset(getAssets(), "fonts/BERNHC.TTF");
//        Typeface Sitka = Typeface.createFromAsset(getAssets(), "fonts/Sitka.ttc");
        textViewWelcomeTo.setTypeface(bernhc);
        textViewTransferret.setTypeface(bernhc);

        Animation translation = AnimationUtils.loadAnimation(this, R.anim.translate);
        textViewWelcomeTo.startAnimation(translation);
        textViewTransferret.startAnimation(translation);

        buttonExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeScreen.this, Tabbed.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });



    }
}