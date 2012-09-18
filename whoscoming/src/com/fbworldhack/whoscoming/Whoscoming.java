package com.fbworldhack.whoscoming;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Whoscoming extends Activity {

	Facebook facebook = new Facebook("144376159040730");
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_whoscoming);
        
        ImageView loginButton = (ImageView) findViewById(R.id.main_login);
        loginButton.setClickable(true);
        loginButton.setOnClickListener(loginClicked);
    }
    
    private OnClickListener loginClicked = new OnClickListener() {
		public void onClick(View v) {
			facebook.authorize(Whoscoming.this, new DialogListener() {

				public void onComplete(Bundle values) {
					Intent myIntent = new Intent(Whoscoming.this, ActionSelectionActivity.class);
	                startActivityForResult(myIntent, 0);
				}

				public void onFacebookError(FacebookError e) {
					onLoginError();
				}

				public void onError(DialogError e) {
					onLoginError();
				}

				public void onCancel() {
					onLoginError();
				}
	        	
	        });
		}
	};
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	facebook.authorizeCallback(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_whoscoming, menu);
        return true;
    }
    
    private void onLoginError() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("There has been an error. Please try again later")
    	       .setCancelable(false)
    	       .setNegativeButton("ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
}
