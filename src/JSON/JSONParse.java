package JSON;

import org.json.JSONException;
import org.json.JSONObject;

import Database.DBHandler;
import Database.User;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class JSONParse extends IntentService{

	public JSONParse() {
		super("JSONParse");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle bun = intent.getBundleExtra("bun");
		String message = bun.getString("message");
		try {
			JSONObject jo = new JSONObject(message);
			String id = jo.getString("id");
			String name = jo.getString("name");
			DBHandler db = new DBHandler(this);
			db.add(new User(id,name));
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	
}
