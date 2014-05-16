package com.nagarjuna_pamu.mess;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Mess extends Application {
	public static String APP_TAG = "com.nagarjuna_pamu.mess";
	int count = 0;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SharedPreferences prefs = getApplicationContext().getSharedPreferences(APP_TAG, Context.MODE_PRIVATE);
		count = prefs.getInt("count", 0);
		
		if(count == 0) {
			Intent signin = new Intent(getApplicationContext(), SigninActivity.class);
			signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(signin);
			
		}else {
			count ++;
			prefs.edit().putInt("count", count);
			prefs.edit().commit();
		}
		
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
	
	
}
