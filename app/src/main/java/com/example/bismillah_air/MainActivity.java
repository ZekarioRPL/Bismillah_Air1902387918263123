package com.example.bismillah_air;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


//    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawerLayout drawerlayout = findViewById(R.id.drawerlayout);



    }

//    @Override
//    protected void onStart() {
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkChangeListener, filter);
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        unregisterReceiver(networkChangeListener);
//        super.onStop();
//    }


}