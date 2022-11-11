package com.example.bismillah_air;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bismillah_air.Utility.NetworkChangeListener;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class WifiActivity extends AppCompatActivity {

    public static final String DEVICE_EXTRA = "com.example.bismillah_motor_listrik.SOCKET";
    public static final String DEVICE_UUID = "com.example.bismillah_motor_listrik.uuid";
    private static final String DEVICE_LIST = "com.example.bismillah_motor_listrik.devicelist";
    private static final String DEVICE_LIST_SELECTED = "com.example.bismillah_motor_listrik.devicelistselected";
    public static final String BUFFER_SIZE = "com.example.bismillah_motor_listrik.buffersize";
    private static final String TAG = "BlueTest5-MainActivity";
    private BluetoothAdapter mBTAdapter;
    private static final int BT_ENABLE_REQUEST = 10; // This is the code we use for BT Enable
    private static final int SETTINGS = 20;
    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private int mBufferSize = 50000; //Default
    private ReadInput mReadThread = null;
    private EditText WifiName, WifiPass;

    private View decorView;

    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    Button connectButton;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    //    private static final String TAG = "BlueTest5-Controlling";
    private int mMaxChars = 50000;//Default//change this to string..........
    private BluetoothSocket mBTSocket;
//    private ReadInput mReadThread = null;

    private boolean mIsUserInitiatedDisconnect = false;
    private boolean mIsBluetoothConnected = false;


    private Button mBtnDisconnect;
    private BluetoothDevice mDevice;

    final static String on = "92";//on
    final static String off = "79";//off

    String key_device, key_mBuffer, key_mDevice;
    private ProgressDialog progressDialog;

    TextView percobaan;


    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        connectButton = (Button) findViewById(R.id.connect);

        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WifiSend();
                finish();
            }
        });


    }

    private class ReadInput implements Runnable {

        private boolean bStop = false;
        private Thread t;

        public ReadInput() {
            t = new Thread(this, "Input Thread");
            t.start();
        }

        public boolean isRunning() {
            return t.isAlive();
        }

        @Override
        public void run() {
            InputStream inputStream;

            try {
                inputStream = mBTSocket.getInputStream();

                inputStream = mBTSocket.getInputStream();
                while (!bStop) {
                    byte[] buffer = new byte[256];
                    if (inputStream.available() > 0) {
                        inputStream.read(buffer);
                        int i = 0;

                        /*
                         * This is needed because new String(buffer) is taking the entire buffer i.e. 256 chars on Android 2.3.4 http://stackoverflow.com/a/8843462/1287554
                         */
                        for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                        }
                        final String strInput = new String(buffer, 0, i);

                        /*
                         * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
                         */


                    }
                    Thread.sleep(500);
                }
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void stop() {
            bStop = true;
        }

    }

    private class DisConnectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {//cant inderstand these dotss

            if (mReadThread != null) {
                mReadThread.stop();
                while (mReadThread.isRunning())
                    ; // Wait until it stops
                mReadThread = null;

            }

            try {
                mBTSocket.close();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            if (mIsUserInitiatedDisconnect) {
                finish();
            }
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (mBTSocket != null && mIsBluetoothConnected) {
            new DisConnectBT().execute();
        }
        Log.d(TAG, "Paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBTSocket == null || !mIsBluetoothConnected) {
            new ConnectBT().execute();
        }
        Log.d(TAG, "Resumed");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopped");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(WifiActivity.this, "Hold On", "Connecting To Device...");// http://stackoverflow.com/a/11130220/1287554

        }

        @SuppressLint("MissingPermission")
        @Override
        protected Void doInBackground(Void... devices) {

            try {
                if (mBTSocket == null || !mIsBluetoothConnected) {
                    mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBTSocket.connect();
                }
            } catch (IOException e) {
// Unable to connect to device`
                // e.printStackTrace();
                mConnectSuccessful = false;


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!mConnectSuccessful) {
                Toast.makeText(getApplicationContext(), "Tidak Bisa Terkoneksi Dengan Motor. Silahkan Pastikan Motor Online!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                msg("Connected to device");
                mIsBluetoothConnected = true;
                mReadThread = new ReadInput(); // Kick off input reader
            }

            progressDialog.dismiss();
        }


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void WifiSend() {

        ByteArrayOutputStream stream
                = new ByteArrayOutputStream();


        WifiName = (EditText) findViewById(R.id.ET_id);
        WifiPass = (EditText) findViewById(R.id.ET_Pass);

        String name = WifiName.getText().toString();
        String pass = WifiPass.getText().toString();

        String combine = name + ";" + pass;
//         writing the specified byte to the output stream
        try {
            mBTSocket.getOutputStream().write(combine.getBytes());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        converting stream to byte array
//        and typecasting into string
        String finalString
                = new String(stream.toByteArray());

        // printing the final string
        System.out.println(finalString);

    }
}