package com.cs301w01.meatload.activities;

import java.util.ArrayList;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.AlbumAdapter;
import com.cs301w01.meatload.adapters.TagAdapter;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Tag;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Implements the logic in the Tags view of the Tab layout in Skindex. 
 * @author Joel Burford
 */
public class ViewTagsActivity extends Skindactivity {

	private MainManager mainManager;
	private ListView tagListView;
	private TagAdapter adapter;
	
    //@Override
    public void update(Object model) {
        
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewtags);
        mainManager = new MainManager();
        mainManager.setContext(this);
        
        refreshScreen();
        createListeners();
    }
    
    protected void createListeners() {
        
        final Button searchButton = (Button) findViewById(R.id.tagSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
        
        //tagListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    public void refreshScreen() {
    	tagListView = (ListView) findViewById(R.id.tagListView);
		ArrayList<Tag> tagList = mainManager.getAllTags();
		adapter = new TagAdapter(this, R.layout.list_item, tagList);
		tagListView.setAdapter(adapter);
    }

}
