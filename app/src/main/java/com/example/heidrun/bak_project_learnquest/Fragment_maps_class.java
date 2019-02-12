package com.example.heidrun.bak_project_learnquest;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;


import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 *
 */
public class Fragment_maps_class extends Fragment implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Maps_TaG";
    private static final long INTERVAL = 10 * 60 * 1;
    private static final long FASTEST_INTERVAL = 10 * 60 * 1;
    private GoogleMap mMap;
    private MapView mView;
    private View view;
    LocationManager loc;
    ArrayList<MarkerOptions> allMarkersOptions;
    ArrayList<Marker> allMarkers;
    ArrayList<Marker> visitedMarkers = new ArrayList<>();

    private static final int LOC_PERM_REQ_CODE = 1;


    private FusedLocationProviderClient mFusedLocationClient;

    float zoom = 18.0f;
    private LatLngBounds fh_Gelaende = new LatLngBounds(
            new LatLng(47.068679, 15.405647), new LatLng(47.070013, 15.410073));

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;

    boolean gps_enabled = false;
    boolean network_enabled = false;

    ArrayList<Question> QuestionsArray;
    private final static String MY_PREFS_NAME = "LearnQuest_Pref_Subject";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_maps, container, false);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        loc = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (getArguments() != null) {
            ArrayList<Question> ArrQuestion = (ArrayList<Question>) getArguments().getSerializable("questions");
            if (ArrQuestion != null) {
                QuestionsArray = ArrQuestion;
            }
        } else {

            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String sub = prefs.getString("CourseName", "");
            if (!sub.equals("")) {
                //gewähltes Fach wieder aktivieren!

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
                databaseAccess.open();
                ArrayList<Question> arrQuestion = databaseAccess.getQuestion(sub);
                QuestionsArray = arrQuestion;
            }
        }
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


        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
        return view;

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //hier weise ich auf Layout zurück
        mView = (MapView) view.findViewById(R.id.map);
        if (mView != null) {
            mView.onCreate(null);
            mView.onResume();
            mView.getMapAsync(this);
        }
        LocationCallback lc = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };

    }


    //Wenn Maps = da und angezeigt werden kann
    public void onMapReady(GoogleMap googleMap) {

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
        allMarkersOptions = (ArrayList<MarkerOptions>) questMarkers.createMarkers();
        allMarkers = new ArrayList<Marker>();
        for (int i = 0; i < allMarkersOptions.size(); i++) {
            allMarkers.add(mMap.addMarker(allMarkersOptions.get(i)));
            allMarkers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        }

        //Bei Marker-click öffnet sich hier nun das Question-Fragment
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (checkForQuestion(marker) == true) {

                    //add marker to list for db
                    if(!visitedMarkers.contains(marker)) {
                        visitedMarkers.add(marker);
                        //write to database
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
                        databaseAccess.open();

                        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        String v = prefs.getString("Email", "");
                        databaseAccess.writeToTrophie(7,v );
                        databaseAccess.writeToTrophie(8,v);
                        databaseAccess.writeToTrophie(9,v);
                        databaseAccess.close();
                    }


                    if (QuestionsArray == null) {
                        Snackbar.make(view, "Du hast leider kein Fach ausgewählt!", Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
                    } else {
                        if (QuestionsArray.size() != 0) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("questions", QuestionsArray);
                            // set MyFragment Arguments
                            questionFragment myObj = new questionFragment();
                            myObj.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myObj).commit();
                        } else if(QuestionsArray.size() == 0) {
                            final Dialog d = new Dialog(getContext());
                            d.setTitle("Help");
                            d.setContentView(R.layout.allquestionsdone_dialog);
                            d.show();

                            TextView next = d.findViewById(R.id.allDone);
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();

                                }
                            });
                        }
                    }
                } else {
                    final Dialog d = new Dialog(getContext());
                    d.setTitle("Faulpelz");
                    d.setContentView(R.layout.faulpelzdialog);
                    d.show();

                    TextView tv = d.findViewById(R.id.allesklar);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.cancel();
                        }
                    });
                }
                return true;
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
        checkLocationService();
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

    //Überprüft ob PositionsAbruf für App erlaubt ist
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

    private boolean checkForQuestion(Marker marker) {

        LatLng marker1 = marker.getPosition();
        Location markerloc = new Location("markerpos");
        markerloc.setLatitude(marker1.latitude);
        markerloc.setLongitude(marker1.longitude);
        if (markerloc != null && mCurrentLocation != null) {
            if (mCurrentLocation.distanceTo(markerloc) < 10) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void CheckDistance(Location location) {
        for (int i = 0; i < allMarkersOptions.size(); i++) {

            LatLng marker = allMarkersOptions.get(i).getPosition();
            Location markerloc = new Location("markerpos");
            markerloc.setLatitude(marker.latitude);
            markerloc.setLongitude(marker.longitude);

            if (location.distanceTo(markerloc) < 10) {
                allMarkers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.drache_geschafft_1));
                Snackbar.make(view, "Eine Frage befindet sich hier in deiner Nähe", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();


            } else {
                allMarkers.get(i).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
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
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        CheckDistance(location);
    }

    public void checkLocationService() {
        createLocationRequest();
        gps_enabled = loc.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = loc.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps_enabled && !network_enabled) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bitte aktiviere dein GPS und das Internet für die Positionsbestimmung");
            dialog.setPositiveButton("Aktivieren", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getContext().startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    paramDialogInterface.dismiss();
                    checkLocationService();
                }
            });
            dialog.show();
        }
    }
}
