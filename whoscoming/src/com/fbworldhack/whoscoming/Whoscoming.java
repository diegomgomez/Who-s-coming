package com.fbworldhack.whoscoming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

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
        
        ImageButton loginButton = (ImageButton) findViewById(R.id.main_login);
        loginButton.setOnClickListener(loginClicked);
    }
    
    private OnClickListener loginClicked = new OnClickListener() {
		public void onClick(View v) {
			facebook.authorize(Whoscoming.this, new DialogListener() {

				public void onComplete(Bundle values) {
					System.out.println("onComplete");
				}

				public void onFacebookError(FacebookError e) {
					System.out.println("onFacebookError");
				}

				public void onError(DialogError e) {
					System.out.println("dielogerror " + e);
				}

				public void onCancel() {
					System.out.println("onCancel");
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
}
