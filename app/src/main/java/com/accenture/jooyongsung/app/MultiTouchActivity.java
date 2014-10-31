package com.accenture.jooyongsung.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class MultiTouchActivity extends Activity implements View.OnClickListener {
    private View infoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showMultiTouchInfo();
    }

    private void showMultiTouchInfo() {
        infoView = getLayoutInflater().inflate(R.layout.view_multitouch_info, null);
        infoView.setOnClickListener(this);
        setContentView(infoView);

    }

    @Override
    public void onClick(View view) {
        infoView.setOnClickListener(null);
        setContentView(new MultiTouchCustomView(this));
    }
}
