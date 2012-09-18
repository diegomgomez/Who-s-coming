package com.fbworldhack.whoscoming;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap>{
	private ImageView iv;
	
	public AsyncImageLoader(ImageView iv) {
		this.iv = iv;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		if(params == null || params.length < 1) return null;
	
		try {
			return BitmapFactory.decodeStream(new URL(params[0]).openConnection().getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if(iv != null) {
			iv.setImageBitmap(result);
		}
	}
}


