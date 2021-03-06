package com.example.birdq.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.birdq.R;
import com.example.birdq.data.BirdInfo;
import com.example.birdq.data.BirdInfoDatabase;
import com.example.birdq.data.ResultManager;

public class ResultActivity extends Activity {

	// XML node keys
	public static final String KEY_ID = "id";
	public static final String BIRD_NAME = "birdName";
	public static final String ANSWER = "answer";
	public static final String KEY_COUNT = "count";
	public static final String KEY_THUMB_URL = "thumb_url";
	public static final String ANSWER_STATUS = "answerStatus";
	public static final String ENGLISH_NAME = "englishName";
	
	

	ListView list;
	BirdAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.final_result);

		list = (ListView) findViewById(R.id.list);

		TextView tView = (TextView) findViewById(R.id.resultText);

		tView.setText("Score : ");

		// Get the result and prepare the collection for display

		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

		ResultManager rm = QuizActivity.getResultManager();

		List<BirdInfo> bList = BirdInfoDatabase.currentList;

		Map<String, String> answerMap = rm.getAnswerMap();

		int count = 0;
		int score = 0;

		for (Iterator<BirdInfo> iterator = bList.iterator(); iterator.hasNext();) {

			HashMap<String, String> resultMap = new HashMap<String, String>();

			BirdInfo birdInfo = (BirdInfo) iterator.next();

			String answer = answerMap.get(birdInfo.getId());

			if (birdInfo.getName().equalsIgnoreCase(answer)) {
				score++;

				// answerStatus

				resultMap.put(ANSWER_STATUS, "right");

			} else {

				resultMap.put(ANSWER_STATUS, "wrong");

			}

			count++;
			
			resultMap.put(KEY_ID, count + "");
			

			resultMap.put(BIRD_NAME, birdInfo.getName());
		
			resultMap.put(ANSWER, answer);
			resultMap.put(ENGLISH_NAME, birdInfo.getEnglishName());
			resultMap.put(KEY_COUNT,count + "");
			resultMap.put(KEY_THUMB_URL, birdInfo.getPictUrl());

			

			resultList.add(resultMap);

		}

		tView.setText("Answered " + score  + " out of " +
				+ bList.size());

		adapter = new BirdAdapter(this, resultList);
		list.setAdapter(adapter);

		
	}

	public void onReset(View view) {

		Intent i = new Intent(this,QuizActivity.class);

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);

	}


}