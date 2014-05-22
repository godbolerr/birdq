/**
 * 
 */
package com.example.birdq.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ravi
 *
 */
public class BirdInfo {
	
	
	String id;
	
	String name;
	
	String englishName;
	
	String pictUrl;
	
	Map<String,String> names;
	
	Map<String,List<String>> alternative;
	
	
	public BirdInfo(String id, String name,String url) {
		names = new HashMap<String,String>();
		alternative = new HashMap<String,List<String>>();
		this.id = id;
		this.name = name;
		this.pictUrl = url;
	}
	
	
	/**
	 * 
	 */
	public BirdInfo() {
		names = new HashMap<String,String>();
		alternative = new HashMap<String,List<String>>();
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPictUrl() {
		return pictUrl;
	}



	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}



	public Map<String, String> getNames() {
		return names;
	}



	public void setNames(Map<String, String> names) {
		this.names = names;
	}
	
	
	public void addAlternative(String key,List<String> value){
		
		alternative.put(key, value);
		
	}
	
	public List<String> getAlternatives(String key){
		
		return alternative.get(key);
	}


	public String getEnglishName() {
		return englishName;
	}


	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	

}
