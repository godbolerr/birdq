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

	public void marathiClick(View v) {

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// new DownloadWebpageTask(this).execute("");
			launchBarDialog(v);
		} else {
			Toast.makeText(getBaseContext(), "No network available",
					Toast.LENGTH_SHORT).show();

		}

	}

	public void launchBarDialog(View view) {

		barProgressDialog = new ProgressDialog(MainActivity.this);

		barProgressDialog.setTitle("Downloading Data ...");
		barProgressDialog.setMessage("Download in progress ...");
		barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(20);
		barProgressDialog.show();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

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

					BirdInfoDatabase
							.init(baseUrl + "xml/" + lang + "/getbirds");

					Intent nextScreen = new Intent(MainActivity.this,
							QuizActivity.class);
					startActivity(nextScreen);

				} catch (Exception e) {
				}
			}
		}).start();
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
			barProgressDialog.setProgress(0);
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

			BirdInfoDatabase.init(baseUrl + "xml/" + lang + "/getbirds");

			Intent nextScreen = new Intent(MainActivity.this,
					QuizActivity.class);
			startActivity(nextScreen);

			while (barProgressDialog.getProgress() <= barProgressDialog
					.getMax()) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				updateBarHandler.post(new Runnable() {

					public void run() {

						barProgressDialog.incrementProgressBy(10);

					}

				});

				if (barProgressDialog.getProgress() == barProgressDialog
						.getMax()) {

					barProgressDialog.dismiss();

				}
			}

			//
			// /* This is just a code that delays the thread execution 4 times,
			// * during 850 milliseconds and updates the current progress. This
			// * is where the code that is going to be executed on a background
			// * thread must be placed.
			// */
			// try
			// {
			// //Get the current thread's token
			// synchronized (this)
			// {
			// //Initialize an integer (that will act as a counter) to zero
			// int counter = 0;
			// //While the counter is smaller than four
			// while(counter <= 4)
			// {
			// //Wait 850 milliseconds
			// this.wait(850);
			// //Increment the counter
			// counter++;
			// //Set the current progress.
			// //This value is going to be passed to the onProgressUpdate()
			// method.
			// publishProgress(counter*25);
			// }
			// }
			// }
			// catch (InterruptedException e)
			// {
			// e.printStackTrace();
			// }
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
			// initialize the View
			// setContentView(R.layout.activity_quiz);

			Intent nextScreen = new Intent(MainActivity.this,
					QuizActivity.class);
			startActivity(nextScreen);

		}
	}

}
