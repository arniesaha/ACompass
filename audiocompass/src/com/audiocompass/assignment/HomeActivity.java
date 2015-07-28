package com.audiocompass.assignment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.audiocompass.assignment.R;

public class HomeActivity extends ActionBarActivity{
	private static final String RUN_NUMS = "run_nums";
	private static final String TEXT_VALUE = "Excelleant Wallpaper  is a great wallpaper App";
	private static final String TITLE_VALUE ="Excelleant Wallpaper";
	private static final int FIRE_CODE = 0;
	private static final String TAG = HomeActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		SharedPreferences fm = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		int runs = fm.getInt(RUN_NUMS, 0);
		fm.edit().putInt(RUN_NUMS, runs+1)
		.commit();
		Log.d(TAG, runs+"");
		if(runs == 0)fireIntent();
		else nextActivity();
			
			
		}
		


	private void nextActivity() {
		startActivity(new Intent(getBaseContext(), MainActivity.class));
		finish();
		
	}



	private void fireIntent() {
		Log.d(TAG, "firing");
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, TEXT_VALUE);
		shareIntent.putExtra(Intent.EXTRA_TITLE, TITLE_VALUE);
		startActivityForResult(Intent.createChooser(shareIntent, "Share this App"), FIRE_CODE);
	}
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		
		if(resultCode == RESULT_OK){
			
		}
		
		
		
		nextActivity();
		super.onActivityResult(requestCode, resultCode, data);
	}
}
