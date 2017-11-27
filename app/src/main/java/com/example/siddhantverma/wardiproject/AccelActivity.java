package com.example.siddhantverma.wardiproject;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class AccelActivity extends AppCompatActivity

        implements OnMapReadyCallback


{
    static final int REQUEST_LOCATION = 1;
    TextView textView;
    BarChart barChart;
    Button pollButton;
    Button serviceB;
    LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    private BroadcastReceiver broadcastRec;

    Location location;
    double longitude;
    double latitude;

    @Override
    protected void onResume()
    {
        super.onResume();
        if(broadcastRec==null)
        {
            broadcastRec=new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Intent i=getIntent();
                    String coord=i.getStringExtra("coordinates");

                    textView.setText(coord);

                }
            };

        }
        registerReceiver(broadcastRec, new IntentFilter("location_update"));
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(broadcastRec!=null)
        {
            unregisterReceiver(broadcastRec);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_accel);

        if(!getLocation())
        {
            enableButton();
        }

        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        //

        textView=(TextView) findViewById(R.id.coordinates);

        pollButton = (Button) findViewById(R.id.Pollution);
        pollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"click hua",Toast.LENGTH_SHORT).show();
                String s = "bhaggg";
                //Log.v(s);
                Intent i = new Intent(AccelActivity.this, PollActivity.class);
                startActivity(i);

            }
        });
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //graph
        barChart = (BarChart) findViewById(R.id.bargraph);

        barChart.setDrawBarShadow(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 0));
        barEntries.add(new BarEntry(1, 0));
        barEntries.add(new BarEntry(2, 0));
        barEntries.add(new BarEntry(3, 0));
        barEntries.add(new BarEntry(4, 0));
        barEntries.add(new BarEntry(5, 0));
        barEntries.add(new BarEntry(6, 34));
        barEntries.add(new BarEntry(7, 14));
        barEntries.add(new BarEntry(8, 45));
        barEntries.add(new BarEntry(9, 10));
        barEntries.add(new BarEntry(10, 24));
        barEntries.add(new BarEntry(11, 40));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Data Set 1");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.8f);
        barChart.setData(data);

        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyAxis(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


    }

    private void enableButton() {
        serviceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"click hua",Toast.LENGTH_SHORT).show();


                Intent i=new Intent(getApplicationContext(),MyService.class);
                startService(i);

            }
        });
        
    }


    private boolean getLocation() {
        if (Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;


        } else if (locationManager != null) {

            //  Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("long:", String.valueOf(longitude));
                    Log.d("lat:", String.valueOf(latitude));


                }
                return false;

            }


        return true;

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enableButton();
            }else {
                getLocation();
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case REQUEST_LOCATION:
//                getLocation();
//                break;
//        }
//    }
        public class MyAxis implements IAxisValueFormatter
        {
            private String[] mval;
            public MyAxis(String[] values)
            {
                this.mval=values;
            }
            @Override
            public String getFormattedValue(float value, AxisBase axisBase)
            {
                return mval[(int)value];

            }
        }

        @Override
        public void onMapReady (GoogleMap googleMap){
            GoogleMap mMap=googleMap;
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
            
        LatLng sydney = new LatLng(latitude,longitude);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Pothole detected"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            MapStyleOptions styleOptions=MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style);
            mMap.setMapStyle(styleOptions);
    }
    }


