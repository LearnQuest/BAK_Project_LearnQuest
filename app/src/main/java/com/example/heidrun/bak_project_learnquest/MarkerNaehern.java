package com.example.heidrun.bak_project_learnquest;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MarkerNaehern {

    ArrayList<MarkerOptions> allMarkers;
    ArrayList<LatLng> allMarkersLatLng;

      /*mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069335, 15.406887))
            .title("Question 1")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069188, 15.406853))
            .title("Question 2")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069453, 15.406402))
            .title("Question 3")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069121, 15.406330))
            .title("Question 4")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069791, 15.405983))
            .title("Question 5")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069654, 15.407368))
            .title("Question 6")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069550, 15.405640))
            .title("Question 7")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069335, 15.406887))
            .title("Question 8")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069408, 15.407031))
            .title("Question 9")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069092, 15.407293))
            .title("Question 10")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069127, 15.408626))
            .title("Question 11")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069160, 15.409731))
            .title("Question 12")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(47.069599, 15.407488))
            .title("Question 13")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_position));*/

public MarkerNaehern(){
createAllLatLng();
}

public void createAllLatLng(){
    allMarkersLatLng = new ArrayList();
    allMarkersLatLng.add(new LatLng(47.069335, 15.406887));
    allMarkersLatLng.add(new LatLng(47.069188, 15.406853));
    allMarkersLatLng.add(new LatLng(47.069453, 15.406402));
    allMarkersLatLng.add(new LatLng(47.069121, 15.406330));
    allMarkersLatLng.add(new LatLng(47.069791, 15.405983));
    allMarkersLatLng.add(new LatLng(47.069654, 15.407368));
    allMarkersLatLng.add(new LatLng(47.069550, 15.405640));
    allMarkersLatLng.add(new LatLng(47.069335, 15.406887));
    allMarkersLatLng.add(new LatLng(47.069408, 15.407031));
    allMarkersLatLng.add(new LatLng(47.069092, 15.407293));
    allMarkersLatLng.add(new LatLng(47.069127, 15.408626));
    allMarkersLatLng.add(new LatLng(47.069160, 15.409731));
    allMarkersLatLng.add(new LatLng(47.069599, 15.407488));
}

public ArrayList createMarkers(){
    allMarkers = new ArrayList<>();
    for (int i = 0; i < allMarkersLatLng.size(); i++) {
        allMarkers.add(new MarkerOptions()
                .position(allMarkersLatLng.get(i))
                .title("Question " + (i +1)));
    }
    return allMarkers;
}

public ArrayList getLatLng(){
    return allMarkersLatLng;
}

}
