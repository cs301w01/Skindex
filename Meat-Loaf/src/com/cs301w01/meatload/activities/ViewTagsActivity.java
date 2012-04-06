package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Collection;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.TagAdapter;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.gallery.TagsGallery;
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
public class ViewTagsActivity extends Skindactivity {

	private MainManager mainManager;

	// adapters
	private TagAdapter allTagsAdapter;
	private TagAdapter selectedTagsAdapter;

	// arraylist needed for second listview
	private ArrayList<Tag> selectedTags;
	private ArrayList<Tag> filteredAllTags;
	private ArrayList<Tag> allTags;

	// list views
	private ListView allTagsLV;
	private ListView selectedTagsLV;

	// tag search edit text
	private EditText searchField;

	// current picture count view
	private TextView pictureCount;

	// @Override
	public void update(Object model) {
		refreshScreen();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewtags);

		mainManager = new MainManager(this);

		pictureCount = (TextView) findViewById(R.id.tagPicCountValue);
		searchField = (EditText) findViewById(R.id.tagSearchEditText);

		createListViews();
		createListeners();
		updateSelectedTagPictureCount();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mainManager.setContext(this);
		refreshScreen();
	}

	protected void createListeners() {

		// set listener for auto complete update, filters allTagsLV
		searchField.setOnKeyListener(new View.OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				filterAllTagsListView();
				return false;
			}
		});

		final Button viewPicturesButton = (Button) findViewById(R.id.viewSelections);
		viewPicturesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				onViewPictureClick();

			}
		});

		allTagsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View v, int pos, long arg3) {

				Tag selectedTag = (Tag) adapter.getItemAtPosition(pos);

				filteredAllTags.remove(selectedTag);
				selectedTags.add(selectedTag);

				updateSelectedTagPictureCount();

				allTagsAdapter.notifyDataSetChanged();
				selectedTagsAdapter.notifyDataSetChanged();

			}

		});

		selectedTagsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View v, int pos, long arg3) {

				Tag selectedTag = (Tag) adapter.getItemAtPosition(pos);

				selectedTags.remove(selectedTag);
				updateSelectedTagPictureCount();
				selectedTagsAdapter.notifyDataSetChanged();

				filterAllTagsListView();
			}

		});

		// tagListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	
	private void refreshScreen() {
		createListViews();
		createListeners();
		updateSelectedTagPictureCount();
	}

	private void updateSelectedTagPictureCount() {

		// if(selectedTags.size() > 0) {

		PictureQueryGenerator pQG = new PictureQueryGenerator(this);
		int pCount = pQG.getPictureCountByTags(convertTagsToStringArray(selectedTags));

		pictureCount.setText(Integer.toString(pCount));

		// } else
		// pictureCount.setText("0");

	}

	private Collection<String> convertTagsToStringArray(ArrayList<Tag> selectedTags) {

		Collection<String> tagNames = new ArrayList<String>();

		for (Tag tag : selectedTags) {

			tagNames.add(tag.getName());

		}

		return tagNames;

	}

	private void onViewPictureClick() {

		TagsGallery tG = new TagsGallery(new ArrayList<String>(
				convertTagsToStringArray(selectedTags)));

		Intent myIntent = new Intent();
		myIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.GalleryActivity");
		myIntent.putExtra("gallery", tG);

		startActivity(myIntent);

	}

	private void filterAllTagsListView() {
		String filterText = searchField.getText().toString();
		filteredAllTags = new ArrayList<Tag>();
		// filter out non-matches
		for (Tag tag : allTags) {

			if (tag.getName().contains(filterText)) {
				boolean tagSelected = false;
				for (Tag selectedTag : selectedTags) {
					if (selectedTag.getName().equals(tag.getName())) {
						tagSelected = true;
						break;
					}
				}

				if (!tagSelected)
					filteredAllTags.add(tag);

			}

		}

		// allTagsAdapter.notifyDataSetChanged();
		allTagsLV = (ListView) findViewById(R.id.tagListView);
		allTagsAdapter = new TagAdapter(this, R.layout.list_item, filteredAllTags);
		allTagsLV.setAdapter(allTagsAdapter);

	}

	private void createListViews() {

		selectedTags = new ArrayList<Tag>();
		allTags = mainManager.getAllTags();
		filteredAllTags = new ArrayList<Tag>();
		filteredAllTags.addAll(allTags);

		// set top list
		allTagsLV = (ListView) findViewById(R.id.tagListView);
		allTagsAdapter = new TagAdapter(this, R.layout.list_item, filteredAllTags);
		allTagsLV.setAdapter(allTagsAdapter);

		// create bottom list
		selectedTagsLV = (ListView) findViewById(R.id.selectedTagsListView);
		selectedTagsAdapter = new TagAdapter(this, R.layout.list_item, selectedTags);
		selectedTagsLV.setAdapter(selectedTagsAdapter);

	}

}
