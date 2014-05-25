package com.example.birdq.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;



//TODO Explore error handling. what if URL can not be retrieved.

public class BirdInfoDatabase {

	public static List<BirdInfo> masterList;
	
	public static List<BirdInfo> currentList;
	
	public static boolean initialized = false;
	
	
	public static String url;
	

	public BirdInfoDatabase() {

	}

	public static void init(String tUrl) {
		
		url = tUrl;
		
		masterList = new ArrayList<BirdInfo>();
		
		currentList =  new ArrayList<BirdInfo>();
		
		
		XMLPullParserHandler parser = new XMLPullParserHandler();

		
		InputStream is;
		
		try {
			is = new URL(url).openStream();
			
			masterList = parser.parse(is);
			
			initialized = true;
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

	public static List<BirdInfo> getList(int size) {
		
		if ( initialized == false ) {
			init(url);
		}
		
		
		Map<String,BirdInfo> rMap = new HashMap<String,BirdInfo>();
		
		for (Iterator<BirdInfo> iterator = masterList.iterator(); iterator.hasNext();) {
			BirdInfo bInfo = (BirdInfo) iterator.next();
			rMap.put(UUID.randomUUID().toString(),bInfo );
		}
		
		currentList =  new ArrayList<BirdInfo>();
		
		int count = 0 ;
		
		for (Iterator<BirdInfo> iterator = rMap.values().iterator(); iterator.hasNext();) {
			
			if ( count >= size ) {
				break;
			}
			
			BirdInfo value = (BirdInfo) iterator.next();
			currentList.add(value);
			count++;
			
			
		}
		
		return currentList;
	}

}
