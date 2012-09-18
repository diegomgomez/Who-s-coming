package com.fbworldhack.whoscoming;

import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.android.Util;

public class ActionSelectionActivity extends Activity {
	private EditText searchBox;
	private Spinner catSpinner;
	private static final String[] spinnerCatArray = new String[] {"CITY", "MOVIE", "MUSICIAN/BAND", "SPORT", "GAMES/TOYS", "FOOD/BEVERAGES"};
	private static final String[] spinnerArray = new String[] {"Place", "Music", "Sport", "Games", "Food"};
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		handler = new Handler();
		
        setContentView(R.layout.activity_actionselection);
       
        catSpinner = (Spinner) findViewById(R.id.categories);
        
        searchBox = (EditText) findViewById(R.id.searchBox);
        TextView searchbutton = (TextView) findViewById(R.id.search_button);
        
        searchbutton.setClickable(true);
        searchbutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String text = "Adele";	//searchBox.getText().toString();
				final int typeID = 2;	//catSpinner.getSelectedItemPosition();
				
				System.out.println("text: " + text);
				System.out.println("typeID: " + typeID);
				
				handler.post(new Runnable() {
					public void run() {
						String query = "SELECT uid, first_name, middle_name, last_name FROM user WHERE uid in (SELECT uid FROM page_fan WHERE page_id in (SELECT page_id FROM page WHERE type='" + spinnerCatArray[typeID].toLowerCase() + "' and name ='" + text +"') AND uid IN (SELECT uid2 FROM friend WHERE uid1 = me()))";
						
						System.out.println("query: " + query);
						
						Bundle params = new Bundle();
				        params.putString("method", "fql.query");
				        params.putString("query", query);
				        
				        String response;
						try {
							response = Whoscoming.facebook.request(params);
					        response = "{\"data\":" + response + "}";

					        System.out.println("response: " + response);
					        JSONObject json = Util.parseJson(response);
					        JSONArray data = json.getJSONArray("data");
					        
					        for ( int i = 0, size = data.length(); i < size; i++ ){
					        	JSONObject object = data.getJSONObject(i);
					        	
					        	System.out.println("object: " + i+ " :: " + object);
					        }
					        
						} catch (Exception e) {
							Whoscoming.onLoginError(ActionSelectionActivity.this);
							e.printStackTrace();
						}
					}
				});
			}
		});
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerArray);
        catSpinner.setAdapter(adapter);
	}
}
