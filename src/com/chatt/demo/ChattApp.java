package com.chatt.demo;

import android.app.Application;

import com.parse.Parse;


public class ChattApp extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		Parse.initialize(this, "ipoqgeKihz3Qdr2mA1EvfNX5a0mkfjrVPk15ajzm",
				"zPfWdHYTdGenR4RmfZepC72YJFkVbNJHOQR9uDwP");

	}
}
