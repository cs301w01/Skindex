package com.cs301w01.meatload.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * Implements Controller logic for Picture objects.
 * 
 * @author Isaac Matichuk
 */
public class PictureManager implements FController {

	private Context context;
	private int picID;
	
	//This is a temporary list of tags on the picture
	//This list will change as the user adds and removes tags
	//No changes will take effect until the user presses save
	private ArrayList<Tag> currentTags;
	
	//These two ArrayLists are temporary lists of added and removed tags, When the user presses save
	//The system will add all the addedTags to the DB and remove all the removedTags.
	private ArrayList<String> addedTags;
	private ArrayList<String> removedTags;

	/**
	 * Constructor, sets Context for db use and set the picture ID to the ID of the supplied picture
	 * @param context
	 * @param picture
	 */
	public PictureManager(Context context, Picture picture) {
		setContext(context);
		this.picID = picture.getPictureID();
		currentTags = new PictureQueryGenerator(context).selectPictureByID(picID).getTags();
		addedTags = new ArrayList<String>();
		removedTags = new ArrayList<String>();
	}

	/**
	 * Constructor, creates a PictureManager and sets the Picture ID to the supplied int.
	 * @param picID
	 */
	public PictureManager(Context context, int picID) {
		setContext(context);
		this.picID = picID;
		currentTags = new PictureQueryGenerator(context).selectPictureByID(picID).getTags();
		addedTags = new ArrayList<String>();
		removedTags = new ArrayList<String>();
	}

	/**
	 * Sets the current PictureManager's Context.  A context is necessary if
	 * the database is going to be used.
	 * The context is set upon creation of the manager object
	 * but this context is invalidated as soon as the user leaves the screen
	 * so every activity that stores a manager needs to update the context
	 * on resume
	 * @param Context
	 */
	public void setContext(Context context) {
		this.context = context;
	}
	
	/**
	 * Updates the database to reflect any changes that were made to the fields in the
	 * edit picture activity
	 * @param picture New Picture with updated information.
	 */
	public void savePicture(Picture picture) {
		picture.setID(picID);
		PictureQueryGenerator pQG = new PictureQueryGenerator(context);
		pQG.updatePictureByID(picture);
		saveTags();
	}

	/**
	 * Returns a picture object of the picture that is associated with this PictureManager
	 * @return Picture
	 */
	public Picture getPicture() {
		Picture newpic = new PictureQueryGenerator(context).selectPictureByID(picID);
		return newpic;
	}

	/**
	 * Delete the Picture associated with this PictureManager object in the
	 * database.
	 * 
	 * @see PictureQueryGenerator
	 */
	public void deletePicture() {
		new PictureQueryGenerator(context).deletePictureByID(picID);
	}

	/**
	 * Adds a new tag to a picture, using a String instead of a tag object.
	 * Note: this change does not take effect in the database until the user presses save
	 * 
	 * @param tagName
	 *            String object representing the tag to be added to this picture
	 */
	public void addTag(String tagName) {
		if(!addedTags.contains(tagName)) {
			boolean tagExists = false;
			for(Tag tag : currentTags) {
				if(tag.getName().equals(tagName)) {
					tagExists = true;
					break;
				}
			}
			if(!tagExists) {
				addedTags.add(tagName);
				currentTags.add(new Tag(tagName, new TagQueryGenerator(context).getTagPictureCount(tagName)));
			}
			removedTags.remove(tagName);
		}
		Log.d("Tag Query", "Added tag " + tagName + " to picture " + picID);
	}
	
	/**
	 * Remove a tag from the picture associated with this PictureManager object.
	 * Note: this change does not take effect in the database until the user presses save
	 * 
	 * @param tag
	 *            Tag to be removed.
	 */
	public void deleteTag(String tagName) {
		if(addedTags.contains(tagName)) {
			addedTags.remove(tagName);
		}
		else {
			removedTags.add(tagName);
		}
			for(Tag tag : currentTags) {
				if(tag.getName().equals(tagName)) {
					currentTags.remove(tag);
				}
			}
	}
	
	/**
	 * Returns the current unsaved list of tags on the current picture
	 * this list is not necessarily consistent with what is in the database
	 * because this includes the users current unsaved changes
	 * @return ArrayList<Tag>
	 */
	public ArrayList<Tag> getTags() {
		return currentTags;
	}

	/**
	 * Sets the tags of the picture associated with this object to the passed
	 * Collection of tags
	 * 
	 * @param tags
	 *            Collection of tags to set the picture's tags to
	 */
	private void saveTags() {
		TagQueryGenerator TQG = new TagQueryGenerator(context);
		TQG.addTagsToPicture(picID, addedTags);
		TQG.deleteTagsFromPicture(picID, removedTags);
	}
}
