package com.nagarjuna_pamu.mess;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Mess extends Application {
	int count = 0;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SharedPreferences prefs = getApplicationContext().getSharedPreferences("mess", Context.MODE_PRIVATE);
		count = prefs.getInt("count", 0);
		Log.d("ASS", "count value is  "+count);
		Toast.makeText(getApplicationContext(), new Integer(count).toString(), Toast.LENGTH_LONG).show();
		if(count == 0) {
			Intent signin = new Intent(getApplicationContext(), SigninActivity.class);
			signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(signin);
			
		}
		
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
	
	
}
