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
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.birdq.activity.QuizActivity;
import com.example.birdq.activity.UserSettingActivity;
import com.example.birdq.data.BirdInfoDatabase;

public class MainActivity extends Activity {

	ProgressDialog myProgressDialog = null;

	private static final int RESULT_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			new DownloadWebpageTask(this).execute("");
		} else {
			Toast.makeText(getBaseContext(), "No network available",
					Toast.LENGTH_SHORT).show();

		}

	}

	public void englishClick(View v) {
		Toast.makeText(getBaseContext(), "Start quiz in english",
				Toast.LENGTH_SHORT).show();
	}

	// Uses AsyncTask to create a task away from the main UI thread. This task
	// takes a
	// URL string and uses it to create an HttpUrlConnection. Once the
	// connection
	// has been established, the AsyncTask downloads the contents of the webpage
	// as
	// an InputStream. Finally, the InputStream is converted into a string,
	// which is
	// displayed in the UI by the AsyncTask's onPostExecute method.
	private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

		MainActivity mActivity;

		public DownloadWebpageTask(MainActivity theActivity) {
			this.mActivity = theActivity;
		}

		@Override
		protected String doInBackground(String... urls) {

			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(mActivity);

			String lang = sharedPrefs.getString("quizLang", "en");

			String baseUrl = sharedPrefs.getString("appUrl",
					"http://birdinfoquiz.appspot.com/");

			if (baseUrl == null) {
				baseUrl = "http://birdinfoquiz.appspot.com/";
			}

			BirdInfoDatabase.init(baseUrl + "xml/" + lang + "/getbirds");

			return "Success";

		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Intent nextScreen = new Intent(mActivity, QuizActivity.class);

			startActivity(nextScreen);
		}
	}
	
	

}
