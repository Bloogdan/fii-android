package com.example.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshop.R;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mTermometer;
    private Sensor mGyroscope;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mTermometer = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        setContentView(R.layout.activity_sensor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTermometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            TextView tv = (TextView)findViewById(R.id.accelerometerView);
            tv.setText("Accelerometer: " + event.values[0] + " " + event.values[1] + " " + event.values[2]);
        }
        else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            TextView tv = (TextView)findViewById(R.id.temperatureView);
            tv.setText("Temperature: " + String.valueOf(event.values[0]));
        } else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            TextView tv = (TextView)findViewById(R.id.gyroscopeView);
            tv.setText("Gyroscope: " + event.values[0] + " " + event.values[1] + " " + event.values[2]);
        }
    }
}
