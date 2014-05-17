package com.nagarjuna_pamu.mess;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

public class MainActivity extends ActionBarActivity {
	
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        	 
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            /**
             * 
             */
            TextView textView = (TextView) rootView.findViewById(R.id.text);
            
            /**
             * 
             */
            final RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
            /**
             * 
             */
            TextView commentView = (TextView) rootView.findViewById(R.id.comment);
            /**
             * 
             */
            final EditText editText = (EditText) rootView.findViewById(R.id.editText);
            /**
             * 
             */
            Button submit = (Button) rootView.findViewById(R.id.submit);
            
            
            
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar arg0, float  arg1, boolean arg2) {
					// TODO Auto-generated method stub
					String ratingText = null;
					int rating = (int)arg1;
					if(rating == 1) {
						ratingText = "very poor";
					} else if(rating == 2) {
						ratingText = "poor";
					} else if(rating == 3) {
						ratingText = "average";
					} else if(rating == 4) {
						ratingText = "above average";
					} else if(rating == 5) {
						ratingText = "excellent";
					}
					Toast.makeText(getActivity(), ratingText, 500).show();
				}
			});
            
            submit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final JSONObject json = new JSONObject();
					int section = getArguments().getInt(ARG_SECTION_NUMBER);
					try {
						String secureId = Secure.getString(getActivity().getApplication().getContentResolver(),Secure.ANDROID_ID);
						json.put("secureId", secureId);
						json.put("section", section);
						json.put("rating", (int)ratingBar.getRating());
						json.put("comment", editText.getText().toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					final WebSocketConnection mConnection = new WebSocketConnection();
					try {
						mConnection.connect("ws://10.8.2.44:9000/rate", new WebSocketConnectionHandler(){

							@Override
							public void onClose(int code, final String reason) {
								// TODO Auto-generated method stub
								super.onClose(code, reason);
								getActivity().runOnUiThread(new Runnable(){
									public void run() {
										Toast.makeText(getActivity(), " Can't proceed due to connection problem. Try Again !!!", Toast.LENGTH_LONG).show();
									}
								});
							}

							@Override
							public void onOpen() {
								// TODO Auto-generated method stub
								super.onOpen();
								mConnection.sendTextMessage(json.toString());
							}

							@Override
							public void onTextMessage(String payload) {
								// TODO Auto-generated method stub
								super.onTextMessage(payload);
								try {
									JSONObject obj = new JSONObject(payload);
									int status = obj.getInt("status");
									if(status == 1) {
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "Thanks for Rating", Toast.LENGTH_LONG).show();
											}
										});
									} else if(status == 0) {
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(getActivity(), "You have already rated", Toast.LENGTH_LONG).show();
											}
										});
									} else {
										Toast.makeText(getActivity(), "WTF !!!(What a terrible Failure)", Toast.LENGTH_LONG).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							
						});
					} catch (WebSocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Toast.makeText(getActivity(), json.toString(), Toast.LENGTH_LONG).show();
					
				}
			});
            
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            
            if(section == 1) {
            	textView.setText("Breakfast");
            }else if(section == 2) {
            	textView.setText("Lunch");
            }else if(section == 3) {
            	textView.setText("Snacks");
            }else {
            	textView.setText("Dinner");
            }
            
            return rootView;
        }
    }

}
