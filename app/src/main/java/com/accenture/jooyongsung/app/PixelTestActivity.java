package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class PixelTestActivity extends Activity implements View.OnTouchListener {

    static int[] colorList = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.BLACK,
            Color.WHITE,
    };
    private int currentIndex = 0;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixel_test);

        tv = (TextView) findViewById(R.id.text_pixel);
        tv.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i("PixelActivity", "ACTION:" + motionEvent.getAction());
        clearText();
        view.setBackgroundColor(colorList[currentIndex++]);

        if (isLastColor()) {
            Log.i("PixelActivity", "isLastColor");
            this.finish();
        }

        return false;
    }

    private boolean isLastColor() {
        return currentIndex >= colorList.length;
    }

    private void clearText() {
        String text = tv.getText().toString();
        if (!text.isEmpty()) {
            tv.setText("");
        }
    }
}
