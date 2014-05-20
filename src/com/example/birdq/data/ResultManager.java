package com.example.birdq.data;

import java.util.HashMap;
import java.util.Map;


public class ResultManager {
	
	
	
	Map<String,String> answerMap;

	public ResultManager() {
		answerMap = new HashMap<String,String>();
	}



	public void addAnswer(String id,String answer){
		answerMap.put(id, answer);
	}
	
	public void reset(){
		answerMap.clear();
	}

	public Map<String, String> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, String> answerMap) {
		this.answerMap = answerMap;
	}
	
	
	
	
	
	
	
	
	
}
