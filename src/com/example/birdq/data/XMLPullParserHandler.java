package com.example.birdq.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLPullParserHandler {
	
	public static final String BIRD = "b";
	public static final String ID = "id";
	public static final String ID_START = "<id>";
	public static final String NAME = "name";
	public static final String URL = "url";	
	public static final String ENGNAME = "eName";
	public static final String ALTERNATIVE = "alt";

	
	List<BirdInfo> birdInfos;
	private BirdInfo birdInfo;
	private String text;

	public XMLPullParserHandler() {
		birdInfos = new ArrayList<BirdInfo>();
	}

	public List<BirdInfo> getBirdInfos() {
		return birdInfos;
	}

	public List<BirdInfo> parse(InputStream is) {
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();
			parser.setInput(is,"UTF-8");

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagname = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagname.equalsIgnoreCase(BIRD)) {
						// create a new instance of birdInfo
						birdInfo = new BirdInfo();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase(BIRD)) {
						// add birdInfo object to list
						birdInfos.add(birdInfo);
					} else if (tagname.equalsIgnoreCase(ENGNAME)) {
						birdInfo.setEnglishName(text);
					} else if (tagname.equalsIgnoreCase(NAME)) {
						birdInfo.setName(text);
					} else if (tagname.equalsIgnoreCase(ID)) {
						birdInfo.setId(text);
					} else if (tagname.equalsIgnoreCase(URL)) {
						birdInfo.setPictUrl(text);
					} else if (tagname.equalsIgnoreCase(ALTERNATIVE)) {

						StringTokenizer stk = new StringTokenizer(text, ",");

						ArrayList<String> names = new ArrayList<String>();

						while (stk.hasMoreTokens()) {
							names.add(stk.nextToken());
						}

						birdInfo.addAlternative("en", names);
					}
					break;

				default:
					break;
				}
				eventType = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return birdInfos;
	}
}
