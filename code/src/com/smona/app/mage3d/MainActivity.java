package com.smona.app.mage3d;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableTitleBar();
        setContentView(new MoneyView(this, null, true));
    }

    private void disableTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
