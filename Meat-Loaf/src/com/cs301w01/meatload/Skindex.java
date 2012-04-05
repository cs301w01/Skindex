package com.cs301w01.meatload;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.activities.ViewTagsActivity;

/**
 * Activity file for tabbed landing page of MoleFinder.
 * @author Joel Burford
 * @author Derek Dowling
 * @author Isaac Matichuk
 * @see <a href="http://developer.android.com/resources/tutorials/views/hello-tabwidget.html">
 	http://developer.android.com/resources/tutorials/views/hello-tabwidget.html</a>
 */
public class Skindex extends TabActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //BEGIN TAB CODE////////////////////////
        //COPIED FROM http://developer.android.com/resources/tutorials/views/hello-tabwidget.html
    	Resources res = getResources(); // Resource object to get Drawables
    	TabHost tabHost = getTabHost();  // The activity TabHost
    	TabHost.TabSpec spec;  // Resusable TabSpec for each tab
    	Intent intent;  // Reusable Intent for each tab

    	// Create an Intent to launch an Activity for the tab (to be reused)
    	intent = new Intent().setClass(this, ViewAlbumsActivity.class);

    	// Initialize a TabSpec for each tab and add it to the TabHost
    	spec = tabHost.newTabSpec("albums").setIndicator(this.getString(R.string.tab_albums),
                       res.getDrawable(R.drawable.tab_main))
                       .setContent(intent);
    	tabHost.addTab(spec);

    	// Do the same for the other tabs
    	intent = new Intent().setClass(this, ViewTagsActivity.class);
    	spec = tabHost.newTabSpec("tags").setIndicator(this.getString(R.string.tab_tags),
                       	res.getDrawable(R.drawable.tab_main))
                       	.setContent(intent);
    	tabHost.addTab(spec);

    	tabHost.setCurrentTab(0);
        	
        	
        	
        ///END TAB CODE/////////////////////////
        
    }
    
    //@Override
    public void update(Object model) {
        //update whatever screen is up.
    }
    
}
