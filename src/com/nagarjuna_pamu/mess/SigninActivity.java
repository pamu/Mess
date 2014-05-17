package com.nagarjuna_pamu.mess;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;

public class SigninActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_signin,
					container, false);
			/**
			 * 
			 */
			final EditText name = (EditText) rootView.findViewById(R.id.name);
			/**
			 * 
			 */
			final Button register = (Button) rootView.findViewById(R.id.register);
			
			register.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String person = name.getText().toString();
					if(person.equals("")) {
						Toast.makeText(getActivity(), "Empty Name is not allowed", Toast.LENGTH_SHORT).show();
					} else {
						String secureId = Secure.getString(getActivity().getApplication().getContentResolver(),Secure.ANDROID_ID);
						JSONObject obj = new JSONObject();
						try {
							obj.put("name", person);
							obj.put("secureId", secureId);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						final String json = obj.toString();
						Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
						
						final WebSocketConnection mConnection = new WebSocketConnection();
						try {
							mConnection.connect("ws://10.8.2.44:9000/register", new WebSocketConnectionHandler(){

								@Override
								public void onClose(int code, String reason) {
									// TODO Auto-generated method stub
									super.onClose(code, reason);
								}

								@Override
								public void onOpen() {
									// TODO Auto-generated method stub
									super.onOpen();
									mConnection.sendTextMessage(json);
								}

								@Override
								public void onTextMessage(String payload) {
									// TODO Auto-generated method stub
									super.onTextMessage(payload);
									try {
										JSONObject json = new JSONObject(payload);
										int status = json.getInt("status");
										if(status == 1) {
											SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences("mess", Context.MODE_PRIVATE);
											prefs.edit().putInt("count", 1).apply();;
											startActivity(new Intent(getActivity(), MainActivity.class));
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									Toast.makeText(getActivity(), payload, Toast.LENGTH_SHORT).show();
								}
								
							});
							
						} catch (WebSocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			});
			return rootView;
		}
	}

}
