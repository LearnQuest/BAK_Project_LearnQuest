package com.example.heidrun.bak_project_learnquest;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MarkerNaehern {

    ArrayList<MarkerOptions> allMarkers;
    ArrayList<LatLng> allMarkersLatLng;

    /**
     * Hier wird die Methode CreateAllLatLng übergeben
     */
    public MarkerNaehern() {
        createAllLatLng();
    }

    /**
     * Hier werden einzelne Marker erzeugt
      */
    public void createAllLatLng() {
        try {
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
            allMarkersLatLng.add(new LatLng(47.069071, 15.405823));
        }catch(Exception ex){
           Log.d("TAG", ex.toString());
        }
    }

    /**
     * Hier werden die einzelnen Marker einer ArrayList zugeordnet
     * @return
     */
    public ArrayList createMarkers() {

        allMarkers = new ArrayList<>();
        for (int i = 0; i < allMarkersLatLng.size(); i++) {
            allMarkers.add(new MarkerOptions()
                    .position(allMarkersLatLng.get(i))
                    .title("Question " + (i + 1)));
        }
        return allMarkers;
    }

    public ArrayList getLatLng() {
        return allMarkersLatLng;
    }

}
