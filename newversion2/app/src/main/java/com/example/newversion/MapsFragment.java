package com.example.newversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locManager;
    MylocationListener locListener;
    Button getLocation;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        addressText = (EditText) view.findViewById(R.id.txt_loc);
        getLocation = (Button) view.findViewById(R.id.btn_location_activity);
        locListener = new MylocationListener(getActivity());
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, locListener);
        } catch (SecurityException ex) {
            Toast.makeText(getActivity(), "Loc not allowed", Toast.LENGTH_SHORT).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getActivity());
                List<Address> addressList;
                Location loc = null;
                try {

                    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch (SecurityException x) {
                    Toast.makeText(getActivity(), "not allowed to access", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "wait until determined", Toast.LENGTH_SHORT).show();
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
                Geocoder coder = new Geocoder(getActivity());
                List<Address> addressList;
                try {
                    addressList = coder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (!addressList.isEmpty()) {
                        String address = "";
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++)
                            address += addressList.get(0).getAddressLine(i) + " , ";
                        addressText.getText().clear();
                    } else {
                        Toast.makeText(getActivity(), "no address for this location", Toast.LENGTH_SHORT).show();
                        addressText.getText().clear();
                    }
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "cant get address for this location", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}