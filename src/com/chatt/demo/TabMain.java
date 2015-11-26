package com.chatt.demo;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class TabMain extends TabActivity{
	TabHost th;
	LinearLayout tab1Layout, tab2Layout, tab3Layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmain);
		th = (TabHost)findViewById(android.R.id.tabhost);
		
		//TabSpec
		
		TabSpec tab1 = th.newTabSpec("Tab friend");
		Intent it = new Intent(TabMain.this, TabFriend.class);
		tab1.setContent(it);
		tab1Layout = (LinearLayout)findViewById(R.id.LinearLayout1);
		tab1.setIndicator("Friend");
		
		th.addTab(tab1);
		
		TabSpec tab2 = th.newTabSpec("Tab Private");
		Intent it2 = new Intent(TabMain.this, TabPrivate.class);
		tab2.setContent(it2);
		tab2Layout = (LinearLayout)findViewById(R.id.LinearLayout2);
		tab2.setIndicator("Private");
		th.addTab(tab2);
		
		TabSpec tab3 = th.newTabSpec("Tab Required");
		Intent it3 = new Intent(TabMain.this, TabRequired.class);
		tab3.setContent(it3);
		tab3Layout = (LinearLayout)findViewById(R.id.LinearLayout3);
		tab3.setIndicator("Required");
		th.addTab(tab3);
		
		th.setOnTabChangedListener(new OnTabChangeListener() {
			
		    public void onTabChanged(String tabId) {
		        View currentView = th.getCurrentView();
		        int currentTab = th.getCurrentTab();
		        if (th.getCurrentTab() > currentTab)
	            {
	                currentView.setAnimation( inFromRightAnimation() );
	            }
	            else
	            {
	                currentView.setAnimation( outToRightAnimation() );
	            }
		        
		    }
		});
	}
	public Animation inFromRightAnimation()
	{
	    Animation inFromRight = new TranslateAnimation(
	            Animation.RELATIVE_TO_PARENT, +1.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f);
	    inFromRight.setDuration(240);
	    inFromRight.setInterpolator(new AccelerateInterpolator());
	    return inFromRight;
	}

	public Animation outToRightAnimation()
	{
	    Animation outtoLeft = new TranslateAnimation(
	            Animation.RELATIVE_TO_PARENT, -1.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f,
	            Animation.RELATIVE_TO_PARENT, 0.0f);
	    outtoLeft.setDuration(240);
	    outtoLeft.setInterpolator(new AccelerateInterpolator());
	    return outtoLeft;
	}

}