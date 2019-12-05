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
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private Bitmap faceToSwap;

    private FirebaseDatabase mRoot;
    private DatabaseReference mRef;

    private RecyclerView staggeredRv;
    private GalleryGridAdapter adapter;
    private StaggeredGridLayoutManager manager;
    private List<GalleryRows> images;
    boolean imageAlreadyExists;

    int selectedImagePos;

    private ImageView iv;
    private ProgressBar pb;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        staggeredRv = findViewById(R.id.staggerd_rv);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredRv.setLayoutManager(manager);

        mRoot = FirebaseDatabase.getInstance();
        mRef = mRoot.getReference("Images");

        images = new ArrayList<>();

        iv = findViewById(R.id.test_view);
        pb = findViewById(R.id.face_swap_pb);

        dbHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    imageAlreadyExists = false;

                    String URL = postSnapshot.getValue(String.class);

                    for (int i = 0; i < images.size(); i++) {
                        if (images.get(i).getImg().equals(URL)) {
                            imageAlreadyExists = true;
                        }
                    }

                    if (!imageAlreadyExists) {
                        images.add(new GalleryRows(URL));
                    }
                }

                adapter = new GalleryGridAdapter(getApplicationContext(), images);
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

                Bitmap photoTaken = BitmapFactory.decodeFile(picturePath);

                Matrix matrix = new Matrix();

                matrix.setRotate(-90);

                faceToSwap = Bitmap.createBitmap(photoTaken, 0, 0, photoTaken.getWidth(), photoTaken.getHeight(), matrix, false);

                pb.setVisibility(View.VISIBLE);

                generateFaceSwappedImage();
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
        image = File.createTempFile("hairstyle", ".png", imageDir);

        picturePath = image.getAbsolutePath();

        return image;
    }

    private void generateFaceSwappedImage() {

        selectedImagePos = adapter.getPositionOfDbPics();

        FaceSwap faceSwap = new FaceSwap(faceToSwap, images.get(selectedImagePos).getBitmap(), iv, pb, dbHelper, getApplicationContext());
        faceSwap.runFaceDetector();
    }

    public void onClickFaceSwapStoredImage(View view) {

        byte[] bytes = dbHelper.getContent();

        selectedImagePos = adapter.getPositionOfDbPics();

        pb.setVisibility(View.VISIBLE);

        FaceSwap faceSwap = new FaceSwap(bytes, images.get(selectedImagePos).getBitmap(), iv, pb);
        faceSwap.runDetectorWithStoredImage();
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





