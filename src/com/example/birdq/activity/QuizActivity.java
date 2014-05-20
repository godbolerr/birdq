package com.example.birdq.activity;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
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
	
	public static final String bUrl  = "http://birdinfoquiz.appspot.com/xml/bird1.xml";

	
	static int currentRow = 0;

	RadioButton rb1;
	RadioButton rb2;
	RadioButton rb3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentRow = 0;

		setContentView(R.layout.activity_quiz);
		
		
		birds = BirdInfoDatabase.birds;

		imageLoader = new ImageLoader(this.getApplicationContext());

		rb1 = (RadioButton) findViewById(R.id.radioButton1);
		rb2 = (RadioButton) findViewById(R.id.radioButton2);
		rb3 = (RadioButton) findViewById(R.id.radioButton3);

		receiver = new QuizReceiver(new Handler(), this); // Create the receiver
		registerReceiver(receiver, new IntentFilter("some.action")); // Register
																		// receiver

		resultManager = new ResultManager();
		resultManager.reset();
		
		
		populateRadioButton();

	}

	
	public void onClickRadioButton(View view) {

		RadioButton radioButton = (RadioButton) findViewById(view.getId());
		TextView birdIdView = (TextView) findViewById(R.id.birdId);

		Intent i = new Intent("some.action");

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

		birdIdView.setText(thisInfo.getId());

		ImageView iView = (ImageView) findViewById(R.id.displayImage);

		imageLoader.DisplayImage(thisInfo.getPictUrl(), iView);

		List<String> alternatives = thisInfo.getAlternatives("en");

		RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);

		rb1.setText(thisInfo.getName());

		RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);

		rb2.setText(alternatives.get(0));

		RadioButton rb3 = (RadioButton) findViewById(R.id.radioButton3);

		rb3.setText(alternatives.get(1));

		// RadioGroup rGroup = (RadioGroup) findViewById(R.id.radioAnswerGroup);

		// if ( currentCount > 0 ){
		// // rGroup.check(id)
		// //rGroup.clearCheck();
		// }

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


	
}
