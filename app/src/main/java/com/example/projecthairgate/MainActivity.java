package com.example.projecthairgate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try {
           setContentView(R.layout.activity_main);
       }catch (Exception e){
           Log.d("debugger", e.getMessage());
       }


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent startMenuIntent = new Intent(MainActivity.this, StartMenuActivity.class);
                startActivity(startMenuIntent);
                finish();
            }
        },SPLASH_TIME_OUT);




    }
}
