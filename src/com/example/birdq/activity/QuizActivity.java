package com.example.birdq.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.birdq.R;
import com.example.birdq.data.BirdInfo;
import com.example.birdq.data.BirdInfoDatabase;
import com.example.birdq.data.ResultManager;
import com.example.birdq.utils.ImageLoader;

public class QuizActivity extends Activity {

	private QuizReceiver receiver;

	List<BirdInfo> birds;

	int currentCount;

	public ImageLoader imageLoader;

	public static ResultManager resultManager;
	
	public static final String BIRD_INTENT = "Bird.Action";

	static int currentRow = 0;

	RadioButton rb1;
	RadioButton rb2;
	RadioButton rb3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentRow = 0;

		setContentView(R.layout.activity_quiz);

		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		String recordsPerQuiz = sharedPrefs
				.getString("noOfRecordsPerQuiz", "5");

		int noOfRecordsPerQuiz = Integer.parseInt(recordsPerQuiz);

		birds = BirdInfoDatabase.getList(noOfRecordsPerQuiz);

		imageLoader = new ImageLoader(this.getApplicationContext());

		rb1 = (RadioButton) findViewById(R.id.radioButton1);
		rb2 = (RadioButton) findViewById(R.id.radioButton2);
		rb3 = (RadioButton) findViewById(R.id.radioButton3);

		receiver = new QuizReceiver(new Handler(), this); // Create the receiver
		registerReceiver(receiver, new IntentFilter(BIRD_INTENT)); // Register
																		// receiver

		resultManager = new ResultManager();
		
		resultManager.reset();

		populateRadioButton();

	}

	public void onClickRadioButton(View view) {

		RadioButton radioButton = (RadioButton) findViewById(view.getId());
		TextView birdIdView = (TextView) findViewById(R.id.birdId);

		Intent i = new Intent(BIRD_INTENT);

		i.putExtra("message", radioButton.getText().toString());
		i.putExtra("birdId", birdIdView.getText());

		resultManager.addAnswer(birdIdView.getText().toString(), radioButton
				.getText().toString());

		sendBroadcast(i);

		rb1.setChecked(false);
		rb2.setChecked(false);
		rb3.setChecked(false);

	}

	public void populateRadioButton() {

		if (currentCount >= birds.size()) {

			currentCount = 0;

			// Shift to result activity
			// Show the right pictures and ask the user if he/she wants to
			// continue ?

			Intent nextScreen = new Intent(this, ResultActivity.class);

			startActivity(nextScreen);

			return;

		}

		BirdInfo thisInfo = birds.get(currentCount);

		TextView birdIdView = (TextView) findViewById(R.id.birdId);
		ImageView iView = (ImageView) findViewById(R.id.displayImage);
		RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);
		RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
		RadioButton rb3 = (RadioButton) findViewById(R.id.radioButton3);

		birdIdView.setText(thisInfo.getId());

		imageLoader.DisplayImage(thisInfo.getPictUrl(), iView);

		List<String> alternatives = thisInfo.getOptions();
		
		// Handle if size is not 2.
		
		String opt1 = null;
		String opt2 = null;
		
		if ( alternatives != null && alternatives.size() < 2 ) {
			opt1 = "NA_ERROR";
			opt2 = opt1;
		}

		opt1 = alternatives.get(0);
		opt2 = alternatives.get(1);

		if (opt1 == null) {
			opt1 = "NA";
		}
		if (opt2 == null) {
			opt2 = "NA";
		}

		// Lets randomize alternatives

		Map<String, String> aMap = new HashMap<String, String>();

		aMap.put(UUID.randomUUID().toString(), opt1);
		aMap.put(UUID.randomUUID().toString(), opt2);
		aMap.put(UUID.randomUUID().toString(), thisInfo.getName());

		List<String> newValues = new ArrayList<String>();

		for (Iterator<String> iterator = aMap.values().iterator(); iterator
				.hasNext();) {
			String value = (String) iterator.next();
			newValues.add(value);
		}

		rb1.setText(newValues.get(0));
		rb2.setText(newValues.get(1));
		rb3.setText(newValues.get(2));

		currentCount++;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static class QuizReceiver extends BroadcastReceiver {

		private final Handler handler;
		private final QuizActivity thisActivity;

		public QuizReceiver(Handler handler, QuizActivity activity) {
			this.handler = handler;
			thisActivity = activity;

		}

		@Override
		public void onReceive(final Context context, final Intent intent) {
			// Post the UI updating code to our Handler
			handler.post(new Runnable() {
				@Override
				public void run() {
					// Toast.makeText(context, "Toast from broadcast receiver",
					// Toast.LENGTH_SHORT).show();
					thisActivity.populateRadioButton();
				}
			});
		}

	}

	public static ResultManager getResultManager() {
		return resultManager;
	}
	

	@Override
	protected void onStop()
	{
	    unregisterReceiver(receiver);
	    super.onStop();
	}

}
