package com.example.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista, lista2;
    private String[] stringArray = {"Obiectul unu", "Obiectul doi"};
    private ArrayAdapter<String> arrayAdapter;

    private List<String> stringList;
    private ListAdapter listAdapter;

    private String filename = "my_file";

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        stringList = new ArrayList<String>();

        lista = (ListView) findViewById(R.id.lista1);
        lista2 = (ListView) findViewById(R.id.lista2);
        try {
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            StringBuilder stringBuilder = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    stringList.add(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            }
        } catch(FileNotFoundException e) {

        }

        for (String s : stringList) {
            Log.d("item", s);
        }

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        lista2.setAdapter(listAdapter);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray);
        lista.setAdapter(arrayAdapter);
        lista.setOnItemClickListener(messageClickedHandler);


        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast toast = Toast.makeText(getApplicationContext(), "Longitude: " + location.getLongitude() + "\nLatitude: " + location.getLatitude(), Toast.LENGTH_SHORT);
                toast.show();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("InstanceState change","List saved to bundle");
        outState.putStringArray("key", stringArray);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("InstanceState change","List imported from saved bundle");
        if(savedInstanceState.getStringArray("key") != null)
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, savedInstanceState.getStringArray("key"));

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private AdapterView.OnItemClickListener messageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Context context = getApplicationContext();
            String[] descriptionList = {"Descriere obiect unu", "Descriere obiect doi"};
            Toast toast = Toast.makeText(context, descriptionList[position], Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    public void changeToSettingsActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void changeToSensorActivity(MenuItem item) {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    public void openDialog(MenuItem item) {
        FireMissilesDialogFragment dialog = new FireMissilesDialogFragment();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    public void onSubmitClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_APPEND);
            String my_string = editText.getText().toString();
            fos.write((my_string + "\n").getBytes());
            Toast toast = Toast.makeText(getApplicationContext(), my_string + " added.", Toast.LENGTH_SHORT);
            toast.show();
        } catch (FileNotFoundException e) {

        } catch(IOException e) {

        }
    }

    public void showLocation(MenuItem item) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        }
    }
}
