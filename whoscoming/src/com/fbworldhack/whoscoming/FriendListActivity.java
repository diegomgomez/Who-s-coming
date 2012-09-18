package com.fbworldhack.whoscoming;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_friendlist);
        
        Bundle bundle = this.getIntent().getExtras();
        String[] data = (String[]) bundle.get("data");
        
        FriendAdapter adapter = new FriendAdapter();
        adapter.setData(data);
        
        for(int i = 0; i < data.length; i++) {
        	System.out.println(data[i]);
        }
        
        GridView grid = (GridView) findViewById(R.id.friendgrid);
        grid.setAdapter(adapter);
	}
	
	private class FriendAdapter extends BaseAdapter {
		private String[] data;
		
		public void setData(String[] data) {
			this.data = data;
		}
		
		public int getCount() {
			return data.length / 4;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) FriendListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.friend_image, null);
			
			ImageView iv = (ImageView) ll.findViewById(R.id.friendImage);
			
			new AsyncImageLoader(iv).execute(data[position * 4 + 3]);
			
			TextView tv = (TextView) ll.findViewById(R.id.friendName);
			tv.setText(data[position * 4 + 1] + " " + data[position * 4 + 2]);
			
			return ll;
		}
		
	}
}
