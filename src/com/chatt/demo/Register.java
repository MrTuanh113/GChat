package com.chatt.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.utils.Utils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Register extends CustomActivity
{

	
	private EditText user;

	
	private EditText pwd;


	private EditText email;
	
	private EditText confirm_pass;
	
	// lưu số điện thoại
	private EditText phonenumber;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		email = (EditText) findViewById(R.id.email);
		confirm_pass = (EditText) findViewById(R.id.confirm_password);
		phonenumber = (EditText) findViewById(R.id.phone_number);
	}


	@Override
	public void onClick(View v)
	{
		super.onClick(v);

		String u = user.getText().toString();
		String p = pwd.getText().toString();
		String e = email.getText().toString();
		String c = confirm_pass.getText().toString();
		String f = phonenumber.getText().toString();
		if (u.length() == 0 || p.length() == 0 || e.length() == 0||f.length()==0)
		{
			Utils.showDialog(this, R.string.err_fields_empty);
			return;
		}
		
		if (!p.equals(c))
		{
			Utils.showDialog(this, "Mật khẩu không khớp");
			return;
		}
		
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_wait));

		final ParseUser pu = new ParseUser();
		pu.setEmail(e);
		pu.setPassword(p);
		pu.setUsername(u);
		pu.put("phonenumber", f);
		
		
		pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e)
			{
				dia.dismiss();
				if (e == null)
				{
					TabFriend.user = pu;
					startActivity(new Intent(Register.this, TabFriend.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
					Utils.showDialog(
							Register.this,
							getString(R.string.err_singup) + " "
									+ e.getMessage());
					e.printStackTrace();
				}
			}
		});

	}
}
