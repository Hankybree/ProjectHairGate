package com.example.projecthairgate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;


public class GalleryActivity extends AppCompatActivity {

    private FirebaseDatabase mRoot = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = mRoot.getReference();
    private String Image;

    /*ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    ImageView image7;
    ImageView image8;
    ImageView image9;
    ImageView image10;

     */

    // Här börjar oleg

    private RecyclerView staggeredRv;
    private GalleryGridAdapter adapter;
    private StaggeredGridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        staggeredRv = findViewById(R.id.staggerd_rv);
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredRv.setLayoutManager(manager);


        // arraylist av bilder här ska bilder från databasen läggas in
        List<GalleryRows> images = new ArrayList<>();
        images.add(new GalleryRows(R.drawable.bearded_man));
        images.add(new GalleryRows(R.drawable.mainview_behandlingar));
        images.add(new GalleryRows(R.drawable.mainview_team));
        images.add(new GalleryRows(R.drawable.bearded_man));
        images.add(new GalleryRows(R.drawable.mainview_kamera));
        images.add(new GalleryRows(R.drawable.bearded_man));
        

        adapter = new GalleryGridAdapter(this,images);
        staggeredRv.setAdapter(adapter);

        /*
        image1 = findViewById(R.id.image_1);
        image2 = findViewById(R.id.image_2);
        image3 = findViewById(R.id.image_3);
        image4 = findViewById(R.id.image_4);
        image5 = findViewById(R.id.image_5);
        image6 = findViewById(R.id.image_6);
        image7 = findViewById(R.id.image_7);
        image8 = findViewById(R.id.image_8);
        image9 = findViewById(R.id.image_9);
        image10 = findViewById(R.id.image_10);
        */



       /* LinearLayout layout = findViewById(R.id.linearLayout);
        for(int i=0;i<10;i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            image.setMaxHeight(20);
            image.setMaxWidth(20);

            // Adds the view to the layout
            layout.addView(image);
        }*/

    }


    @Override
    protected void onStart() {
        super.onStart();

        Query query = mRef.child("Images");

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String URL = dataSnapshot.child("Image1").getValue(String.class);
            String URL2 = dataSnapshot.child("Image2").getValue(String.class);
            String URL3 = dataSnapshot.child("Image3").getValue(String.class);
            String URL4 = dataSnapshot.child("Image4").getValue(String.class);
            String URL5 = dataSnapshot.child("Image5").getValue(String.class);
            String URL6 = dataSnapshot.child("Image6").getValue(String.class);
            String URL7 = dataSnapshot.child("Image7").getValue(String.class);
            String URL8 = dataSnapshot.child("Image8").getValue(String.class);
            String URL9 = dataSnapshot.child("Image9").getValue(String.class);
            String URL10 = dataSnapshot.child("Image10").getValue(String.class);


            /*Picasso.get().load(URL).into();
            Picasso.get().load(URL2).into(image2);
            Picasso.get().load(URL3).into(image3);
            Picasso.get().load(URL4).into(image4);
            Picasso.get().load(URL5).into(image5);
            Picasso.get().load(URL6).into(image6);
            Picasso.get().load(URL7).into(image7);
            Picasso.get().load(URL8).into(image8);
            Picasso.get().load(URL9).into(image9);
            Picasso.get().load(URL10).into(image10);*/




                Log.wtf("imageURL", "länk:"+ URL);
                Log.wtf("imageURL", "länk2:"+ URL2);
                Log.wtf("imageURL", "länk3:"+ URL3);
                Log.wtf("imageURL", "länk4:"+ URL4);
                Log.wtf("imageURL", "länk5:"+ URL5);
                Log.wtf("imageURL", "länk6:"+ URL6);
                Log.wtf("imageURL", "länk7:"+ URL7);
                Log.wtf("imageURL", "länk8:"+ URL8);
                Log.wtf("imageURL", "länk9:"+ URL9);
                Log.wtf("imageURL", "länk10:"+ URL10);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.wtf("onCancelledError", "fel vid läsning av bilder");
        }
    });

    }
}





