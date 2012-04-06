package com.cs301w01.meatload.controllers;

import java.util.ArrayList;

import android.content.Context;

import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * Mediates between DBManager and Skindex by providing lists of Albums and Tags.
 * 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see Skindex
 */
public class MainManager implements FController {
	final private int MAX_TAG_NAME_LENGTH = 20;
	Context context;

	public MainManager(Context context) {
		setContext(context);
	}

	/**
	 * Sets the current GalleryManager's Context. A context is necessary if the
	 * database is going to be used. The context is set upon creation of the
	 * manager object but this context is invalidated as soon as the user leaves
	 * the screen so every activity that stores a manager needs to update the
	 * context on resume
	 * 
	 * @param Context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	public int getPictureCount() {
		return new PictureQueryGenerator(context).getPictureCount();
	}

	public ArrayList<Tag> getAllTags() {
		return new TagQueryGenerator(context).selectAllTags();
	}

	public int getMaxTagName() {
		return MAX_TAG_NAME_LENGTH;
	}
	
}
