package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;


public class SensorTestActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView textX;
    private TextView textY;
    private TextView textZ;
    private TextView textLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);

        initTextView();
        showSensorList();
    }

    private void initTextView() {
        textX = (TextView) findViewById(R.id.text_x);
        textY = (TextView) findViewById(R.id.text_y);
        textZ = (TextView) findViewById(R.id.text_z);
        textLight = (TextView) findViewById(R.id.text_light);
    }

    private void showSensorList() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sensorList.size(); i++) {
            sb.append("센서 ").append(i).append("번 [");
            sb.append(sensorList.get(i).getVendor()).append("] ");
            sb.append(sensorList.get(i).getName()).append("\n");
        }

        String sensorString = sb.toString();
        TextView textSensorList = (TextView) findViewById(R.id.text_sensorlist);
        TextView textSensorCount = (TextView) findViewById(R.id.text_sensor_count);
        textSensorList.setText(sensorString);
        textSensorCount.append(String.valueOf(sensorList.size()));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (Sensor.TYPE_ACCELEROMETER  == sensorEvent.sensor.getType()) {
            showAccelerometer(sensorEvent);
        } else if (Sensor.TYPE_LIGHT  == sensorEvent.sensor.getType()) {
            showLightValue(sensorEvent);
        }
    }

    private void showLightValue(SensorEvent sensorEvent) {
        float lightValue = sensorEvent.values[0];
        textLight.setText(String.valueOf(lightValue));
    }

    private void showAccelerometer(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        textX.setText("x축 : " + x);
        textY.setText("y축 : " + y);
        textZ.setText("z축 : " + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.i("onAccuracyChanged", "onAccuracyChanged");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener or the orientation and
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
