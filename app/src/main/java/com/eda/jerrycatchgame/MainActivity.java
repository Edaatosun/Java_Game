package com.eda.jerrycatchgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView time;
    TextView score;
    int scoreNumber;
    SharedPreferences sharedPreferences;
    Runnable runnable;
    Handler handler;
    ImageView[] imageArray = new ImageView[9];
    int[] imageIds = {R.id.imageView, R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,R.id.imageView5,R.id.imageView6, R.id.imageView7,R.id.imageView8};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = findViewById(R.id.textView);
        score = findViewById(R.id.textView2);
        scoreNumber=0;
        sharedPreferences = this.getSharedPreferences("com.eda.jerrycatchgame",MODE_PRIVATE);
        for(int i= 0;i<9;i++){
            imageArray[i]= findViewById(imageIds[i]);
        }

        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
              time.setText("Time:"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                handler.removeCallbacks(runnable);
                sharedPreferences.edit().putInt("storedScore",scoreNumber).apply();

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                int storedScore = sharedPreferences.getInt("storedScore",0);
                alert.setMessage("Mevcut Score: "+storedScore+"  Restart?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.show();

            }
        }.start();

        hideImages();


    }

    public void increaseScore(View view){
        scoreNumber++;
        score.setText("Score:"+scoreNumber);


    }

    public void hideImages(){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image:imageArray){
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this,500);
            }
        };

        handler.post(runnable);


    }


}
