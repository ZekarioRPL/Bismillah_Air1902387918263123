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
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bismillah_air.API.InterfaceAPI;
import com.example.bismillah_air.API.Respon;
import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    TextView setelah_filter, sebelum_filter;
    public Handler handler;
    public Runnable runnable;
    ProgressBar pg1, pg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        pg1 = findViewById(R.id.pg1);
//        pg2 = findViewById(R.id.pg2);


        final DrawerLayout drawerlayout = findViewById(R.id.drawerlayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });

//        ChangeActivity
        t = new ActionBarDrawerToggle(this, drawerlayout, R.string.app_name, R.string.app_name);
        t.syncState();

        nv = (NavigationView)findViewById(R.id.NavigationView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {

                    case R.id.home:
                        return true;
                    case R.id.information:

                        Handler handler1 = new Handler();
                        Runnable runnable1 = new Runnable() {
                            public void run() {
                                Intent mtintent = new Intent(MainActivity.this, InformationActivity.class);
                                startActivity(mtintent);
                            }
                        };
                        handler1.postDelayed(runnable1, 1000);
                        return true;
                    case R.id.history:

                        Handler handler2 = new Handler();
                        Runnable runnable2 = new Runnable() {
                            public void run() {
                                Intent mtintent1 = new Intent(MainActivity.this, HistoryActivity.class);
                                startActivity(mtintent1);
                            }
                        };
                        handler2.postDelayed(runnable2, 1000);
                        return true;
                    default:
                        return true;
                }




            }

        });

        setelah_filter = findViewById(R.id.setelah_filter);
        sebelum_filter = findViewById(R.id.sebelum_filter);


        st_filter();
        loop();

    }

    private void loop() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                st_filter();

                loop();

            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void st_filter() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<Respon> call = api.rp("4");

        call.enqueue(new Callback<Respon>() {
            @Override
            public void onResponse(Call<Respon> call, Response<Respon> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (response.body()!= null) {
//                    pg1.setVisibility(View.INVISIBLE);
//                    pg2.setVisibility(View.INVISIBLE);
//                    setelah_filter.setVisibility(View.VISIBLE);
//                    sebelum_filter.setVisibility(View.VISIBLE);

                    if (response.body().getData().getDebuAfterFilter() != null) {
                        setelah_filter.setText(response.body().getData().getDebuAfterFilter().toString());
                        sebelum_filter.setText(response.body().getData().getDebuBeforeFilter().toString());
                    }

                }
                return;


            }

            @Override
            public void onFailure(Call<Respon> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
//        handler.removeCallbacks(runnable);
        super.onStop();
    }


}