package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class SubViewActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_view);

        initButton();
    }

    private void initButton() {
        TextView btnPixelTest= (TextView) findViewById(R.id.btn_pixel_test);
        TextView btnMultiTouchTest= (TextView) findViewById(R.id.btn_multi_touch_test);
        btnPixelTest.setOnClickListener(this);
        btnMultiTouchTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        goActivity(id);
    }

    private void goActivity(int id) {
        Intent intent = null;

        if (R.id.btn_pixel_test == id) {
            intent = new Intent(this, PixelTestActivity.class);
            startActivity(intent);
        } else if (R.id.btn_multi_touch_test == id) {
            intent = new Intent(this, MultiTouchActivity.class);
            startActivity(intent);
        } else {
            throw new IllegalStateException();
        }
    }
}
