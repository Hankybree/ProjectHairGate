package com.example.projecthairgate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private FirebaseDatabase mRoot;
    private DatabaseReference mRef;

    private RecyclerView staggeredRv;
    private GalleryGridAdapter adapter;
    private StaggeredGridLayoutManager manager;

    List<GalleryRows> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        staggeredRv = findViewById(R.id.staggerd_rv);
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredRv.setLayoutManager(manager);

        mRoot = FirebaseDatabase.getInstance();
        mRef = mRoot.getReference("Images");

        images = new ArrayList<>();
    }


    @Override
    protected void onStart() {
        super.onStart();

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String URL = postSnapshot.getValue(String.class);

                images.add(new GalleryRows(URL));
            }

            adapter = new GalleryGridAdapter(getApplicationContext(),images);
            staggeredRv.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.wtf("onCancelledError", "fel vid läsning av bilder");
        }
    });

    }

    public void onClick(View view) {
        //Links to harportens instagram
        Uri uri = Uri.parse("http://instagram.com/_u/harportenvarberg");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/harportenvarberg")));
        }
    }

    public void onClickFb(View view) {
        //Links to harportens facebook
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/577574002374672"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/harporten.varberg")));
        }
    }
}





