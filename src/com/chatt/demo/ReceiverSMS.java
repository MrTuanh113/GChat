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
						db.add(new User(id,name));
						db.close();
						}catch(Exception e){
							Toast.makeText(context, "Lỗi "+e, Toast.LENGTH_LONG).show();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}   }
			
			
		}catch(Exception e){
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
			Toast.makeText(context, "Lỗi "+e, Toast.LENGTH_LONG).show();
		}
		
	}
	
		
		
	

}
