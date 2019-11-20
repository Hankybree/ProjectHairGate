package com.example.projecthairgate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Gallery extends AppCompatActivity {

    ImageView placeHolder;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    // create a reference object from the root in firebase that are called "Image"
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference first = databaseReference.child("Image");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        placeHolder.findViewById(R.id.ImageHolder);
    }

    /*
     * When activity starts, listen for changes in firebase Storage where the picture are stored.
     */
    @Override
    protected void onStart() {
        super.onStart();
        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String link = dataSnapshot.getValue(String.class); // Creates a String for which the image later are called
                Picasso.get().load(link).into(placeHolder); // Takes 'link' as (image name)/(reference) and puts it to our ImageView                -----------
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
