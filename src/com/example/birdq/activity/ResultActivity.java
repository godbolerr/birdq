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
	// All static variables
	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	public static final String KEY_SONG = "song"; // parent node
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_ARTIST = "artist";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_THUMB_URL = "thumb_url";
	public static final String ANSWER_STATUS = "answerStatus";

	ListView list;
	BirdAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();

		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();

		HashMap<String, String> map5 = new HashMap<String, String>();
		HashMap<String, String> map6 = new HashMap<String, String>();

		map1.put(KEY_ID, "1");
		map1.put(KEY_TITLE, "t1");
		map1.put(KEY_ARTIST, "t2");
		map1.put(KEY_DURATION, "t3");
		map1.put(
				KEY_THUMB_URL,
				"http://www.warrenphotographic.co.uk/photography/bigs/00489-Street-Pigeon-white-background.jpg");

		map2.put(KEY_ID, "2");
		map2.put(KEY_TITLE, "r1");
		map2.put(KEY_ARTIST, "r2");
		map2.put(KEY_DURATION, "r3");
		map2.put(
				KEY_THUMB_URL,
				"http://images.nationalgeographic.com/wpf/media-live/photos/000/171/overrides/house-sparrow_17170_600x450.jpg");

		map3.put(KEY_ID, "3");
		map3.put(KEY_TITLE, "t3");
		map3.put(KEY_ARTIST, "t3");
		map3.put(KEY_DURATION, "t3");
		map3.put(
				KEY_THUMB_URL,
				"http://birds.audubon.org/sites/default/files/imagecache/bird-half/species_images/Baltimore_Oriole_s52-11-018_l_0.jpg");

		map4.put(KEY_ID, "4");
		map4.put(KEY_TITLE, "t4");
		map4.put(KEY_ARTIST, "t4");
		map4.put(KEY_DURATION, "t4");
		map4.put(
				KEY_THUMB_URL,
				"http://www.warrenphotographic.co.uk/photography/bigs/00489-Street-Pigeon-white-background.jpg");

		map5.put(KEY_ID, "5");
		map5.put(KEY_TITLE, "t5");
		map5.put(KEY_ARTIST, "t5");
		map5.put(KEY_DURATION, "t5");
		map5.put(
				KEY_THUMB_URL,
				"http://birds.audubon.org/sites/default/files/imagecache/bird-half/species_images/Baltimore_Oriole_s52-11-018_l_0.jpg");

		map6.put(KEY_ID, "6");
		map6.put(KEY_TITLE, "t6");
		map6.put(KEY_ARTIST, "t6");
		map6.put(KEY_DURATION, "t6");
		map6.put(KEY_THUMB_URL,
				"http://pets4u.info/wp-content/uploads/2013/12/hawk-bird.jpg");

		songsList.add(map1);
		songsList.add(map2);
		songsList.add(map3);
		songsList.add(map4);
		songsList.add(map5);
		songsList.add(map6);

		// XMLParser parser = new XMLParser();
		// String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		// Document doc = parser.getDomElement(xml); // getting DOM element
		//
		// NodeList nl = doc.getElementsByTagName(KEY_SONG);
		// // looping through all song nodes <song>
		// for (int i = 0; i < nl.getLength(); i++) {
		// // creating new HashMap
		// HashMap<String, String> map = new HashMap<String, String>();
		// Element e = (Element) nl.item(i);
		// // adding each child node to HashMap key => value
		// map.put(KEY_ID, parser.getValue(e, KEY_ID));
		// map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
		// map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
		// map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
		// map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
		//
		// // adding HashList to ArrayList
		// songsList.add(map);
		// }

		list = (ListView) findViewById(R.id.list);

		TextView tView = (TextView) findViewById(R.id.resultText);

		tView.setText("Score : ");

		// Get the result and prepare the collection for display

		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

		ResultManager rm = QuizActivity.getResultManager();

		List<BirdInfo> bList = BirdInfoDatabase.birds;

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

			resultMap.put(KEY_ID, count + "");
			resultMap.put(KEY_TITLE, birdInfo.getName());
			resultMap.put(KEY_ARTIST, answer);
			resultMap.put(KEY_DURATION, birdInfo.getId());
			resultMap.put(KEY_THUMB_URL, birdInfo.getPictUrl());

			count++;

			resultList.add(resultMap);

		}

		tView.setText("Answered " + score  + " out of " +
				+ bList.size());

		adapter = new BirdAdapter(this, resultList);
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	public void onReset(View view) {

		Intent i = new Intent(this,QuizActivity.class);

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);

	}


}