package com.sds.study.googlemapsclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    Toolbar toolbar;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_navi,menu);
        return super.onCreateOptionsMenu(menu);
    }
    /*웹서버로부터 갱신된 데이터 가져오기!*/
    public void getPosition(){
        MapAsync async = new MapAsync(googleMap);
        async.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //액션 버튼을 누르면
        if(item.getItemId()==R.id.m_refresh){
            getPosition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;

        LatLng point = new LatLng(37.50169, 127.039650);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
    }
}
