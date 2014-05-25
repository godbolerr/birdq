/**
 * 
 */
package com.example.birdq.data;

import java.util.ArrayList;
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
	
	List<String> options;
	
	
	public BirdInfo(String id, String name,String url) {
		names = new HashMap<String,String>();
		options = new ArrayList<String>();
		this.id = id;
		this.name = name;
		this.pictUrl = url;
	}
	
	
	/**
	 * 
	 */
	public BirdInfo() {
		names = new HashMap<String,String>();
		options = new ArrayList<String>();
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
	

	public String getEnglishName() {
		return englishName;
	}


	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}


	public List<String> getOptions() {
		return options;
	}

	public void addOption(String option){
		options.add(option);
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}
	

}
