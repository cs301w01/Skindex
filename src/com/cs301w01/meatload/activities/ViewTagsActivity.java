package com.cs301w01.meatload.activities;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Implements the logic in the Tags view of the Tab layout in Skindex. 
 * @author Joel Burford
 */
public class ViewTagsActivity extends Skindactivity {

    //@Override
    public void update(Object model) {
        
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Tags tab");
        setContentView(textview);
    }

}
