package com.example.birdq.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BirdInfoDatabase {

	public static List<BirdInfo> birds;
	public static String url;
	

	public BirdInfoDatabase() {

	}

	public static void init(String tUrl) {
		
		url = tUrl;
		birds = new ArrayList<BirdInfo>();
		
		XMLPullParserHandler parser = new XMLPullParserHandler();

		
		
		InputStream is;
		try {
			is = new URL(url).openStream();
			
			birds = parser.parse(is);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		// Hardcode 5 birds with values;

		BirdInfo sparrow = new BirdInfo(
				"1",
				"चिमणी",
				"http://images.nationalgeographic.com/wpf/media-live/photos/000/171/overrides/house-sparrow_17170_600x450.jpg");
		
		
		List<String> sparrowA = new ArrayList<String>();
		
		sparrowA.add(" कावळा ");
		sparrowA.add("गिधाड ");
		sparrowA.add("चिमणी कावळा गिधाड ");
		
		sparrow.addAlternative("en", sparrowA);
		
		
		BirdInfo piegon = new BirdInfo(
				"2",
				"piegon",
				"http://www.warrenphotographic.co.uk/photography/bigs/00489-Street-Pigeon-white-background.jpg");
		
		List<String> piegonA = new ArrayList<String>();
			
		piegonA.add("piegon 1");
		piegonA.add("piegon 2");
		piegonA.add("piegon 3");
			
		piegon.addAlternative("en", piegonA);
		
		
		
		
		
		
		BirdInfo oriole = new BirdInfo(
				"3",
				"oriole",
				"http://birds.audubon.org/sites/default/files/imagecache/bird-half/species_images/Baltimore_Oriole_s52-11-018_l_0.jpg");

		List<String> orioleA = new ArrayList<String>();
		
		orioleA.add("oriole 1");
		orioleA.add("oriole 2");
		orioleA.add("oriole 3");
			
		oriole.addAlternative("en", orioleA);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		BirdInfo hawk = new BirdInfo("4", "hawk",
				"http://pets4u.info/wp-content/uploads/2013/12/hawk-bird.jpg");
		
		
		
		List<String> hawkA = new ArrayList<String>();
		
		hawkA.add("hawk 1");
		hawkA.add("hawk 2");
		hawkA.add("hawk 3");
			
		hawk.addAlternative("en", hawkA);
		
			
		
		
		
		
		
		
		
		
		
		
		
		
		BirdInfo parrot = new BirdInfo(
				"5",
				"parrot",
				"http://www.wallpaperklix.com/wp-content/uploads/2014/02/yellow-color-parrot-bird-wallpaper-300x225.jpg");

		List<String> parrotA = new ArrayList<String>();
		
		parrotA.add("parrot 1");
		parrotA.add("parrot 2");
		parrotA.add("parrot 3");
			
		parrot.addAlternative("en", parrotA);
		
	
		
//		birds.add(sparrow);
//			birds.add(piegon);
//		birds.add(oriole);
//		birds.add(hawk);
//		birds.add(parrot);
		
	}

	public List<BirdInfo> getList() {
		return birds;
	}

}
