package com.fbworldhack.whoscoming;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ActionSelectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_actionselection);
        
        String[] spinnerArray = new String[] {"Place", "Music", "Sport", "Games", "Food"};
        
        Spinner catSpinner = (Spinner) findViewById(R.id.categories);
        EditText searchBox = (EditText) findViewById(R.id.searchBox);
        TextView searchbutton = (TextView) findViewById(R.id.search_button);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerArray);
        catSpinner.setAdapter(adapter);
	}
}
