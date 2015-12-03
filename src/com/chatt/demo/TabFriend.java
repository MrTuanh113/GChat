package com.chatt.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.SimpleFormatter;

import Database.DBHandler;
import Database.User;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chatt.demo.utils.Const;
import com.chatt.demo.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class TabFriend extends Activity{
	ListView listview;
	SimpleAdapter adapter;
	ArrayList<HashMap<String,String>> array;
	public final static String NAME = "name";
	public static ParseUser user;
	public static Date create_At;
	SimpleDateFormat ab ;
	Date date;
	Handler handler;
	 ProgressDialog dia;
	 List<User> listuser;
	 List<User> mlistuser;
	 int k =0;
	 int m =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabfriend);
		showDialos();
		loadListView();
	
	
		
	}
	public void saveToFriendTable(String id, String name,String createAt){
		DBHandler db = new DBHandler(this);
		User user = new User(id,name,0,createAt);
		db.addFriend(user);
		db.close();
		
		
		
	}
	public void loadFriend(){
		array.removeAll(array);
		listuser = new ArrayList<User>();
		mlistuser =new ArrayList<User>();
		
		DBHandler db = new DBHandler(this);
		 listuser = db.getAllFriend();
		
		SimpleDateFormat d = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
		int i=0;
		
		for(User user : listuser){
			i++;
			if(user.getState()!=1){
			HashMap<String,String> temp = new HashMap<String,String>();
			
			mlistuser.add(user);
			try {
				 date = d.parse(user.getCreateAt());
				
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(date.after(create_At)){
				create_At=date;
			}
			
			temp.put(NAME, user.getUser_name());
//			Log.d("id la ",user.getUser_id());
//			Log.d("name", user.getUser_name());
//			Log.d("state", ""+user.getState());
//			Log.d("createAt", ""+user.getCreateAt());
			array.add(temp);
			
		}
		}
		Log.d("createAt lúc này là", ""+create_At);
		db.close();
		
	}
	public	boolean checkNullDatabase(){
		DBHandler db = new DBHandler(this);
		int a = db.getCountFriend();
		if(a==0){
			db.close();
			return true;
		}else{
			db.close();
			return false;
		}
		
		
	}
	public void loadListView(){
		m++;
		
		listview = (ListView) findViewById(R.id.listView1);
		array = new ArrayList<HashMap<String,String>>();
		handler = new Handler();
		Log.d("vào listview", "======********************************************"+m);
		String[] tags = {NAME};
		int [] ids = {R.id.tvName};
		create_At = new Date();
		
	//============================	
		ab = new SimpleDateFormat("yyyy-MM-dd");
		date = new Date();
		if(checkNullDatabase()){
			
		
		String input = "2014-11-10";
		try {
			create_At = ab.parse(input);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}else{
			loadFriend();
			
		}
		
//		Log.d("createAt", ""+create_At);
		
		
		
		
		try {
			List<ParseUser> li=ParseUser.getQuery().whereNotEqualTo("username", TabFriend.user.getUsername())
			.whereGreaterThan("createdAt", create_At)
			.orderByAscending("createdAt")
			.find();
			int i=0;
			for(ParseUser pu : li){
				i++;
				
				String id = pu.getObjectId();
				String name = pu.getUsername();
				String createAt = ""+pu.getCreatedAt();
				
				
				if(pu.getCreatedAt().after(create_At)){
					create_At=pu.getCreatedAt();
					
					saveToFriendTable(id, name, createAt);
					
				}
				
				

			}
			
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		loadFriend();
		adapter = new SimpleAdapter(this, array, R.layout.chat_item, tags, ids){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				final int a= position;
				View v= super.getView(position, convertView, parent);
				final Button AddFriend = (Button) v.findViewById(R.id.btAddFriend);
				final Button Chat = (Button) v.findViewById(R.id.btChat);
					AddFriend.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v){
										Animation shake = AnimationUtils.loadAnimation(TabFriend.this, R.anim.shake);
										AddFriend.startAnimation(shake);
										DBHandler db = new DBHandler(getApplicationContext());
										User m_user = mlistuser.get(a);
										int b = db.updateTrueUser_Friend(m_user);
										db.close();
										new MyAsynTask().execute(m_user);
										loadNew();
									}});
					Chat.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Animation shake = AnimationUtils.loadAnimation(TabFriend.this, R.anim.shake);
							Chat.startAnimation(shake);
							startActivity(new Intent(TabFriend.this,
									Chat1.class).putExtra(
									Const.EXTRA_DATA, mlistuser.get(a)
											.getUser_name()));
							
						}
					});
				
				return v;
			}
			
		};
	
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		dia.dismiss();
//	handler.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				k++;
//				loadListView();
//				Log.d("Vào lần thứ ", ""+k);
//			}
//		}, 5000);
	}
	
	
	private void sendSMS(String phonenumber, String text) {
		// TODO Auto-generated method stub
		Log.d("có vào gửi SMS","số điện thoại"+ phonenumber+" tin nhắn là "+ text);
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, TabFriend.class), 0);
		SmsManager sms = SmsManager.getDefault();
		
		sms.sendTextMessage(phonenumber, null, text, null, null);
	}
	public void loadNew(){
		loadFriend();
		adapter.notifyDataSetChanged();
		
	}
	public void showDialos(){
		dia = ProgressDialog.show(this, null,
				getString(R.string.alert_wait_load_user));
		
	}
	
@Override
	protected void onDestroy()
	{
		super.onDestroy();
		updateUserStatus(false);
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		loadNew();
	}
	private void updateUserStatus(boolean online)
	{
		user.put("online", online);
		user.saveEventually();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2 && resultCode == RESULT_OK)
			Toast.makeText(getApplicationContext(), "OK- intent2", Toast.LENGTH_SHORT).show();
			finish();}
	public class MyAsynTask extends AsyncTask<User, Void, Void>{

		@Override
		protected Void doInBackground(User... m_user) {
			// TODO Auto-generated method stub
			String id = user.getObjectId();
			String name = user.getUsername();
			
			String message ="{\"id\":"+id+",\"name\":"+name+"}";
			
			
		
			
			
			//=========
			try {
		
				String id1= m_user[0].getUser_id();
				List<ParseUser> list =ParseUser.getQuery().whereEqualTo("objectId", id1).find();
				ParseUser user2=list.get(0);
				String number = user2.getString("phonenumber");
				Log.d("phonenumber", number);
				if(number.equals("")){
//					Utils.showDialog(
//							TabFriend.this,
//							"Người dùng không có số điện thoại");
					
				}
				else{
					
					Log.d("vao day", "==============");
					sendSMS(number,message);
				}
				
				
//				Toast.makeText(getBaseContext(), "phonenumber ", Toast.LENGTH_SHORT).show();
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

	
}
}
