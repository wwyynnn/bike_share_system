package com.example.bikesshare;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Transliterator;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.tasks.Task;


import java.io.Serializable;
import java.util.*;




public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<MarkerOptions> markersArray = new ArrayList<MarkerOptions>();
    private ArrayList<Station> stationsList = new ArrayList<Station>();
    private DbOperator db;
    String userId;
    String permissionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        userId =getIntent().getStringExtra("UserId");
        permissionId =getIntent().getStringExtra("PermissionId");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        db = new DbOperator(this);

        SQLiteDatabase db1 = db.getReadableDatabase();

        Cursor stations = db1.rawQuery("select * from Station", null);
        for (int i = 0; i < stations.getCount(); i++) {
            if (stations.moveToNext()) {
                Station st = new Station(stations.getInt(0), stations.getString(1), stations.getInt(2), stations.getString(3), stations.getString(4),stations.getString(5), stations.getDouble(6),stations.getDouble(7));
                stationsList.add(st);


            }
        }

        createMarkerList();
        // Add a marker in Sydney and move the camera


        LatLng glsgw = new LatLng(55.871008, -4.288740);
       // mMap.addMarker(new MarkerOptions().position(glsgw).title("Marker in Glasgow"));
        float zoomAmount = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glsgw, zoomAmount));



        for (int i = 0; i < markersArray.size(); i++) {

            mMap.addMarker(markersArray.get(i));
            Log.d("HERE",markersArray.get(i).getTitle());
            /*markersArray.get(i).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(new MarkerOptions().position(markersArray.get(i).getPosition()));
            Log.d("HERE",markersArray.get(i).getTitle());*/


        }





      mMap.setOnMarkerClickListener(this);
        db.close();

    }








    public void createMarkerList(){

        for(int i =0; i<stationsList.size();i++){
            LatLng latLng = new LatLng(stationsList.get(i).getLatitude(), stationsList.get(i).getLongitude());
            MarkerOptions marker = new MarkerOptions().position(
                    latLng)
                    .title(stationsList.get(i).getStationName());


            Log.d("HERE",stationsList.get(i).getLongitude() +"");
            markersArray.add(marker);
        }
       /* LatLng latLng = new LatLng(55.8754, -4.2923);
        MarkerOptions hillhead = new MarkerOptions().position(
                latLng)
                .title("Hillhead");

        markersArray.add(hillhead);

        latLng = new LatLng(55.8251, -4.2864);
        MarkerOptions shawlands = new MarkerOptions().position(
                latLng)
                .title("Shawlands");

        markersArray.add(shawlands);

        latLng = new LatLng(55.8711, -4.3078);
        MarkerOptions patrick = new MarkerOptions().position(
                latLng)
                .title("Patrick");


        markersArray.add(patrick);*/










    }


    @Override
    public boolean onMarkerClick(Marker marker) {



       // Log.d("HERE","click detected");
        Intent intent = new Intent(MapsActivity.this,StationInfo.class);
        Station st = null;
        LatLng pos = marker.getPosition();
        Log.d("HERE",pos+"");
        int listId = -1;

        for(int i =0; i<markersArray.size();i++){
            if(pos.equals(markersArray.get(i).getPosition()))
            {
                st = stationsList.get(i);
                break;
                //listId = i;
            }
        }
        Log.d("HERE",st.getStationId()+"");



        intent.putExtra("stationId", st.getStationId()+"");
        intent.putExtra("UserId", userId);
        Log.d("The id from maps", userId);
        intent.putExtra("PermissionId", permissionId);
        startActivity(intent);


        return false;
    }
}


