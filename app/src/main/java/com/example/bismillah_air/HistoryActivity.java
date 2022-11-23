package com.example.bismillah_air;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bismillah_air.API.History;
import com.example.bismillah_air.API.InterfaceAPI;
import com.example.bismillah_air.API.Respon;
import com.example.bismillah_air.Adapter.HistoryAdapter;
import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryActivity extends AppCompatActivity {

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private RecyclerView recyclerView;
    ProgressBar pg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pg1 = findViewById(R.id.pg1);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        history();
        sidebar();
    }

    private void history() {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterfaceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<List<History>> call = api.getHistory();

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HistoryActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(HistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                if (response.body()!= null) {
                    pg1.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    List<History> postList = response.body();
                    HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this,postList);
                    recyclerView.setAdapter(historyAdapter);
//                }
//                return;
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sidebar() {
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
                        Intent mtintent = new Intent(HistoryActivity.this, MainActivity.class);
                        startActivity(mtintent);
                        return true;
                    case R.id.information:
                        Intent mtintent1 = new Intent(HistoryActivity.this, InformationActivity.class);
                        startActivity(mtintent1);
                        return true;
                    case R.id.history:
                        return true;
                    default:
                        return true;
                }




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