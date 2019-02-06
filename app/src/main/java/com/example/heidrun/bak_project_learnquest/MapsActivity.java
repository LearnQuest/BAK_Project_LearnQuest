package com.example.heidrun.bak_project_learnquest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ResultCallback<Status>, LocationListener {

    private static final String TAG = "Maps_TaG";
    private GoogleMap mMap;
    private static final int LOC_PERM_REQ_CODE = 1;
    //meters
    private static final int GEOFENCE_RADIUS = 150;
    //in milli seconds
    private static final int GEOFENCE_EXPIRATION = 6000;
    private GeofencingClient geofencingClient;

    //ProbeMarker
    Marker geof;


    float zoom = 18.0f;
    private LatLngBounds fh_Gelaende = new LatLngBounds(
            new LatLng(47.068679, 15.405647), new LatLng(47.070013, 15.410073));

    ArrayList<MarkerOptions> allMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(this);



    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void changeType(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //   mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        //  mMap.setOnMapClickListener((GoogleMap.OnMapClickListener) this);
        // mMap.setOnMyLocationChangeListener(this);


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.maps_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        LatLng fh = new LatLng(47.068990, 15.406672);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(fh, zoom);
        mMap.setLatLngBoundsForCameraTarget(fh_Gelaende);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(fh_Gelaende, 0));
        mMap.setMinZoomPreference(18.0f);
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addLocationAlert(latLng.latitude, latLng.longitude);
            }
        });*/


        MarkerNaehern questMarkers = new MarkerNaehern();
        allMarkers = (ArrayList<MarkerOptions>) questMarkers.createMarkers();
        for (int i = 0; i < allMarkers.size(); i++) {
            mMap.addMarker(allMarkers.get(i)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.drachen_position));

            if (i == allMarkers.size() - 1) {
                MarkerOptions om = new MarkerOptions()
                        .position(new LatLng(47.069071, 15.405823))
                        .title("Test Geofencing Marker");
                geof = mMap.addMarker(om);
            }
        }
        ArrayList<LatLng> allLatLng = (ArrayList<LatLng>) questMarkers.getLatLng();
        for (int i = 0; i < allLatLng.size(); i++) {
            addLocationAlert(allLatLng.get(i).latitude, allLatLng.get(i).longitude);
        }


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
        mMap.setMyLocationEnabled(true);

    }


    @SuppressLint("MissingPermission")
    private void showCurrentLocationOnMap() {
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private boolean isLocationAccessPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_PERM_REQ_CODE);
    }

    @SuppressLint("MissingPermission")
    private void addLocationAlert(double lat, double lng) {
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else {
            String key = "" + lat + "-" + lng;
            Geofence geofence = getGeofence(lat, lng, key);
            geofencingClient.addGeofences(getGeofencingRequest(geofence),
                    getGeofencePendingIntent())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Toast.makeText(MapsActivity.this,
                                // "Location alter has been added",
                                //   Toast.LENGTH_SHORT).show();
                            } else {
                                //  Toast.makeText(MapsActivity.this,
                                //"Location alter could not be added",
                                // Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOC_PERM_REQ_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showCurrentLocationOnMap();
                    Toast.makeText(MapsActivity.this,
                            "Location access permission granted, you try " +
                                    "add or remove location alerts",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private PendingIntent getGeofencePendingIntent() {

        Intent intent = new Intent(this, MapsActivity.class);
        return PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private Geofence getGeofence(double lat, double lang, String key) {
        return new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(lat, lang, GEOFENCE_RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(2000)
                .build();
    }

    Circle geoFenceLimits;

    @Override
    public void onResult(@NonNull Status status) {
        if (geoFenceLimits != null) {
            geoFenceLimits.remove();
        }

        CircleOptions cp = new CircleOptions()
                .center(geof.getPosition())
                .strokeColor(Color.argb(50, 70, 70, 70))
                .fillColor(Color.argb(100, 150, 150, 150))
                .radius(400f);

        geoFenceLimits = mMap.addCircle(cp);
    }

    @Override
    public void onLocationChanged(Location location) {

    }



    //neu
}
