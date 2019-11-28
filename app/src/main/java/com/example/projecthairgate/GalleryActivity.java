package com.example.projecthairgate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    public final int CAMERA_REQUEST_CODE = 1;
    private String picturePath;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap image = BitmapFactory.decodeFile(picturePath);

                // TODO face swap-code
            }
        }
    }

    public void onClickTakePicture(View view)
    {
        showCamera();
    }

    private void showCamera()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getPackageManager()) != null)
        {
            File imageFile;

            try
            {
                imageFile = createImageFile();
            }
            catch (IOException e)
            {
                return;
            }

            if(imageFile != null)
            {
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.projecthairgate.fileprovider", imageFile);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException
    {
        File image;

        // Path to images in phone
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // New image folder
        File imageDir = new File(dir, "HairImages");

        // Check if folders exist
        if(!imageDir.exists())
        {
            if(!imageDir.mkdirs())
            {
                Toast toast = Toast.makeText(this, "Could not create folder", Toast.LENGTH_LONG);

                toast.show();
            }
        }

        // Create the image file
        image = File.createTempFile("hairstyle", ".jpg", imageDir);

        picturePath = image.getAbsolutePath();

        return image;
    }

    public void onClickIg(View view) {
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





