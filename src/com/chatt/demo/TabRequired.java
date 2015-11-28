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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TabRequired extends Activity{	
	ListView listview ;
	ArrayList<HashMap<String,String>> array;
	SimpleAdapter adapter;
	public final static String NAME ="name";
	public static int index;
	private NotificationManager mNoti;
	private int notiID=100;
	private int numMessages =0;
	Handler hander;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabrequired);
		setResult(RESULT_OK);
		listview = (ListView) findViewById(R.id.lvRequired2);
		array = new ArrayList<HashMap<String,String>>();
		hander = new Handler();
		
		createList();
		
	}
	
//	public void noticle(){
//		DBHandler db = new DBHandler(this);
//		
//		int count = db.getCount();
//		
//		if(TabRequired.index==count){
//			if(TabRequired.index >= 10){
//				array.removeAll(array);
//				adapter.notifyDataSetChanged();
//			}
//		}else{
//			
//			notifitycation();
//			
//			TabRequired.index=count;
//		}
//		
//		
//	}
	public void createList(){
		//================================
		DBHandler db = new DBHandler(this);
				
				array.removeAll(array);
				// phần này là lấy dữ liệu từ cơ sở dữ liệu ra
				
//				db.add(new User("tbm111","Tu Anh "));
//				db.add(new User("tbm112","Ngoc Son"));
//				db.add(new User("tbm113","Tuan Phuc"));
				//=================================
				List<User> user_list = db.getAllRequired();
				for(User user : user_list){
					String name= user.getUser_name();
					// phần này là lấy dữ liệu từ mảng userlist cho vào biến HashMap
					loadData(name);
					
					
					
				}
				
				//==================================
				String[] tags ={NAME};
				int [] ids = {R.id.tvNamerq};
//				Toast.makeText(getApplicationContext(), "Khởi tạo adapter", Toast.LENGTH_SHORT).show();
				adapter = new SimpleAdapter(this,array,R.layout.required_list_item,tags,ids){
					@Override
					public View getView(int position, View convertView, android.view.ViewGroup parent){
//						Toast.makeText(getApplicationContext(), "Position la "+position, Toast.LENGTH_SHORT).show();
						final int a= position;
						
						final Intent intent = new Intent(TabRequired.this,TabRequired.class);
						
						View v= super.getView(position, convertView, parent);
						Button btagree = (Button) v.findViewById(R.id.btAgree);
						Button btrefuse = (Button) v.findViewById(R.id.btRefuse);
						btagree.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
//								Toast.makeText(getApplicationContext(), "Vào button đồng ý", Toast.LENGTH_SHORT).show();
								User ur = getUser(a);
								addFriend(ur);
								deleteRequired(ur);
								createList();
//								loadData();
//								startActivityForResult(intent, 1);
//								setResult(RESULT_OK);
							}
						});
					
						btrefuse.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
//								Toast.makeText(getApplicationContext(), "Vào button từ chối", Toast.LENGTH_SHORT).show();
								User ur = getUser(a);
								deleteRequired(ur);
								createList();
//								loadData();
//								startActivityForResult(intent, 1);
//								setResult(RESULT_OK);
								
							}
						});
						
						return v;
					}
				};
//				Toast.makeText(getApplicationContext(), "set Adapter cho listview", Toast.LENGTH_SHORT).show();
				listview.setAdapter(adapter);
//				Toast.makeText(getApplicationContext(), "lưu thay đổi cho listview", Toast.LENGTH_SHORT).show();
				adapter.notifyDataSetChanged();
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						
					}
				});
				db.close();
	
				hander.postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						createList();
//						noticle();
					}
					
					
				}, 1000);
	}
	public void refrestData(){
		array.removeAll(array);
		DBHandler db = new DBHandler(this);
		List<User> user_list = db.getAllRequired();
		for(User user : user_list){
			String name= user.getUser_name();
			// phần này là lấy dữ liệu từ mảng userlist cho vào biến HashMap
			loadData(name);
			
			
		}
		adapter.notifyDataSetChanged();
		refrestData();
	}
	public void deleteRequired(User ur){
		DBHandler db = new DBHandler(this);
		db.delete(ur);
	
		
	}
	//
	public User getUser(int pos){
		
		DBHandler db = new DBHandler(this);
		User ur = db.getUserbyPos(pos);
		return ur;
	
	}
	public void addFriend(User ur){
		DBHandler db = new DBHandler(this);
		db.addFriend(ur);
		
	}
	public void loadData(String name){
		
		HashMap temp = new HashMap();
		temp.put(NAME, name);
		array.add(temp);
		
	}
//	public void notifitycation(){
//		NotificationCompat.Builder b = new NotificationCompat.Builder(this);
//		b.setContentTitle("Tin nhắn mới");
//		b.setContentText("Bạn có tin nhắn mới");
//		b.setTicker("Tin nhắn mới tới");
//		b.setSmallIcon(R.drawable.icon);
//		b.setNumber(++numMessages);
//		Intent resultIntent = new Intent(this,TabMain.class);
//		TaskStackBuilder sb = TaskStackBuilder.create(this);
//		sb.addParentStack(TabMain.class);
//		sb.addNextIntent(resultIntent);
//		PendingIntent rpi = sb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//		b.setContentIntent(rpi);
//		mNoti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		mNoti.notify(notiID,b.build());
//		
//		
//	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK)
			Toast.makeText(getApplicationContext(), "OK- intent", Toast.LENGTH_SHORT).show();
		
		
		 if(requestCode == 3 && resultCode == RESULT_OK)
			
			finish();
			
			
		
	}

}
