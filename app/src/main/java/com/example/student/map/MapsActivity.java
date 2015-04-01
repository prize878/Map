package com.example.student.map;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity
implements GoogleMap.OnMapLongClickListener,LocationListener{


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    LocationManager lMgr;
    LatLng previousLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        //Set Map to accept long click
        mMap.setOnMapLongClickListener(this);

        //LocationListener
        lMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);//(time,distance,this)
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng BKK = new LatLng(13.7500, 100.4833);
        LatLng HK = new LatLng(22.2783, 114.1747);
        //mMap.addMarker(new MarkerOptions().position(BKK).title("Bangkok"));
        //mMap.addMarker(new MarkerOptions().position(HK).title("HongKong"));

        //when start the app, start at the location Thailand
        // else(not setting up), it will automatically set to Lat,lng=(0,0)
        //CameraUpdate start_map = CameraUpdateFactory.newLatLng(BKK);
        //mMap.moveCamera(start_map);

        //change the Map Zoom Level
        //CameraUpdate start_zoom = CameraUpdateFactory.zoomTo(7);
        //mMap.animateCamera(start_zoom);

        //Draw a line between 2 points
        //polyline is a line containing >= 2 points
        //mMap.addPolyline(new PolylineOptions().add(BKK).add(HK));

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        LatLng OTHERPLACE = latLng;
        mMap.addMarker(new MarkerOptions().position(OTHERPLACE).title("Place" + ++Place));

        CameraUpdate con_map = CameraUpdateFactory.newLatLng(OTHERPLACE);
        mMap.moveCamera(con_map);

        CameraUpdate con_zoom = CameraUpdateFactory.zoomTo(5);
        mMap.animateCamera(con_zoom);

        if(previousLatLng != null) {
            mMap.addPolyline(new PolylineOptions().add(previousLatLng).add(OTHERPLACE));
        }

        previousLatLng = OTHERPLACE;
    }

    int Place = 0;


    //From LocationListener
    @Override
    public void onLocationChanged(Location location) {

        LatLng OTHERPLACE = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(OTHERPLACE).title("Place" + ++Place));
        // (++Place) plus Place everytime with 1

        CameraUpdate con_map = CameraUpdateFactory.newLatLng(OTHERPLACE);
        mMap.moveCamera(con_map);

        CameraUpdate con_zoom = CameraUpdateFactory.zoomTo(5);
        mMap.animateCamera(con_zoom);

        if(previousLatLng != null) {
            mMap.addPolyline(new PolylineOptions().add(previousLatLng).add(OTHERPLACE));
        }

        previousLatLng = OTHERPLACE;


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
