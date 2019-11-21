package com.example.projecthairgate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.LocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "Maps AboutActivity";
    private static final String FOUND_MAP = "Map found";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private boolean mLocationPermissionGranted = false;
    private MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        init();
        openMaps(savedInstanceState);

    }

    /*
     * Use case is only for keeping the check of permissions working
     */
    private void init(){
        Log.d("initMaps", "activity succes");
    }


    /*
     * Initialize the google maps
     */
    private void openMaps(Bundle savedInstanceState){

        if (isGoogleServiceCheckOk()){
            Bundle mapViewBundle = null;
            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
            }
            mMapView = findViewById(R.id.Google_maps);
            mMapView.onCreate(mapViewBundle);

            mMapView.getMapAsync(this);
        }
    }


   /*
    * If the user has play services AND the GPS i enabled, show the map
    */
    private boolean checkMapServices(){
        if(isGoogleServiceCheckOk()){
            if(isGpsEnabled()){
                return true;
            }
        }
        return false;
    }


    /*
    * STEP 1
    * This method is responsible for:
    * Determining whether the device is able to run Google Play services or not
    */
    public boolean isGoogleServiceCheckOk(){
        boolean isAvailable = false;

        // No issuse
        Log.d(TAG, "Checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AboutActivity.this);

        if (available == ConnectionResult.SUCCESS){
            isAvailable = true;
            Log.d(TAG, "Google Play Services is working");
            Toast.makeText(this, FOUND_MAP, Toast.LENGTH_LONG).show();
        }

        // Användaren kunde inte öppna google maps. lösning finns att tillgå av google egna popUp fönster
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "An error occured but we can fix it");

            // Pop up fönster som dirigerar användaren mot att installera 'google services'
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AboutActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        // Kan inte komma åt play service
        }else{
            Toast.makeText(this, "Sorry, you can't make map request", Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }


    /*
    * STEP 2
    * This method is responsible for:
    * checking if the user has GPS enabled on his/ or hers device or not.
    */
    public boolean isGpsEnabled(){
        // This class (LocationManager) provides access to the system location services. These services allow applications to obtain periodic updates of the device's geographical location,
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMesssageNoGps();
            return false; // not activated
        }
        return true; // Activated
    }

    /*
     * STEP 2.1
     * This method is responsible for:
     * If GPS are not active, prompt user with a dialog with a question of which the user want to activate the GPS.
     * If the user press yes redirect he/her to the settings page on their phone otherwise nothing happends with the google maps 'MapView'.
     */
    private void buildAlertMesssageNoGps(){
        final AlertDialog.Builder popUp = new AlertDialog.Builder(this);

        // if yes
        popUp.setMessage("GPS är ej aktiv, önskar du att sätta igång din GPS ?")
                .setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                // startActivityForResult enables the double check for which alternative that was picked by the user.
                // this is a callback with a constant to the method below
                startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);

            }
        });
        final AlertDialog alert = popUp.create();
        alert.show();
    }

    /*
     * STEP 2.2
     * This method is responsible for:
     * checking for that constant 'PERMISSIONS_REQUEST_ENABLE_GPS' and follow up if the user accepted or not and redirect the them.
     * it means that we are checking if the user have said yes or no.
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) { // explicit asking if the permission is true
                    init(); // user has ascpliaccepted so just keep running the app as usual
                } else {
                    getLocationPermission(); // no permission to use ther location
                }
            }
        }
    }


    /*
     * STEP 3
     * This method is responsible for:
     * The request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult under this method.
     */
    private void getLocationPermission() {

        // check if user has or have accepted our ACCESS_FINE_LOCATION permission.
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    init(); // if user already accepted sometime before just run the app as intended;
        } else {
            // otherwise ask for permission (Dialog will show up)
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /*
     * STEP 4
     * This method is gonna run after they accepted or denied the permission and if the permission are still denied the program will prompt the same dialog
     * as the one that executes in the method above if 'mLocationPermissionGranted' = is FALSE
     * This all happends because we are also here checking a constant that are called 'PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION'
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false; // no permission granted yet

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0  // If request is granted, the result array are not empty.
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)  // and a explicit confirmation is granted
                {
                    mLocationPermissionGranted = true; // Clear to use the user's location
                }
            }
        }
    }

    /*
     * Save the state of google maps if device are turned horizontal or vertical.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    /*
     * This override method gives the utility that we want.
     */
    @Override
    public void onMapReady(GoogleMap mMap) {
        LatLng harPorten = new LatLng(57.105495, 12.251454); // Finds location

        // Places a pin on desired location which show information
       Marker PinHarPorten = mMap.addMarker(new MarkerOptions().position(harPorten)
               .title("hårporten")
       .snippet("Torggatan 5, 43241 Varberg"));
       PinHarPorten.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(harPorten, 15)); // Map opens on this location
    }

    /*
     * This override handles the convenience for the user:
     * if permission GRANTED, show map and always show map when the activity opens.
     * Otherwise redirect user to 'getLocationPermission' = a PopUp window shows and asks the user
     * for permission to to use their location as also specified in comment for that method
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                init();
                mMapView.onResume();
            }
            else{
                getLocationPermission();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}

