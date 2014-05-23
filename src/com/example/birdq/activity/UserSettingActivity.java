/**
 * 
 */
package com.example.birdq.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.birdq.R;

/**
 * @author Ravi
 * 
 */
public class UserSettingActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

	}
}
