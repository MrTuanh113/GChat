package com.chatt.demo;

import Database.DBHandler;
import android.app.Notification;
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

public class Noti extends Service{
	public static int index;
	private NotificationManager mNoti;
	private int notiID=100;
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
	}
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		try{
		noticle();}
		catch(Exception e){
			Toast.makeText(this, "Lỗi "+e, Toast.LENGTH_LONG).show();
			
		}
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void noticle(){
		DBHandler db = new DBHandler(this);
		

		int count = db.getCount();
		
		if(TabRequired.index==count){
			
		}else if(TabRequired.index > count){
			TabRequired.index = count;
			
		}else{
			
			notifitycation();
			
			TabRequired.index=count;
		}
		db.close();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				noticle();
			}
		});
		
		
	}
	public void notifitycation(){
		NotificationCompat.Builder b = new NotificationCompat.Builder(this);
		b.setContentTitle("GChat - Lời mời kết bạn");
		b.setContentText("Bạn có lời mời kết bạn");
		b.setTicker("Lời mời kết bạn mới");
		b.setSmallIcon(R.drawable.icon);
		b.setNumber(++numMessages);
		Intent resultIntent = new Intent(this,TabMain.class);
		TaskStackBuilder sb = TaskStackBuilder.create(this);
		sb.addParentStack(TabMain.class);
		sb.addNextIntent(resultIntent);
		PendingIntent rpi = sb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		b.setContentIntent(rpi);
		b.setAutoCancel(true);
		mNoti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNoti.notify(notiID,b.build());
		
		
	}
	public void cancelNotification(){
		mNoti.cancel(notiID);
		
	}

}
