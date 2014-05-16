package com.nagarjuna_pamu.mess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SocketService extends Service {
	
	public static final String MESS_SERVER = "ws://10.8.2.44:9000/register";
	
	public SocketService() {
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		
		return super.onStartCommand(intent, flags, startId);
	}

	

}
