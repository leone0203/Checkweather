package com.example.ideo2.checkweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Powitalne avtivity
 */
public class IntroActivity extends AppCompatActivity {

    LocationManager locationManager;
    Location location;
    Double lat;
    Double lng;



    /**
     * Przeniesienie do list activity
     */
    public void goToMyLocations(View view) {
        Intent i = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(i);
    }
    /**
     * Przeniesienie do weather activity wraz z danymi obecnej lokalizacji
     */
    public void goByCurrentLocation(View view) {
        getMyLocation();

        if (location != null) {

            Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
            i.putExtra("source", "IntroActivity");
            i.putExtra("lat", lat.toString());
            i.putExtra("lng", lng.toString());
            startActivity(i);

        } else {

            Toast.makeText(getApplicationContext(), "Cannot get lociation coordinates.", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * Pobranie lokalizacji
     */
    public void getMyLocation() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location==null) location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();

        }
    }

    /**
     * Metoda oncreate, w ktorej pobieramy lokalizacje oraz niezbedne pozwolenia od uzytkownika
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getMyLocation();

        getPermissionToLocation();
    }

    private static final int LOCATION_PERMISSION_REQUEST = 1;
    /**
     * uzyskanie pozwolenia na lokalizacje
     */
    public void getPermissionToLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            }
        }
    }
    /**
     * radanie pozwolenia na ustalenie lokalizacji
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean showRationale = true;
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    showRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                }

                if (showRationale) {
                    //to handle
                } else {
                    Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

}


}