package com.example.newversion;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locManager;
    MylocationListener locListener;
    Button getLocation;

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] CAMERA_PERMS={
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS={
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int CAMERA_REQUEST=INITIAL_REQUEST+1;
    private static final int CONTACTS_REQUEST=INITIAL_REQUEST+2;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addressText = (EditText) findViewById(R.id.txt_current_location_actvity);
        getLocation = (Button) findViewById(R.id.btn_location_activity);
         Button Next_step=(Button)findViewById(R.id.button2);
        locListener = new MylocationListener(getApplicationContext());
        locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);
        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "Loc not allowed", Toast.LENGTH_SHORT).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressText.getText() !=null){
                    Intent gotocheckout=new Intent(MapsActivity.this,checkout.class);
                    Bundle b = new Bundle();
                    String address = addressText.getText().toString();
                    b.putString("address",address);
                    b.putString("order total", "200");
                   gotocheckout.putExtras(b);
                    MapsActivity.this.startActivity(gotocheckout);
                finish();
                }
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));
        getUserLocation();
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;
                try {

                    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch (SecurityException x) {
                    Toast.makeText(getApplicationContext(), "not allowed to access", Toast.LENGTH_SHORT).show();
                }
                if (loc != null) {
                    LatLng myPosition = new LatLng(loc.getLatitude(), loc.getLongitude());
                    try {
                        addressList = coder.getFromLocation(myPosition.latitude, myPosition.longitude, 1);
                        if (!addressList.isEmpty()) {
                            String adress = "";
                            for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++)
                                adress += addressList.get(0).getAddressLine(i) + ", ";
                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(adress)).setDraggable(true);
                            addressText.setText(adress);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
                } else {
                    Toast.makeText(getApplicationContext(), "wait until determined", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList = coder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (!addressList.isEmpty()) {
                        String address = "";
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++)
                            address += addressList.get(0).getAddressLine(i) + " , ";
                        addressText.getText().clear();
                    } else {
                        Toast.makeText(getApplicationContext(), "no address for this location", Toast.LENGTH_SHORT).show();
                        addressText.getText().clear();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "cant get address for this location", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void getUserLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
                return;
            }


        }
    }
}