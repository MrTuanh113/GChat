package com.chatt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.utils.Const;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

// Class này hiển thị tất cả người dùng trong hệ thống
public class UserList extends CustomActivity
{
	ListView listview;
	ArrayList<ParseUser> listuser;
	ArrayList<HashMap<String,String>> array;
	SimpleAdapter adapter;
	public static ParseUser user;
	public final static String TEXT ="texview";
	public final static String BUTTON ="button";
	/** The Chat list. */
	

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);

		getActionBar().setDisplayHomeAsUpEnabled(false);
listview = (ListView) findViewById(R.id.list);
		
		ParseUser.getQuery().whereNotEqualTo("username", user.getUsername())
		.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> li, ParseException e)
			{
				
				if (li != null)
				{
					if (li.size() == 0)
						Toast.makeText(UserList.this,
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
					
					adapter = new SimpleAdapter(UserList.this, array,R.layout.chat_item, tags, ids){
						public View getView(int position, View convertView, android.view.ViewGroup parent) {
							final int a = position;
							View v= super.getView(position, convertView, parent);
							Button b = (Button) v.findViewById(R.id.btAddFriend);
							ImageButton imgbt = (ImageButton) v.findViewById(R.id.btChat);
							b.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Toast.makeText(getApplicationContext(), "save"+a, Toast.LENGTH_SHORT).show();
									sendSMS("01653666316","Ok gửi được rồi này!");
									
									
								}

							
							});
							imgbt.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									startActivity(new Intent(UserList.this,
											Chat.class).putExtra(
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
							
//							Button btAddFriend = (Button) findViewById(R.id.btAddFriend);
//							if(v==btAddFriend){
//								Toast.makeText(getApplicationContext(), "Bạn chọn "+ array.get(pos).get(TEXT), Toast.LENGTH_SHORT).show();
//								
//							}
						
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
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, UserList.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phonenumber, null, text, pi, null);
	}
	private void loadData(String name) {
		// TODO Auto-generated method stub
		
		HashMap temp = new HashMap();
		temp.put(TEXT, name);
		array.add(temp);
		
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		

	}

	/**
	 * Update user status.
	 * 
	 * @param online
	 *            true if user is online
	 */
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
			public boolean onOptionsItemSelected(MenuItem item) {
				// TODO Auto-generated method stub
				switch(item.getItemId()){
				case R.id.btpublic:
					Toast.makeText(getApplicationContext(), "Public", Toast.LENGTH_SHORT).show();
				
					return true;
					
				case R.id.btprivate:
					Toast.makeText(getApplicationContext(), "Private", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(UserList.this,ActivityPrivate.class);
					startActivity(intent);
					return true;
				default:
					Toast.makeText(getApplicationContext(), "Required", Toast.LENGTH_SHORT).show();
					return super.onOptionsItemSelected(item);
				}
				
			}
			
			
}
