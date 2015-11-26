package com.chatt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DBHandler;
import Database.User;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chatt.demo.utils.Const;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class TabFriend extends Activity{
	ListView listview;
	ArrayList<ParseUser> listuser;
	ArrayList<HashMap<String,String>> array;
	SimpleAdapter adapter;
	public static ParseUser user;
	public final static String TEXT ="texview";
	public final static String BUTTON ="button";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabfriend);
//		getActionBar().setDisplayHomeAsUpEnabled(false);
		listview = (ListView) findViewById(R.id.listView1);
				
				ParseUser.getQuery().whereNotEqualTo("username", user.getUsername())
				.findInBackground(new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> li, ParseException e)
					{
						
						if (li != null)
						{
							if (li.size() == 0)
								Toast.makeText(TabFriend.this,
										R.string.msg_no_user_found,
										Toast.LENGTH_SHORT).show();

							listuser = new ArrayList<ParseUser>(li);
							array = new ArrayList<HashMap<String,String>>();
							for(ParseUser puser:listuser){
								String name = puser.getUsername();
								loadData(name);
								
								
							}
							
							String[] tags={TEXT};
							int[] ids={R.id.tvName};
							
							adapter = new SimpleAdapter(TabFriend.this, array,R.layout.chat_item, tags, ids){
								public View getView(int position, View convertView, android.view.ViewGroup parent) {
									final int a = position;
//									Toast.makeText(getApplicationContext(), "vị trí hiện tại là  "+position, Toast.LENGTH_SHORT).show();
									View v= super.getView(position, convertView, parent);
									Button b = (Button) v.findViewById(R.id.btAddFriend);
									Button imgbt = (Button) v.findViewById(R.id.btChat);
									b.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											
											String f_id=listuser.get(a).getObjectId();
											String f_name= listuser.get(a).getUsername();
											User userf = new User(f_id,f_name);
											addFriend(userf);
											
											
											
											String id = user.getObjectId();
											String name = user.getUsername();
											
											String message ="{\"id\":"+id+",\"name\":"+name+"}";
											
											
										
											
											
											//=========
											try {
												ParseUser user1 = listuser.get(a);
												String id1= user1.getObjectId();
												List<ParseUser> list =ParseUser.getQuery().whereEqualTo("objectId", id1).find();
												ParseUser user2=list.get(0);
												String number = user2.getString("phonenumber");
												
												sendSMS(number,message);
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
//											//============
											
											
											
											
										}
												
									
									});
									
									imgbt.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											startActivity(new Intent(TabFriend.this,
													Chat1.class).putExtra(
													Const.EXTRA_DATA, listuser.get(a)
															.getUsername()));
										}
									});
									return v;
										
										
									
								};
								
							};
							listview.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							
							listview.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View v, int pos, long arg3)
								{
									
//									Button btAddFriend = (Button) findViewById(R.id.btAddFriend);
//									if(v==btAddFriend){
//										Toast.makeText(getApplicationContext(), "Bạn chọn "+ array.get(pos).get(TEXT), Toast.LENGTH_SHORT).show();
//										
//									}
								
								}
								
							});
						}
							else
							{
								
								e.printStackTrace();
							}
						}
					});
				
	}
	
	private void sendSMS(String phonenumber, String text) {
		// TODO Auto-generated method stub
		
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, TabFriend.class), 0);
		SmsManager sms = SmsManager.getDefault();
		
		sms.sendTextMessage(phonenumber, null, text, null, null);
	}
	private void loadData(String name) {
		// TODO Auto-generated method stub
		
		HashMap temp = new HashMap();
		temp.put(TEXT, name);
		array.add(temp);
		
	}
	public void addFriend(User ur){
		DBHandler db = new DBHandler(this);
		db.addFriend(ur);
		
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
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
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		switch(item.getItemId()){
//		case R.id.btpublic:
//			Toast.makeText(getApplicationContext(), "Public", Toast.LENGTH_SHORT).show();
//		
//			return true;
//			
//		case R.id.btprivate:
//			
//			Intent intent2 = new Intent(TabFriend.this,ActivityRequired.class);
//			startActivityForResult(intent2, 3);
////			Toast.makeText(getApplicationContext(), "Private", Toast.LENGTH_SHORT).show();
////			Intent intent1 = new Intent(UserList.this,ActivityPrivate.class);
////			startActivity(intent1);
//			return true;
//		default:
//			
//			return super.onOptionsItemSelected(item);
//		}
//		
//	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2 && resultCode == RESULT_OK)
			Toast.makeText(getApplicationContext(), "OK- intent2", Toast.LENGTH_SHORT).show();
			finish();

	}

}
