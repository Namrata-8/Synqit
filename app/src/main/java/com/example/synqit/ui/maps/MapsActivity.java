package com.example.synqit.ui.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.synqit.R;
import com.example.synqit.fragments.insightfragment.model.UserDataMap;
import com.example.synqit.utils.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ViewPager viewPager;
    List<Marker> markerList = new ArrayList<>();
    ViewPagerAdapterMap viewPagerAdapter;
    int oldPosition = 0;
    FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private ImageButton btnLocation;
    private List<UserDataMap> userDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        viewPager = findViewById(R.id.viewPager);
        btnLocation = findViewById(R.id.btnLocation);

        userDataList = new ArrayList<>();
        Type type = new TypeToken<List<UserDataMap>>() {}.getType();
        userDataList = new Gson().fromJson(getIntent().getStringExtra("UserDataMaps"), type);

        client = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }else {
                    ActivityCompat.requestPermissions(MapsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<android.location.Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                           LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                           MarkerOptions options = new MarkerOptions().position(latLng);
                           googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                           googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initMarkers();
        if (!userDataList.isEmpty()) {
            viewPagerAdapter = new ViewPagerAdapterMap(getSupportFragmentManager(), userDataList);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    updateView(i);

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    private void updateView(int position) {
        if (!userDataList.isEmpty()) {
            markerList.get(position).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_selected_map));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userDataList.get(position).getLat(), userDataList.get(position).getLng()), 15));


            markerList.get(oldPosition).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_unselected_map));
            oldPosition = position;
        }
    }

    private void initMarkers() {

        if (!userDataList.isEmpty()) {
            for (int i = 0; i < userDataList.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(userDataList.get(i).getLat(), userDataList.get(i).getLng()));
                if (i == 0)
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_selected_map));
                else
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_unselected_map));
                markerList.add(mMap.addMarker(markerOptions));
            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userDataList.get(0).getLat(), userDataList.get(0).getLng()), 15));
        }else {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
}