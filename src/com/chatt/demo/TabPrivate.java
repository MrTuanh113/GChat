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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chatt.demo.utils.Const;
import com.parse.ParseUser;

public class TabPrivate extends Activity {
	ListView listview;
	ArrayList<HashMap<String,String>> array;
	SimpleAdapter adapter;
	public final static String NAME ="name";
	Handler handler;
	List<User>listfriend;
	int i=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabprivate);
		
		listview = (ListView) findViewById(R.id.list_friend1);
		
		handler = new Handler();
		
		
		refreshData();
		
		
		
		addArrayToAdapter();
		
	}
	public void refreshData(){
	
		array = new ArrayList<HashMap<String,String>>();
		
		
		for(User user:loadFromDatabase()){
		
			loadToArray(user.getUser_name());
			
		}
		
		
		
	}
	
	// load dữ liệu từ database và lọc kết quả
	public List<User> loadFromDatabase(){
		
		listfriend= new ArrayList<User>();
		
		
		DBHandler db = new DBHandler(this);
		
		List<User> listuser = db.getAllFriend();
		
		for(User user: listuser){
			if(user.getState()==1){
		
			
		
			listfriend.add(user);
			
			}
			
		}
		db.close();
		
		return listfriend;
		
	}
	// truyền dữ liệu vào arraylist, thống số truyền vào là name
	public void loadToArray(String name){
	
		HashMap<String,String>temp = new HashMap<String, String>();
		temp.put(NAME, name);
		
		array.add(temp);
		
		
	}
	public void addArrayToAdapter(){
		
		String[] tags ={NAME};
		int [] ids = {R.id.tvNameFr};
		

		adapter = new SimpleAdapter(this,array,R.layout.chat_friend_item,tags,ids){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				final int a= position;
				View v= super.getView(position, convertView, parent);
				Button RemoveFriend = (Button) v.findViewById(R.id.btRemoveFriend);
				Button Chat = (Button) v.findViewById(R.id.btChatFriend);
				RemoveFriend.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DBHandler db = new DBHandler(getApplicationContext());
						User user = listfriend.get(a);
						int b = db.updateFalseUser_Friend(user);
						db.close();
						abc();
					}
				});
				Chat.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(TabPrivate.this,
								Chat1.class).putExtra(
								Const.EXTRA_DATA, listfriend.get(a)
										.getUser_name()));
					}
				});
				return v;
				
			}
			
		};
		
		listview.setAdapter(adapter);
		
		adapter.notifyDataSetChanged();
		
	}
	public void abc(){
		refreshData();
		addArrayToAdapter();
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshData();
		addArrayToAdapter();
				
//				adapter.notifyDataSetChanged();
		
	}
}
