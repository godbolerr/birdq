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
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        ImageView answerStatus =(ImageView)vi.findViewById(R.id.answerStatus); // thumb image
 
        HashMap<String,String> song = new HashMap<String,String>();
        
        song = data.get(position);
        
       
 
        // Setting all values in listview
        title.setText(song.get(ResultActivity.KEY_TITLE));
        artist.setText(song.get(ResultActivity.KEY_ARTIST));
        duration.setText(song.get(ResultActivity.KEY_DURATION));
        imageLoader.DisplayImage(song.get(ResultActivity.KEY_THUMB_URL), thumb_image);
        
       if ( "right".equals(song.get(ResultActivity.ANSWER_STATUS))){
    	   answerStatus.setImageResource(R.drawable.right);
       } else {
    	   answerStatus.setImageResource(R.drawable.wrong);
       }
        
        
        
        return vi;
    }
}
