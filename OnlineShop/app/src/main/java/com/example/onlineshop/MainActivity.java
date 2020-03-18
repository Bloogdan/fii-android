package com.example.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private String[] stringList = {"Obiectul unu", "Obiectul doi"};
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista1);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        lista.setAdapter(arrayAdapter);
        lista.setOnItemClickListener(messageClickedHandler);
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
        outState.putStringArray("key", stringList);

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

    public void openDialog(MenuItem item) {
        FireMissilesDialogFragment dialog = new FireMissilesDialogFragment();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

}
