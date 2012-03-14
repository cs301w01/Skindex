package com.cs301w01.meatload.activities;

import java.util.Collection;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PhotoManager;
import com.cs301w01.meatload.model.Album;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ViewGroupsActivity extends Skindactivity {
	private MainManager mainManager;

    //@Override
    public void update(Object model) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Code taken from here: http://www.vogella.de/articles/AndroidListView/article.html
        
        ListView listView = new ListView(this);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        	"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        	"Linux", "OS/2" };

        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the View to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        	android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        setContentView(listView);
       
        
        mainManager = new MainManager(this);
        Collection<Album> aNames = mainManager.getAllAlbums();
    }

}
