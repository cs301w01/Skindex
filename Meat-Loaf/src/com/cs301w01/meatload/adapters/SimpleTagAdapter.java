package com.cs301w01.meatload.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs301w01.meatload.model.Tag;

public class SimpleTagAdapter extends TagAdapter {

	public SimpleTagAdapter(Context context, int textViewResourceId, List<Tag> tags) {
		super(context, textViewResourceId, tags);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
        // Get current tag object
        Tag tag = getItem(position);
 
        // Inflate the view
        if (view == null) {
            view = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater )getContext().getSystemService(inflater);
            view = vi.inflate(resource, null);
        }
        
        // Get the text boxes from the list_item.xml file
        TextView tagName = (TextView) view;
 
        // Assign the appropriate data from tag object
        tagName.setText(tag.getName());
        
        return view;
    }

}
