package com.chatt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DBHandler;
import Database.User;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TabRequired extends Activity{	
	List<User> listrequired;
	ListView listview;
	ArrayList<HashMap<String,String>> array;
	public final static String NAME = "name";
	SimpleAdapter adapter;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabrequired);
		listview = (ListView) findViewById(R.id.lvRequired2);
		listrequired = new ArrayList<User>();
		array = new ArrayList<HashMap<String,String>>();
		loadDatatoArrayList(loadUserFromDatabase());
		createAdapter();
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		handler = new Handler();
		refreshData();
		
	}
	public void refreshData(){
		loadDatatoArrayList(loadUserFromDatabase());
		adapter.notifyDataSetChanged();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				refreshData();
			}
		}, 1000);
	}
	// lấy dữ liệu từ bảng Friend về
	public List<User> loadUserFromDatabase(){
		listrequired.removeAll(listrequired);
		DBHandler db = new DBHandler(this);
		List<User> listuser = new ArrayList<User>(db.getAllFriend());
		for(User user :listuser){
			if(user.getState()==2){
				listrequired.add(user);
			}
		}
		db.close();
		return listrequired;
	}
	// load dữ liệu vào arraylist kiểu hashmap
	public void loadDatatoArrayList(List<User> listrequired){
		array.removeAll(array);
		for(User user:listrequired){
			HashMap<String,String> temp = new HashMap<String,String>();
			String name = user.getUser_name();
			temp.put(NAME, name);
			array.add(temp);}}
	// truyền arraylist vào adapter
	public void createAdapter(){
		String [] tags ={NAME};
		int [] ids = {R.id.tvNamerq};
		 // set click cho button trong listview
		adapter = new SimpleAdapter(this,array,R.layout.required_list_item,tags,ids){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				final int a= position;
				View v= super.getView(position, convertView, parent);
				final Button Agree = (Button) v.findViewById(R.id.btAgree);
				final Button Refuse = (Button) v.findViewById(R.id.btRefuse);
				Agree.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Animation shake = AnimationUtils.loadAnimation(TabRequired.this, R.anim.shake);
						Agree.startAnimation(shake);
						DBHandler db = new DBHandler(getApplicationContext());
						User r_user = listrequired.get(a);
						db.updateTrueUser_Friend(r_user);
						db.close();
						refreshData();
						
					}
				});
				Refuse.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Animation shake = AnimationUtils.loadAnimation(TabRequired.this, R.anim.shake);
						Refuse.startAnimation(shake);
						DBHandler db = new DBHandler(getApplicationContext());
						User r_user = listrequired.get(a);
						db.updateFalseUser_Friend(r_user);
						db.close();
						refreshData();
					}
				});
				return v;
			}
			
		};
	}
	
}
