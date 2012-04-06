package com.cs301w01.meatload.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs301w01.meatload.model.Album;

/**
 * Adapter for the Spinner Album View in EditPictureActivity that takes a
 * TextView as its view rather than a LinearLayout.
 * 
 * @author Blake Bouchard
 * @see AlbumAdapter
 * @see EditPictureActivity
 * @see <a href="http://www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview/">
 http://www.josecgomez.com/2010/05/03/android-putting-custom-objects-in-listview/</a>
 */
public class SpinnerAlbumAdapter extends AlbumAdapter {

	public SpinnerAlbumAdapter(Context context, int textViewResourceId, List<Album> albums) {
		super(context, textViewResourceId, albums);
		
		this.resource = textViewResourceId;
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
			albumListItem = (TextView) vi.inflate(resource, null);
		} else {
			albumListItem = (TextView) convertView;
		}

		albumListItem.setText(album.getName());
		albumListItem.setTextColor(Color.BLACK);
		albumListItem.setEllipsize(TruncateAt.END);
		albumListItem.setSingleLine(true);

		return albumListItem;
	}
}
