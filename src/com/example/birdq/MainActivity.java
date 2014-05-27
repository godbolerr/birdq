package com.example.birdq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.birdq.activity.QuizActivity;
import com.example.birdq.activity.UserSettingActivity;
import com.example.birdq.data.BirdInfoDatabase;

public class MainActivity extends Activity {

	ProgressDialog barProgressDialog;
	Handler updateBarHandler;

	private static final int RESULT_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		updateBarHandler = new Handler();

		new LoadViewTask().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_settings:
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			break;

		}

		return true;
	}

	public void startQuizOnClick(View v) {

		Intent nextScreen = new Intent(MainActivity.this, QuizActivity.class);
		startActivity(nextScreen);

	}

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
		// Before running code in separate thread
		@Override
		protected void onPreExecute() {
			// Create a new progress dialog
			barProgressDialog = new ProgressDialog(MainActivity.this);
			// Set the progress dialog to display a horizontal progress bar
			barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// Set the dialog title to 'Loading...'
			barProgressDialog.setTitle("Loading...");
			// Set the dialog message to 'Loading application View, please
			// wait...'
			barProgressDialog.setMessage("Loading remote data ....");
			// This dialog can't be canceled by pressing the back key
			barProgressDialog.setCancelable(false);
			// This dialog isn't indeterminate
			barProgressDialog.setIndeterminate(false);
			// The maximum number of items is 100
			barProgressDialog.setMax(100);
			// Set the current progress to zero
			barProgressDialog.setProgress(30);
			// Display the progress dialog
			barProgressDialog.show();
		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {

			// Here you should write your time consuming task...
			// Let the progress ring for 10 seconds...
			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(MainActivity.this);

			String lang = sharedPrefs.getString("quizLang", "en");

			String baseUrl = sharedPrefs.getString("appUrl",
					"http://birdinfoquiz.appspot.com/");

			if (baseUrl == null) {
				baseUrl = "http://birdinfoquiz.appspot.com/";
			}

			BirdInfoDatabase.init(MainActivity.this, baseUrl + "xml/" + lang
					+ "/getbirds",lang);

			if (BirdInfoDatabase.initialized == true) {
				barProgressDialog.dismiss();
			}

			return null;
		}

		// Update the progress
		@Override
		protected void onProgressUpdate(Integer... values) {
			// set the current progress of the progress dialog
			barProgressDialog.setProgress(values[0]);
		}

		// after executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			// close the progress dialog
			barProgressDialog.dismiss();

			Intent nextScreen = new Intent(MainActivity.this,
					QuizActivity.class);
			startActivity(nextScreen);

		}
	}

}
