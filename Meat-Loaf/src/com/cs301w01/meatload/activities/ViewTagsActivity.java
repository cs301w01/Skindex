package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Collection;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.AlbumAdapter;
import com.cs301w01.meatload.adapters.TagAdapter;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.TagsGallery;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
	
	//adapters
	private TagAdapter allTagsAdapter;
	private TagAdapter selectedTagsAdapter;
	
	//arraylist needed for second listview
	private ArrayList<Tag> selectedTags;
	private ArrayList<Tag> filteredAllTags;
	private ArrayList<Tag> allTags;
	
	//list views
	private ListView allTagsLV;
	private ListView selectedTagsLV;
	
	//auto complete view
	private AutoCompleteTextView searchField;
	
	//current picture count view
	private TextView pictureCount;
	
    //@Override
    public void update(Object model) {
        
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.viewtags);
        
        mainManager = new MainManager();
        mainManager.setContext(this);
        
        selectedTags = new ArrayList<Tag>();
		allTags = mainManager.getAllTags();
		filteredAllTags = new ArrayList<Tag>();
		filteredAllTags.addAll(allTags);
        
		pictureCount = (TextView) findViewById(R.id.tagPicCountValue);
		
        createListViews();       
        createSearchField();       		
		createListeners();
        
    }
    
    private void createSearchField() {
    	
    	searchField = (AutoCompleteTextView) findViewById(R.id.tagSearchEditText);
        
    	ArrayList<String> tagStrings = new ArrayList<String>();
        //create auto complete field
		for (Tag tag : allTags) {
			tagStrings.add(tag.getName());
		}
		
		//create and adapter for auto complete field
		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item, tagStrings);
		searchField.setAdapter(stringAdapter);
		
	}

	protected void createListeners() {
        
    	//set listener for auto complete update, filters allTagsLV
    	searchField.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int pos,
                    long id) {
            	//get string of autocomplete
            	String tagName = parent.getItemAtPosition(pos).toString();
            	
            	filterAllTagsListView(tagName);

            }
        });

    	
        final Button viewPicturesButton = (Button) findViewById(R.id.viewSelections);
        viewPicturesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	onViewPictureClick();
            	
            }
        });
        
        allTagsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long arg3) {
	                
	                Tag selectedTag = (Tag) adapter.getItemAtPosition(pos); 
	                
	                filteredAllTags.remove(selectedTag);
	                selectedTags.add(selectedTag);
	                
	                updateSelectedTagPictureCount();
	                
	                allTagsAdapter.notifyDataSetChanged();
	                selectedTagsAdapter.notifyDataSetChanged();
						
					
	        }
			
		});
        
        selectedTagsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long arg3) {
	                
	                Tag selectedTag = (Tag) adapter.getItemAtPosition(pos); 
	                
	                selectedTags.remove(selectedTag);
	                filteredAllTags.add(selectedTag);
	                
	                updateSelectedTagPictureCount();
	                
	                allTagsAdapter.notifyDataSetChanged();
	                selectedTagsAdapter.notifyDataSetChanged();
						
					
	        }
			
		});
        
        //tagListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    private void updateSelectedTagPictureCount() {
		
    	if(selectedTags.size() > 0) {
    	
	    	PictureQueryGenerator pQG = new PictureQueryGenerator(this);
	    	int pCount = pQG.getPictureCountByTags(convertTagsToStringArray(selectedTags));
	    	
	    	pictureCount.setText(Integer.toString(pCount));
    	
    	} else 
    		pictureCount.setText("0");
    	
		
	}

	private Collection<String> convertTagsToStringArray(ArrayList<Tag> selectedTags) {
		
		Collection<String> tagNames = new ArrayList<String>();
		
		for(Tag tag : selectedTags) {
			
			tagNames.add(tag.getName());
			
		}
		
		return tagNames;

	}

	private void onViewPictureClick() {

		
		
    	TagsGallery tG = new TagsGallery(new ArrayList(convertTagsToStringArray(selectedTags)));
    	
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", 
		"com.cs301w01.meatload.activities.GalleryActivity");
    	myIntent.putExtra("gallery", tG);
    	
    	startActivity(myIntent);
		
	}

	private void filterAllTagsListView(String tagName) {
    	
    	//filter out non-matches
    	for(int i = 0; i < filteredAllTags.size(); i++) {
    		
    		if(!filteredAllTags.get(i).getName().equals(tagName) 
    				|| !filteredAllTags.get(i).getName().contains(tagName)) {
    			
    			filteredAllTags.remove(tag);
    			
    		}
    		
    	}
    	
    	allTagsAdapter.notifyDataSetChanged();
    	
    }
    
    public void refreshScreen() {
    	
    	createListViews();
    	createListeners();
		
    }

	private void createListViews() {
		
		//set top list
    	allTagsLV= (ListView) findViewById(R.id.tagListView);
		allTagsAdapter = new TagAdapter(this, R.layout.list_item, filteredAllTags);
		allTagsLV.setAdapter(allTagsAdapter);
		
		//create bottom list
		selectedTagsLV = (ListView) findViewById(R.id.selectedTagsListView);
		selectedTagsAdapter = new TagAdapter(this, R.layout.list_item, selectedTags);
		selectedTagsLV.setAdapter(selectedTagsAdapter);
		
		
	}
    
    

}
