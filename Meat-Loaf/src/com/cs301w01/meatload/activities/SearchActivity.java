package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Collection;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.TagAdapter;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.SearchGallery;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.TagsGallery;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Implements the logic in the Tags view of the Tab layout in Skindex.
 * 
 * @author Joel Burford
 */
public class SearchActivity extends Skindactivity {

	private EditText searchNameField;

	// @Override
	public void update(Object model) {

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search);
		
		searchNameField = (EditText) findViewById(R.id.search_pic_name);

		createListeners();

	}

	protected void createListeners() {

		final Button searchButton = (Button) findViewById(R.id.button_search);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				onSearchClick();

			}
		});
	}

	private void onSearchClick() {

		SearchGallery sG = new SearchGallery(searchNameField.getText().toString(), null, null, new ArrayList<String>());

		Intent myIntent = new Intent();
		myIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.GalleryActivity");
		myIntent.putExtra("gallery", sG);

		startActivity(myIntent);

	}

}
