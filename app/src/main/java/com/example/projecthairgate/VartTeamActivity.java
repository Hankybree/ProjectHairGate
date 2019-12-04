package com.example.projecthairgate;

import android.content.Context;
import android.media.ImageReader;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class VartTeamActivity extends AppCompatActivity {

    ImageView imageButtonLars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vartteam);
        changePicture();
    }

    public void changePicture()   {


        imageButtonLars = findViewById(R.id.lars);
        imageButtonLars.setTag("bild");
        imageButtonLars.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!"+imageButtonLars.getTag();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                if(imageButtonLars.getTag().equals("bild")) {
                    YoYo.with(Techniques.RollIn).duration(1000).playOn(imageButtonLars);
                    imageButtonLars.setImageResource(R.drawable.linn);
                    imageButtonLars.setTag("text");
                } else {
                    YoYo.with(Techniques.RollIn).duration(1000).playOn(imageButtonLars);
                    imageButtonLars.setImageResource(R.drawable.lars);
                    imageButtonLars.setTag("bild");
                }

            }
        });
    }
    }

