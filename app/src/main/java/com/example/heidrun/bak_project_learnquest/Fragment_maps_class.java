package com.example.heidrun.bak_project_learnquest;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;

import android.location.LocationListener;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class Fragment_maps_class extends Fragment implements OnMapReadyCallback, LocationListener {
    private static final String TAG = "Maps_TaG";
    private GoogleMap mMap;
    private MapView mView;
    private View view;
    LocationManager loc;
    ArrayList<MarkerOptions> allMarkers;

    private static final int LOC_PERM_REQ_CODE = 1;
    //meters
    private static final int GEOFENCE_RADIUS = 150;
    //in milli seconds
    private static final int GEOFENCE_EXPIRATION = 6000;
    private GeofencingClient geofencingClient;

    private FusedLocationProviderClient mFusedLocationClient;

    float zoom = 18.0f;
    private LatLngBounds fh_Gelaende = new LatLngBounds(
            new LatLng(47.068679, 15.405647), new LatLng(47.070013, 15.410073));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_maps, container, false);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


        //Sofort überprüfen ob User der App erlaubt auf die Position zuzugreifen
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestLocationAccessPermission();
            //return null;
        }//else fehlt!!


        //Verschoben auf : onMapReady
        /*mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            Toast.makeText(getContext(), location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                            CheckDistance(location);
                        }
                    }
                });*/

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //hier weise ich auf Layout zurück
        mView = (MapView) view.findViewById(R.id.map);
        if (mView != null) {
            mView.onCreate(null);
            mView.onResume();
            mView.getMapAsync(this);
            geofencingClient = LocationServices.getGeofencingClient(getContext());
        }
        LocationCallback lc = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d(TAG, "onLocationResult");
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        loc = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, (LocationListener)this);
    }
    //Wenn Maps = da und angezeigt werden kann
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "Test methode 2", Toast.LENGTH_LONG).show();
        System.out.println("Hallo");
        mMap = googleMap;
        try {
            // Hier weise ich unsere App einen angepassten Maps Style zu (JSON-Datein aus Raw-Folder)
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.maps_style));
            //Falls dies nicht erfolgreich verläuft:
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
            //Falls keine JSON-Datei = vorhanden
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //Hier zoome ich auf den Fh-Campus hin (erstelle also einen neuen LatLng)
        LatLng fh = new LatLng(47.068990, 15.406672);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(fh, zoom);

        //Ich beschränke die Maps auf den Fh-Campus
        mMap.setLatLngBoundsForCameraTarget(fh_Gelaende);
        mMap.moveCamera(cameraUpdate);//(fh_Gelaende, 0));
        mMap.setMinZoomPreference(18.0f);
        //User kann ab hier nicht mehr reiin/raus zoomen
        mMap.getUiSettings().setZoomGesturesEnabled(false);

        //hier erstelle ich ein Objekt meiner Klasse Marker, welche mehrere LatLng beinhaltet
        //an welche ich Marker setzen möchte
        MarkerNaehern questMarkers = new MarkerNaehern();

        //Ich weise einer Arraylist meine LatLngs zu und mit Hilfe einer Schleife
        //setzte ich an all diesen LatLngs einen Marker
        allMarkers = (ArrayList<MarkerOptions>) questMarkers.createMarkers();
        for (int i = 0; i < allMarkers.size(); i++) {
            mMap.addMarker(allMarkers.get(i)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        }

        //Bei Marker-click öffnet sich hier nun das Question-Fragment
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new questionFragment()).commit();
                return false;
            }
        });

        //Hier möchte ich zu jedem Marker einen LocationAlert zuweisen
        ArrayList<LatLng> allLatLng = (ArrayList<LatLng>) questMarkers.getLatLng();
        for (int i = 0; i < allLatLng.size(); i++) {
            addLocationAlert(allLatLng.get(i).latitude, allLatLng.get(i).longitude);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Hier "aktivere" ich die Anzeige des Standorts (der Zugang dazu wird natürlich abgefragt)
        mMap.setMyLocationEnabled(true);

        //Hier möchte ich nun auf meine letzte bekannte Position zurückgreifen
        //und füge einen Listener hinzu, der meine letzte Position "ermittelt"
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Toast.makeText(getContext(), location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                            CheckDistance(location);
                        }
                    }
                });


    }

    @SuppressLint("MissingPermission")
    private void showCurrentLocationOnMap() {
        if (isLocationAccessPermitted()) {
            requestLocationAccessPermission();
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }
    //Überprüft ob pPositionsAbruf für App erlaubt ist
    private boolean isLocationAccessPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    //Fragt ob Permission zum Standort gegeben ist
    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_PERM_REQ_CODE);
    }

    //Eigentlich nicht mehr notwendig!! Da kein Geofencing!
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
                                // Toast.makeText(getContext(),
                                // "Location alter hasfdgsdfgs been added",
                                //Toast.LENGTH_SHORT).show();
                            } else {
                                // Toast.makeText(getContext(),
                                //  "Location alter could not be added",
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
                    Toast.makeText(getContext(),
                            "Location access permission granted, you try " +
                                    "add or remove location alerts",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(getActivity(), Fragment_maps_class.class);
        return PendingIntent.getService(getContext(), 0, intent,
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


    private void CheckDistance(Location location) {
        for (int i = 0; i < allMarkers.size(); i++) {
            LatLng marker = allMarkers.get(i).getPosition();
            Location markerloc = new Location("marker");
            markerloc.setLatitude(marker.latitude);
            markerloc.setLongitude(marker.longitude);

            Log.i("XXXX", "test");
            if (location.distanceTo(markerloc) < 10) {
                Toast.makeText(getContext(), "Marker in der Nähe" + i, Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(),"hier" +location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
