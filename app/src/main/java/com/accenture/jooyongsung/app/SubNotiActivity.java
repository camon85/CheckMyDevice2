package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;


public class SubNotiActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_noti);

        initButton();
    }

    private void initButton() {
        TextView btnSoundTest= (TextView) findViewById(R.id.btn_sound_test);
        TextView btnVibrationTest= (TextView) findViewById(R.id.btn_vibrate_test);
        btnSoundTest.setOnClickListener(this);
        btnVibrationTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        goActivity(id);
    }

    private void goActivity(int id) {
        if (R.id.btn_sound_test == id) {
            Intent intent = new Intent(this, SoundTestActivity.class);
            startActivity(intent);
        } else if (R.id.btn_vibrate_test == id) {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(1000);
        } else {
            throw new IllegalStateException();
        }
    }
}
