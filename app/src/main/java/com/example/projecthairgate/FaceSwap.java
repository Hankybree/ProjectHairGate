package com.example.projecthairgate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FaceSwap {

    Bitmap faceImg;
    FirebaseVisionImage faceVsnImg;
    List<FirebaseVisionPoint> pointsFace1;

    Bitmap faceImg2;
    FirebaseVisionImage faceVsnImg2;

    float rotFace1;
    float rotFace2;

    Bitmap croppedImage;
    Bitmap finalImage;

    Path path;

    FirebaseVisionFaceDetector faceDetector;

    ImageView iv;
    ProgressBar pb;

    DatabaseHelper db;

    public FaceSwap(Bitmap faceImg, Bitmap faceImg2, ImageView iv, ProgressBar pb, DatabaseHelper db) {

        this.iv = iv;
        this.pb = pb;
        this.db = db;

        this.faceImg = faceImg;
        faceVsnImg = FirebaseVisionImage.fromBitmap(faceImg);

        this.faceImg2 = faceImg2;
        faceVsnImg2 = FirebaseVisionImage.fromBitmap(faceImg2);

        faceDetector = createDetector();
    }

    public FaceSwap(byte[] storedFace, Bitmap faceImg2, ImageView iv, ProgressBar pb) {
        this.iv = iv;
        this.pb = pb;

        this.croppedImage = BitmapFactory.decodeByteArray(storedFace, 0, storedFace.length);
        //faceVsnImg = FirebaseVisionImage.fromBitmap(faceImg);

        this.faceImg2 = faceImg2;
        faceVsnImg2 = FirebaseVisionImage.fromBitmap(faceImg2);

        faceDetector = createDetector();
    }

    public void runFaceDetector() {

        faceDetector.detectInImage(faceVsnImg)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {

                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {

                        Bitmap mutableImage = faceImg.copy(Bitmap.Config.ARGB_8888, true);

                        detectFace(firebaseVisionFaces, mutableImage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void runDetectorWithStoredImage() {
        extractFace();
    }

    private void detectFace(List<FirebaseVisionFace> firebaseVisionFaces, Bitmap mutableImage) {

        if(firebaseVisionFaces == null || mutableImage == null) {
            return;
        }

        pointsFace1 = null;
        for (int i = 0; i < firebaseVisionFaces.size(); i++) {
            pointsFace1 = firebaseVisionFaces.get(i).getContour(FirebaseVisionFaceContour.FACE).getPoints();
        }

        if(pointsFace1 == null) {
            pb.setVisibility(View.INVISIBLE);
            //Toast toast = Toast.makeText(GalleryActivity.class, "Hittade inget ansikte. Försök igen med främre kameran", Toast.LENGTH_LONG).show();
            return;
        }

        Canvas canvas = new Canvas(mutableImage);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.GREEN);

        path = new Path();
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        path.moveTo(pointsFace1.get(0).getX(), pointsFace1.get(0).getY());

        for(int i = 1; i < pointsFace1.size(); i++) {

            path.lineTo(pointsFace1.get(i).getX(), pointsFace1.get(i).getY());
        }

        path.lineTo(pointsFace1.get(0).getX(), pointsFace1.get(0).getY());
        path.close();

        canvas.drawPath(path, paint);

        rotFace1 = firebaseVisionFaces.get(0).getHeadEulerAngleZ();

        int leftmostX = Math.round(getLeftmostX(pointsFace1));
        int topY = Math.round(getTopY(pointsFace1));
        int width = Math.round(getWidth(pointsFace1));
        int height = Math.round(getHeight(pointsFace1));

        croppedImage = Bitmap.createBitmap(mutableImage, leftmostX, topY, width, height);
        croppedImage.setHasAlpha(true);

        setPixelsInBitmap(croppedImage);

        db.addData(getBytes(croppedImage));

        extractFace();
    }

    private FirebaseVisionFaceDetector createDetector() {

        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS).build();

        FirebaseVisionFaceDetector faceDetector = FirebaseVision.getInstance().getVisionFaceDetector(options);

        return faceDetector;
    }

    private void extractFace() {

        faceDetector.detectInImage(faceVsnImg2)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {

                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {

                        Bitmap mutableImage = faceImg2.copy(Bitmap.Config.ARGB_8888, true);

                        faceSwap(firebaseVisionFaces, mutableImage);
                    }
                });
    }

    private float getLeftmostX(List<FirebaseVisionPoint> points) {

        ArrayList<Float> sortedList = new ArrayList<>();

        for(int i = 0; i < points.size(); i++) {
            sortedList.add(points.get(i).getX());
        }

        for (int i = 0; i < sortedList.size()-1; i++) {

            for (int j = 0; j < sortedList.size()-i-1; j++) {

                if (sortedList.get(j) > sortedList.get(j+1)) {
                    // swap arr[j+1] and arr[i]
                    float temp = sortedList.get(j);
                    sortedList.set(j,sortedList.get(j+1));
                    sortedList.set(j+1, temp);
                }
            }
        }

        return sortedList.get(0);
    }

    private float getTopY(List<FirebaseVisionPoint> points) {

        ArrayList<Float> sortedList = new ArrayList<>();

        for(int i = 0; i < points.size(); i++) {
            sortedList.add(points.get(i).getY());
        }

        for (int i = 0; i < sortedList.size()-1; i++) {

            for (int j = 0; j < sortedList.size()-i-1; j++) {

                if (sortedList.get(j) > sortedList.get(j+1)) {
                    // swap arr[j+1] and arr[i]
                    float temp = sortedList.get(j);
                    sortedList.set(j,sortedList.get(j+1));
                    sortedList.set(j+1, temp);
                }
            }
        }

        return sortedList.get(0);
    }

    private float getWidth(List<FirebaseVisionPoint> points) {

        ArrayList<Float> sortedList = new ArrayList<>();

        for(int i = 0; i < points.size(); i++) {
            sortedList.add(points.get(i).getX());
        }

        for (int i = 0; i < sortedList.size()-1; i++) {

            for (int j = 0; j < sortedList.size()-i-1; j++) {

                if (sortedList.get(j) > sortedList.get(j+1)) {
                    // swap arr[j+1] and arr[i]
                    float temp = sortedList.get(j);
                    sortedList.set(j,sortedList.get(j+1));
                    sortedList.set(j+1, temp);
                }
            }
        }

        return sortedList.get(sortedList.size() - 1) - sortedList.get(0);
    }

    private float getHeight(List<FirebaseVisionPoint> points) {

        ArrayList<Float> sortedList = new ArrayList<>();

        for(int i = 0; i < points.size(); i++) {
            sortedList.add(points.get(i).getY());
        }

        for (int i = 0; i < sortedList.size()-1; i++) {

            for (int j = 0; j < sortedList.size()-i-1; j++) {

                if (sortedList.get(j) > sortedList.get(j+1)) {
                    // swap arr[j+1] and arr[i]
                    float temp = sortedList.get(j);
                    sortedList.set(j,sortedList.get(j+1));
                    sortedList.set(j+1, temp);
                }
            }
        }

        return sortedList.get(sortedList.size() - 1) - sortedList.get(0);
    }

    private void setPixelsInBitmap(Bitmap alteredBitmap) {

        int[] pixels = new int[alteredBitmap.getHeight()*alteredBitmap.getWidth()];
        alteredBitmap.getPixels(pixels, 0, alteredBitmap.getWidth(), 0, 0, alteredBitmap.getWidth(), alteredBitmap.getHeight());
        for (int i=0; i<alteredBitmap.getWidth()*alteredBitmap.getHeight(); i++) {
            if(pixels[i] == Color.GREEN) {
                pixels[i] = Color.TRANSPARENT;
            }
        }

        alteredBitmap.setPixels(pixels, 0, alteredBitmap.getWidth(), 0, 0, alteredBitmap.getWidth(), alteredBitmap.getHeight());
    }

    private Bitmap createFinalImage(float rotFace1, float rotFace2, int width, int heigth, Bitmap image) {

        float rotationDifference = rotFace1 - rotFace2;

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationDifference);

        Bitmap rotatedImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);

        Bitmap resizedImage = Bitmap.createScaledBitmap(rotatedImage, width, heigth, false);

        return resizedImage;
    }

    private void faceSwap(List<FirebaseVisionFace> firebaseVisionFaces, Bitmap mutable) {

        List<FirebaseVisionPoint> points = null;
        for (int i = 0; i < firebaseVisionFaces.size(); i++) {
            points = firebaseVisionFaces.get(i).getContour(FirebaseVisionFaceContour.FACE).getPoints();
        }

        float leftX = getLeftmostX(points);
        float topY = getTopY(points);

        rotFace2 = firebaseVisionFaces.get(0).getHeadEulerAngleZ();
        int width = Math.round(getWidth(points));
        int height = Math.round(getHeight(points));

        Canvas canvas = new Canvas(mutable);

        finalImage = createFinalImage(rotFace1, rotFace2, width, height, croppedImage);

        canvas.drawBitmap(finalImage, leftX, topY, null);

        iv.setImageBitmap(mutable);

        pb.setVisibility(View.INVISIBLE);
    }

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
