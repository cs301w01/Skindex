package com.cs301w01.meatload.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs301w01.meatload.model.Album;

public class SimpleAlbumAdapter extends AlbumAdapter {

	public SimpleAlbumAdapter(Context context, int textViewResourceId, List<Album> albums) {
		super(context, textViewResourceId, albums);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
        TextView albumListItem;
        // Get current album object
        Album album = getItem(position);
 
        // Inflate the view
        if (convertView == null) {
            albumListItem = new TextView(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, null);
        } else {
            albumListItem = (TextView) convertView;
        }
        
        albumListItem.setText(album.getName());
        
        return albumListItem;
    }
}
