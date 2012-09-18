package com.fbworldhack.whoscoming;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Whoscoming extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whoscoming);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_whoscoming, menu);
        return true;
    }
}
