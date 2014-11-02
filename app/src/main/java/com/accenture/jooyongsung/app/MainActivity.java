package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButton();
    }

    private void initButton() {
        TextView btnViewTest= (TextView) findViewById(R.id.btn_view_test);
        TextView btnNotiTest= (TextView) findViewById(R.id.btn_noti_test);
        TextView btngpsTest= (TextView) findViewById(R.id.btn_gps_test);
        TextView btnCameraTest= (TextView) findViewById(R.id.btn_camera_test);
        TextView btnSensorTest= (TextView) findViewById(R.id.btn_sensor_test);
        TextView btnDevInfo= (TextView) findViewById(R.id.btn_dev_info);
        btnViewTest.setOnClickListener(this);
        btnNotiTest.setOnClickListener(this);
        btngpsTest.setOnClickListener(this);
        btnCameraTest.setOnClickListener(this);
        btnSensorTest.setOnClickListener(this);
        btnDevInfo.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        showExitConfirmDialog();
    }

    private void showExitConfirmDialog() {
        String appName = getResources().getString(R.string.app_name);
        String msg = getResources().getString(R.string.msg_back);
        String btnYes = getResources().getString(R.string.btn_yes);
        String btnNo = getResources().getString(R.string.btn_no);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(appName);
        builder.setMessage(msg);
        builder.setNegativeButton(btnNo, null);
        builder.setPositiveButton(btnYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        goActivity(id);
    }

    private void goActivity(int id) {
        if (R.id.btn_view_test == id) {
            Intent intent = new Intent(this, SubViewActivity.class);
            startActivity(intent);
        } else if (R.id.btn_noti_test == id) {
            Intent intent = new Intent(this, SubNotiActivity.class);
            startActivity(intent);
        } else if (R.id.btn_gps_test == id) {
            Intent intent = new Intent(this, GoogleMapTestActivity.class);
            startActivity(intent);
        } else if (R.id.btn_camera_test == id) {
            Intent intent = new Intent(this, CameraTestActivity.class);
            startActivity(intent);
        } else if (R.id.btn_sensor_test == id) {
            Intent intent = new Intent(this, SensorTestActivity.class);
            startActivity(intent);
        } else if (R.id.btn_dev_info == id) {
            Intent intent = new Intent(this, DevInfoActivity.class);
            startActivity(intent);
        } else {
            throw new IllegalStateException();
        }


    }
}
