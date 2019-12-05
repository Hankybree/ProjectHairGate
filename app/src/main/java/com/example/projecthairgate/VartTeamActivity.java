package com.example.projecthairgate;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class VartTeamActivity extends AppCompatActivity {

    ImageView imageLars;
    ImageView imageLinn;
    ImageView imageEmelie;
    ImageView imageIsabelle;
    ImageView imageJessica;
    ImageView imageLinnea;
    ImageView imageLouise;
    ImageView imageOliver;
    ImageView imageCecilia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vartteam);

        getImageViewIDs();

        imageLars.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageLars.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageLars);
                    imageLars.setImageResource(R.drawable.larskort);
                    imageLars.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageLars);
                    imageLars.setImageResource(R.drawable.lars);
                    imageLars.setTag("bild");
                }

            }
        });
        imageLinn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageLinn.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageLinn);
                    imageLinn.setImageResource(R.drawable.linnkort4);
                    imageLinn.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageLinn);
                    imageLinn.setImageResource(R.drawable.linn);
                    imageLinn.setTag("bild");
                }

            }
        });

        imageEmelie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageEmelie.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageEmelie);
                    imageEmelie.setImageResource(R.drawable.emeliekort);
                    imageEmelie.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageEmelie);
                    imageEmelie.setImageResource(R.drawable.emelie);
                    imageEmelie.setTag("bild");
                }

            }
        });

        imageIsabelle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageIsabelle.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageIsabelle);
                    imageIsabelle.setImageResource(R.drawable.isabelekort);
                    imageIsabelle.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageIsabelle);
                    imageIsabelle.setImageResource(R.drawable.isabelle);
                    imageIsabelle.setTag("bild");
                }

            }
        });

        imageJessica.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageJessica.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageJessica);
                    imageJessica.setImageResource(R.drawable.jessicakort);
                    imageJessica.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageJessica);
                    imageJessica.setImageResource(R.drawable.jessica);
                    imageJessica.setTag("bild");
                }

            }
        });

        imageLinnea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageLinnea.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageLinnea);
                    imageLinnea.setImageResource(R.drawable.linneakort);
                    imageLinnea.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageLinnea);
                    imageLinnea.setImageResource(R.drawable.linnea);
                    imageLinnea.setTag("bild");
                }

            }
        });

        imageLouise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageLouise.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageLouise);
                    imageLouise.setImageResource(R.drawable.louisekort);
                    imageLouise.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageLouise);
                    imageLouise.setImageResource(R.drawable.louise);
                    imageLouise.setTag("bild");
                }

            }
        });

        imageOliver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageOliver.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageOliver);
                    imageOliver.setImageResource(R.drawable.oliverkort);
                    imageOliver.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageOliver);
                    imageOliver.setImageResource(R.drawable.oliver);
                    imageOliver.setTag("bild");
                }

            }
        });

        imageCecilia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (imageCecilia.getTag().equals("bild")) {
                    YoYo.with(Techniques.FlipInX).duration(1000).playOn(imageCecilia);
                    imageCecilia.setImageResource(R.drawable.ceciliakort);
                    imageCecilia.setTag("text");
                } else {
                    YoYo.with(Techniques.FlipInY).duration(1000).playOn(imageCecilia);
                    imageCecilia.setImageResource(R.drawable.cecilia);
                    imageCecilia.setTag("bild");
                }

            }
        });
    }

    private void getImageViewIDs() {
        imageLars = findViewById(R.id.lars);
        imageLars.setTag("bild");
        imageLinn = findViewById(R.id.linn);
        imageLinn.setTag("bild");
        imageEmelie = findViewById(R.id.emelie);
        imageEmelie.setTag("bild");
        imageIsabelle = findViewById(R.id.isabelle);
        imageIsabelle.setTag("bild");
        imageJessica = findViewById(R.id.jessica);
        imageJessica.setTag("bild");
        imageLinnea = findViewById(R.id.linnea);
        imageLinnea.setTag("bild");
        imageLouise = findViewById(R.id.louise);
        imageLouise.setTag("bild");
        imageOliver = findViewById(R.id.oliver);
        imageOliver.setTag("bild");
        imageCecilia = findViewById(R.id.cecilia);
        imageCecilia.setTag("bild");
    }
}

