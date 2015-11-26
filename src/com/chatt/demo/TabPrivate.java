package com.chatt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DBHandler;
import Database.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.chatt.demo.utils.Const;
import com.parse.ParseUser;

public class TabPrivate extends Activity {
	 ListView list;
	 ArrayList<ParseUser> listuser;
		ArrayList<HashMap<String,String>> array;
		SimpleAdapter adapter;
		public static ParseUser user;
		public final static String TEXT ="texview";
		public final static String BUTTON ="button";
		Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabprivate);
		list = (ListView) findViewById(R.id.list_friend1);
		array = new ArrayList<HashMap<String,String>>();
		handler = new Handler();
		createList();
	}
	public void createList(){
		array.removeAll(array);
		DBHandler db = new DBHandler(this);
		List<User> user_list = db.getAllFriend();
		for(User user:user_list){
			String name = user.getUser_name();
			loadData(name);
			
		}
		String[] tags ={TEXT};
		int [] ids = {R.id.tvNameFr};
		adapter = new SimpleAdapter(this,array,R.layout.chat_friend_item,tags,ids){
			@Override
			public View getView(int position, View convertView, android.view.ViewGroup parent){
//				Toast.makeText(getApplicationContext(), "Position la "+position, Toast.LENGTH_SHORT).show();
				final int a= position;
				
				
				
				View v= super.getView(position, convertView, parent);
				Button btagree = (Button) v.findViewById(R.id.btRemoveFriend);
				Button btrefuse = (Button) v.findViewById(R.id.btChatFriend);
				btagree.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						User user = getFriend(a);
						deleteFriend(user);
						createList();
						
					}
				});
			
				btrefuse.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						User user = getFriend(a);
						String name = user.getUser_name();
						startActivity(new Intent(TabPrivate.this,
								Chat1.class).putExtra(
								Const.EXTRA_DATA, name));						
						
					}
				});
				
				return v;
			}
		};
		
		list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				createList();
			}
		}, 1000);
	}
	private void loadData(String name) {
		// TODO Auto-generated method stub
		
		HashMap temp = new HashMap();
		temp.put(TEXT, name);
		array.add(temp);
		
	}
	public void deleteFriend(User ur){
		DBHandler db = new DBHandler(this);
		db.deleteFriend(ur);
	
		
	}
public User getFriend(int pos){
		
		DBHandler db = new DBHandler(this);
		User ur = db.getFriendbyPos(pos);
		return ur;
	
	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity__private, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
