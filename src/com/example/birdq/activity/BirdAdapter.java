package com.example.birdq.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birdq.R;
import com.example.birdq.utils.ImageLoader;

public class BirdAdapter extends BaseAdapter {
	
    private Activity activity;
    private ArrayList<HashMap<String,String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    
    
    public BirdAdapter(Activity a, ArrayList<HashMap<String,String>> d ) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

	public BirdAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.drawable.list_row, null);
 
        TextView birdName = (TextView)vi.findViewById(R.id.birdName); // title
        TextView answer = (TextView)vi.findViewById(R.id.answer); // artist name
        TextView englishName = (TextView)vi.findViewById(R.id.englishName); // artist name
        TextView count = (TextView)vi.findViewById(R.id.count); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        ImageView answerStatus =(ImageView)vi.findViewById(R.id.answerStatus); // thumb image
 
        HashMap<String,String> song = new HashMap<String,String>();
        
        song = data.get(position);
        
       
 
        // Setting all values in listview
      
        answer.setText(song.get(ResultActivity.ANSWER));
        count.setText(song.get(ResultActivity.KEY_COUNT));
        imageLoader.DisplayImage(song.get(ResultActivity.KEY_THUMB_URL), thumb_image);
        
        String bName = song.get(ResultActivity.BIRD_NAME);
        String engName = song.get(ResultActivity.ENGLISH_NAME);
        
        
        birdName.setText(bName);
        
        if ( bName != null & bName.equals(engName)) {
        	englishName.setText("");
        } else {
        	englishName.setText("[" + song.get(ResultActivity.ENGLISH_NAME) + "]");
        }
        
        
       if ( "right".equals(song.get(ResultActivity.ANSWER_STATUS))){
    	   answerStatus.setImageResource(R.drawable.right);
       } else {
    	   answerStatus.setImageResource(R.drawable.wrong);
       }
        
        
        
        return vi;
    }
}
