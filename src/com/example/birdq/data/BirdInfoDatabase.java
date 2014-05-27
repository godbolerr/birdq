package com.example.birdq.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;

import com.example.birdq.utils.FileCache;

//TODO Explore error handling. what if URL can not be retrieved.

public class BirdInfoDatabase {

	public static List<BirdInfo> masterList;

	public static List<BirdInfo> currentList;

	public static boolean initialized = false;

	private static FileCache fileCache;

	public static String FILE_NAME = "birdQuizInput.xml";

	public static String url;

	public BirdInfoDatabase() {

	}

	public static void init(Activity activity, String tUrl, String langCode) {
		
		
		FILE_NAME = langCode + FILE_NAME;

		masterList = new ArrayList<BirdInfo>();

		currentList = new ArrayList<BirdInfo>();

		XMLPullParserHandler parser = new XMLPullParserHandler();

		InputStream is = null;

		url = tUrl;

		fileCache = new FileCache(activity.getApplicationContext());

		File f = fileCache.getFile(FILE_NAME);

		if (f != null && f.length() < 10) {

			try {
				URL url = new URL(tUrl);
				URLConnection connection = url.openConnection();
				InputStream in = connection.getInputStream();
				FileOutputStream fos = new FileOutputStream(new File(
						fileCache.getCacheDir(), FILE_NAME.hashCode()+""));
				byte[] buf = new byte[512];
				while (true) {
					int len = in.read(buf);
					if (len == -1) {
						break;
					}
					fos.write(buf, 0, len);
				}
				in.close();
				fos.flush();
				fos.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		f = fileCache.getFile(FILE_NAME);

		try {

			if (f != null) {

				is = new FileInputStream(f);

				masterList = parser.parse(is);

				initialized = true;
				
				
				

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (initialized == false) {

			// You should quit the program and try again....

		}

	}

	public static List<BirdInfo> getList(int size) {

		if (initialized == false) {
			return new ArrayList<BirdInfo>();
		}

		Map<String, BirdInfo> rMap = new HashMap<String, BirdInfo>();

		for (Iterator<BirdInfo> iterator = masterList.iterator(); iterator
				.hasNext();) {
			BirdInfo bInfo = (BirdInfo) iterator.next();
			rMap.put(UUID.randomUUID().toString(), bInfo);
		}

		currentList = new ArrayList<BirdInfo>();

		int count = 0;

		for (Iterator<BirdInfo> iterator = rMap.values().iterator(); iterator
				.hasNext();) {

			if (count >= size) {
				break;
			}

			BirdInfo value = (BirdInfo) iterator.next();
			currentList.add(value);
			count++;

		}

		return currentList;
	}

}
