package com.chatt.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chatt.demo.model.Conversation;
import com.chatt.demo.utils.Const;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import Database.DBHandler;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ServiceNewMessage extends Service {
	private NotificationManager mNoti;
	private Date lastMsgDate;
	private String buddy;
	private ArrayList<Conversation> convList;
	public static ParseUser user;
	ArrayList<ParseUser> listuser;
	private int notiID;
	private int numMessages =0;
	Handler handler;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
		convList = new ArrayList<Conversation>();
		
		
	}
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		try{
		loadMessage();}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), "Lỗi là "+e, Toast.LENGTH_LONG).show();
			
		}
		
	}
//	public void noticle(){
//		DBHandler db = new DBHandler(this);
//		
//		int count = db.getCount();
//		
//		if(TabRequired.index==count){
//			
//		}else if(TabRequired.index > count){
//			TabRequired.index = count;
//			
//		}else{
//			
//			notifitycation();
//			
//			TabRequired.index=count;
//		}
//		handler.post(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//				noticle();
//			}
//		});
//	}
	public void loadMessage(){
		notiID =100;
		ParseUser.getQuery().whereNotEqualTo("username", TabFriend.user.getUsername()).findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
			
			for(ParseUser pu: arg0){
				String name = pu.getString("sender");
				
				
				ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");
				if (convList.size() == 0)
				{
					// load all messages...
					
					q.whereEqualTo("sender", name);
					q.whereEqualTo("receiver", TabFriend.user.getUsername());
				}
				else
				{
					// load only newly received message..
					if (lastMsgDate != null)
						q.whereGreaterThan("createdAt", lastMsgDate);
					q.whereEqualTo("sender", name);
					q.whereEqualTo("receiver", TabFriend.user.getUsername());
				}
				q.orderByDescending("createdAt");
				q.setLimit(30);
				
				q.findInBackground(new FindCallback<ParseObject>() {
						
					@Override
					public void done(List<ParseObject> li, ParseException e)
					{
						if (li != null && li.size() > 0)
						{
							
							for (int i = li.size() - 1; i >= 0; i--)
							{
								
								ParseObject po = li.get(i);
								Conversation c = new Conversation(po
										.getString("message"), po.getCreatedAt(), po
										.getString("sender"));
								convList.add(c);
								if (lastMsgDate == null
										|| lastMsgDate.before(c.getDate()))
									lastMsgDate = c.getDate();
								
								
								
								
								
								boolean t = po.getBoolean("state");
								if(t==false){
									
								notifitycation(buddy,notiID);}
							}
						}
						
						handler.postDelayed(new Runnable() {

							@Override
							public void run()
							{
								loadMessage();
							}
						}, 1000);
					}
				});
				Toast.makeText(getApplicationContext(), "id giờ là "+notiID, Toast.LENGTH_LONG).show();
				notiID++;
				
				
				
				
			}
			}
		});
		
		
	}
	public void notifitycation(String buddy, int id){
		NotificationCompat.Builder b = new NotificationCompat.Builder(this);
		b.setContentTitle("GChat - Bạn có tin nhắn mới");
		b.setContentText("Bạn có tin nhắn mới từ "+buddy);
		b.setTicker("Tin nhắn mới");
		b.setSmallIcon(R.drawable.icon);
		b.setNumber(++numMessages);
		Intent resultIntent = new Intent(this,Chat1.class).putExtra(
				Const.EXTRA_DATA, buddy);
		TaskStackBuilder sb = TaskStackBuilder.create(this);
		sb.addParentStack(TabMain.class);
		sb.addNextIntent(resultIntent);
		PendingIntent rpi = sb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		b.setContentIntent(rpi);
		b.setAutoCancel(true);
		mNoti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNoti.notify(id,b.build());
		
		
	}

}
