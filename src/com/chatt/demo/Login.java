package com.chatt.demo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ShareActionProvider;

import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.utils.Utils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Login extends CustomActivity
{

	
	private EditText user;

	
	private EditText pwd;
	String name ="my_data";
	private CheckBox ck;
	SQLiteDatabase data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		createData();
		createTable();

		setTouchNClick(R.id.btnLogin);
		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		ck = (CheckBox) findViewById(R.id.ck);
		
		

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		savingPreferenes();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		restoringPreferences();
	}
	private void savingPreferenes(){
		SharedPreferences share = getSharedPreferences(name, MODE_PRIVATE);
		SharedPreferences.Editor editor = share.edit();
		String u= user.getText().toString();
		String p = pwd.getText().toString();
		boolean c = ck.isChecked();
		if(!c){
			editor.clear();
		}else{
			editor.putString("username", u);
			editor.putString("password", p);
			editor.putBoolean("saveStatus", c);
			
			
		}
		editor.commit();
	}
	private void restoringPreferences(){
		SharedPreferences pref = getSharedPreferences(name, MODE_PRIVATE);
		boolean c = pref.getBoolean("saveStatus", false);
		if(c){
			String u = pref.getString("username", "");
			String p = pref.getString("password", "");
			user.setText(u);
			pwd.setText(p);
			
			
		}
		ck.setChecked(c);
	}
	

	


	public void createData(){
		data = openOrCreateDatabase("database", MODE_PRIVATE, null);
	}
	public void createTable(){
		String sql = "CREATE TABLE IF NOT EXISTS PASSWORD (";
			
			sql+="PASS TEXT)";
			data.execSQL(sql);
	}

	
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btnReg)
		{
			startActivityForResult(new Intent(this, Register.class), 10);
			 overridePendingTransition(R.anim.pull_in_right, 0);
		}
		else
		{

			String u = user.getText().toString();
			String p = pwd.getText().toString();
			if (u.length() == 0 || p.length() == 0)
			{
				Utils.showDialog(this, R.string.err_fields_empty);
				return;
			}
			final ProgressDialog dia = ProgressDialog.show(this, null,
					getString(R.string.alert_wait));
			ParseUser.logInInBackground(u, p, new LogInCallback() {

				@Override
				public void done(ParseUser pu, ParseException e)
				{
					dia.dismiss();
					if (pu != null)
					{
						Intent intent = new Intent(Login.this,Noti.class);
						startService(intent);
						Intent intent1 = new Intent(Login.this,ServiceNewMessage.class);
						startService(intent1);
						TabFriend.user = pu;
						startActivity(new Intent(Login.this, TabMain.class));
						overridePendingTransition(R.anim.pull_in_left, 0);
						finish();
					}
					else
					{
						Utils.showDialog(
								Login.this,
								getString(R.string.err_login) + " "
										+ e.getMessage());
						e.printStackTrace();
					}
				}
			});
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();

	}
}
