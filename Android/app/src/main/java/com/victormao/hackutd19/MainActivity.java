package com.victormao.hackutd19;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStructure;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.MenuItem;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(myIntent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        if (intent != null)
        {
            String message = intent.getStringExtra("barcode");
            if (message != null)
            {
                textView.setText("Successfully checked in at " + message);
                textView.setVisibility(View.VISIBLE);
            }
            else {
                textView.setVisibility(View.INVISIBLE);
            }
        }

        // populate listview
        StableArrayAdapter row;

        List<String> name = Arrays.asList("Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 1", "Table 2", "Table 3", "Table 4", "Table 5");
        List<String> availability = Arrays.asList("Available", "Available", "Unvailable", "Unavailable", "Available", "Available", "Available", "Unvailable", "Unavailable", "Available", "Available", "Available", "Unvailable", "Unavailable", "Available");
        List<String> location = Arrays.asList("ECSW Floor 1", "ECSW Floor 2", "ECSW Floor  3", "ECSW Floor  4", "ECSW Floor  5", "ECSW Floor 1", "ECSW Floor 2", "ECSW Floor  3", "ECSW Floor  4", "ECSW Floor  5", "ECSW Floor 1", "ECSW Floor 2", "ECSW Floor  3", "ECSW Floor  4", "ECSW Floor  5");
        List<String> details = Arrays.asList("High Chair", "No outlet", "Noisy", "Natural Light", "Standing Desk", "High Chair", "No outlet", "Noisy", "Natural Light", "Standing Desk", "High Chair", "No outlet", "Noisy", "Natural Light", "Standing Desk");

        // create StableArrayAdapter and populate ListView with stock data
        // create StableArrayAdapter and populate ListView with stock data
        row = new StableArrayAdapter(getApplicationContext(), name, availability, location, details);
        ((ListView)findViewById(R.id.rooms_list)).setAdapter(row);


        //Snackbar.make(findViewById(R.id.main_coordinating_layout), barcode.displayValue, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void refresh(MenuItem item) {
    }


    }
