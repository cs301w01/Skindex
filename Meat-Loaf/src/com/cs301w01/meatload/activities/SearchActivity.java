package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Date;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.model.SearchGallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Implements the login in the Search tab of the main landing page of Skindex.
 * 
 * @author Isaac Matichuk
 */
public class SearchActivity extends Skindactivity {

	private EditText searchNameField;
	private DatePicker startDate;
	private DatePicker endDate;

	public void update(Object model) {

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search);

		searchNameField = (EditText) findViewById(R.id.search_pic_name);
		startDate = (DatePicker) findViewById(R.id.start_date);
		endDate = (DatePicker) findViewById(R.id.end_date);

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

		Date sDate = new Date(startDate.getYear() - 1900, startDate.getMonth(),
				startDate.getDayOfMonth());
		Date eDate = new Date(endDate.getYear() - 1900, endDate.getMonth(),
				endDate.getDayOfMonth() + 1);
		SearchGallery sG = new SearchGallery(searchNameField.getText().toString(), sDate, eDate,
				new ArrayList<String>());

		Intent myIntent = new Intent();
		myIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.GalleryActivity");
		myIntent.putExtra("gallery", sG);

		startActivity(myIntent);

	}

}
