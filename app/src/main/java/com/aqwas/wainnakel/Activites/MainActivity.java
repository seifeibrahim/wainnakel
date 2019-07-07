package com.aqwas.wainnakel.Activites;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aqwas.wainnakel.APIHandler.ApiClient;
import com.aqwas.wainnakel.APIHandler.ApiInterface;
import com.aqwas.wainnakel.Models.RestaurantModel;
import com.aqwas.wainnakel.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.mapview)
    MapView mapView;
    @BindView(R.id.tv_restaurant_name)
    TextView tvRestaurantName;
    @BindView(R.id.tv_rate_type)
    TextView tvRateType;
    @BindView(R.id.ln_restaurant_details)
    LinearLayout lnRestaurantDetails;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private LocationManager locationManager;
    private GoogleMap mMap;
    private LocationListener mLocationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupMapView(savedInstanceState);

    }

    private void setupMapView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;
            setCurrentLocation();
        });

    }

    private void getRestaurantRequest(Double current_latitude, Double current_longitude) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        String location = current_latitude + "," + current_longitude;
        Call<RestaurantModel> call = apiService.getRestaurant(location);
        call.enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    RestaurantModel restaurantModel = response.body();

                    LatLng markerLatlng = new LatLng
                            (Double.valueOf(restaurantModel.getLatitude()), Double.valueOf(restaurantModel.getLongitude()));

                    mMap.addMarker(new MarkerOptions()
                            .position(markerLatlng)
                            .title(restaurantModel.getName()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLatlng,16));

                    //set TextViews Details
                    lnRestaurantDetails.setVisibility(View.VISIBLE);
                    tvRestaurantName.setText(restaurantModel.getName());
                    if (restaurantModel.getRating().equals("-1") || restaurantModel.getRating().equals("")) {
                        tvRateType.setText(restaurantModel.getCategory());
                    } else {
                        tvRateType.setText(restaurantModel.getRating() + "/10 - " + restaurantModel.getCategory());
                    }

                }
            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, getString(R.string.connectionerror), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        else {
            mMap.setMyLocationEnabled(true);

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    if (location != null) {
                        getRestaurantRequest(location.getLatitude(), location.getLongitude());
                        locationManager.removeUpdates(mLocationListener);
                    }
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

            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setCurrentLocation();
            }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
