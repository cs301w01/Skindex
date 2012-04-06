package com.cs301w01.meatload.adapters;

import java.util.List;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.model.Album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Adapter for populating a ListView with album names and picture counts of those albums.
 * @author Blake Bouchard
 * @see <a href="http://www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview/">
 http://www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview/</a>
 */
public class AlbumAdapter extends ArrayAdapter<Album> {

	int resource;
	String response;
	Context context;
	public AlbumAdapter(Context context, int textViewResourceId, List<Album> albums) {
		super(context, textViewResourceId, albums);
		this.resource = textViewResourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout albumListItem;
        // Get current album object
        Album album = getItem(position);
 
        // Inflate the view
        if (convertView == null) {
            albumListItem = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, albumListItem, true);
        } else {
            albumListItem = (LinearLayout) convertView;
        }
        
        // Get the text boxes from the list_item.xml file
        TextView albumName = (TextView) albumListItem.findViewById(R.id.itemName);
        TextView albumCount = (TextView) albumListItem.findViewById(R.id.itemValue);
 
        // Assign the appropriate data from album object
        albumName.setText(album.getName());
        albumCount.setText(Integer.toString(album.getPictureCount()));
 
        return albumListItem;
    }
}
