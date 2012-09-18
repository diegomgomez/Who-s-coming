package com.fbworldhack.whoscoming;

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

import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
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
//						String query = "SELECT uid, first_name, middle_name, last_name, pic_square, pic_small FROM user WHERE uid in (SELECT uid FROM page_fan WHERE page_id in (SELECT page_id FROM page WHERE type='" + spinnerCatArray[typeID].toLowerCase() + "' and name ='" + text +"') AND uid IN (SELECT uid2 FROM friend WHERE uid1 = me()))";
						String query = "SELECT uid, first_name, middle_name, last_name, pic_square, pic_small FROM user WHERE uid in (SELECT uid FROM page_fan WHERE page_id in (SELECT page_id FROM page WHERE type='" + spinnerCatArray[typeID].toLowerCase() + "' and name ='" + text +"') AND uid IN (SELECT uid2 FROM friend WHERE uid1 = me()))";
						
						Bundle params = new Bundle();
				        params.putString("method", "fql.query");
				        params.putString("query", query);
				        
				        StringBuffer uidList = new StringBuffer();
				        
				        String response;
						try {
							response = Whoscoming.facebook.request(params);
					        response = "{\"data\":" + response + "}";

					        System.out.println("response: " + response);
					        JSONObject json = Util.parseJson(response);
					        JSONArray data = json.getJSONArray("data");
					    
					        String[] sendData = new String[data.length() * 4];
					        for ( int i = 0, size = data.length(); i < size; i++ ){
					        	JSONObject object = data.getJSONObject(i);
					        	String uid = object.getString("uid");
					        	String name = object.getString("first_name");
					        	String lastName = object.getString("last_name");
					        	String picSquare = object.getString("pic_square");
					        	
					        	sendData[i * 4 + 0] = uid;
					        	sendData[i * 4 + 1] = name;
					        	sendData[i * 4 + 2] = lastName;
					        	sendData[i * 4 + 3] = picSquare;
					        	
					        	if(i != 0) uidList.append(",");
					        	uidList.append(uid);
					        	
					        	System.out.println("object: " + i+ " :: " + object);
					        }
					        
					        Bundle rparams = new Bundle();
//					        rparams.putString("to", uidList.toString());
					        rparams.putString("message", "invite friends");
					        
					        Whoscoming.facebook.dialog(ActionSelectionActivity.this, "apprequests", rparams, new DialogListener() {

								public void onComplete(Bundle values) {
									System.out.println("oncomplete");
								}

								public void onFacebookError(FacebookError e) {
									System.out.println("fb error");
									System.out.println("error: " + e);
									e.printStackTrace();
								}

								public void onError(DialogError e) {
									System.out.println("dialog error");
									System.out.println("error: " + e);
									e.printStackTrace();
								}

								public void onCancel() {
									System.out.println("cancel");
								}
					        });
					        
//					        Intent myIntent = new Intent(ActionSelectionActivity.this, FriendListActivity.class);
//					        myIntent.putExtra("data", sendData);
//			                startActivityForResult(myIntent, 0);
			                
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
