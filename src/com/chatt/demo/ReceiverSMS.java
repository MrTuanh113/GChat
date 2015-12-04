package com.chatt.demo;

import org.json.JSONException;
import org.json.JSONObject;

import Database.DBHandler;
import Database.User;
import JSON.JSONParse;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ReceiverSMS extends BroadcastReceiver{
final SmsManager sms = SmsManager.getDefault();
private NotificationManager mNoti;
private int notiID=100;
private int numMessages =0;


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final Bundle bun = intent.getExtras();
		try{
			if(bun!=null){
				final Object[] pdusObj = (Object[]) bun.get("pdus");
				for (int i = 0; i < pdusObj.length; i++) {
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
			        String senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();
			        Toast.makeText(context, message+"   "+phoneNumber, Toast.LENGTH_SHORT).show();
			        try {
						JSONObject jo = new JSONObject(message);
						String id = jo.getString("id");
						String name = jo.getString("name");
						try{
						DBHandler db = new DBHandler(context);
						// tÃ¬m trong báº£ng Friend nhá»¯ng user cÃ³ id giá»‘ng vá»›i id trong tin nháº¯n
						User user = db.getUserFriendbyUserId(id);
						if(user.getState()==0){
							// thay Ä‘á»•i state lÃ  2
							db.updateRequiredState(user);
							notifitycation(context,name);
						}
						
						

						db.close();
						}catch(Exception e){
							Toast.makeText(context, "LÃ¡Â»â€”i "+e, Toast.LENGTH_LONG).show();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}   }
			
			
		}catch(Exception e){
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
			Toast.makeText(context, "LÃ¡Â»â€”i "+e, Toast.LENGTH_LONG).show();
		}
		
		
		
		
		
		
		
		
	}
	
	public void notifitycation(Context context, String name){
		
		NotificationCompat.Builder b = new NotificationCompat.Builder(context);
		b.setContentTitle("GChat- tin nhắn kết bạn mới");
		b.setContentText("Bạn có tin nhắn kết bạn mới từ "+name);
		b.setTicker("Bạn có tin nhắn kết bạn mới");
		b.setSmallIcon(R.drawable.icon);
		b.setNumber(++numMessages);
		
		Intent resultIntent = new Intent(context,TabMain.class);
		resultIntent.putExtra("abc", true);
		TaskStackBuilder sb = TaskStackBuilder.create(context);
		sb.addParentStack(TabMain.class);
		sb.addNextIntent(resultIntent);
		PendingIntent rpi = sb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		b.setContentIntent(rpi);
		b.setAutoCancel(true);
		mNoti = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
		mNoti.notify(notiID,b.build());
		
		
	}
		
	

}
