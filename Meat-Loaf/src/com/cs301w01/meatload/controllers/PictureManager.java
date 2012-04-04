package com.cs301w01.meatload.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
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
	//private Bitmap imgOnDisplay;
	private ArrayList<Tag> currentTags;
	private ArrayList<String> addedTags;
	private ArrayList<String> removedTags;

	/**
	 * Constructor, populates PictureManager with info from Picture in args.
	 * @param picture
	 */
	public PictureManager(Picture picture) {
		this.picID = picture.getPictureID();
		currentTags = picture.getTags();
		addedTags = new ArrayList<String>();
		removedTags = new ArrayList<String>();
	}

	/**
	 * Constructor, sets Context for db use and populates PictureManager with info
	 * from Picture in args.
	 * @param context
	 * @param picture
	 */
	public PictureManager(Context context, Picture picture) {
		this.context = context;
		this.picID = picture.getPictureID();
		currentTags = picture.getTags();
		addedTags = new ArrayList<String>();
		removedTags = new ArrayList<String>();
	}

	/**
	 * Constructor, creates a PictureManager and sets the Picture ID to the supplied int.
	 * @param picID
	 */
	public PictureManager(int picID) {
		this.picID = picID;
	}

	/**
	 * Constructor, only sets the Context.
	 * @param context
	 */
	public PictureManager(Context context) {
		this.context = context;
	}

	// public PictureManager(int pid) {
	// photoID = pid;
	// }

	// public PictureManager(String albumName) {
	// this.albumName = albumName;
	// }

	/**
	 * Sets the current PictureManager's Context.  A context is necessary if
	 * the database is going to be used.
	 * @param Context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Saves the Bitmap provided to the file path provided and adds the appropriate
	 * information to the DB.
	 * 
	 * @see <a href=http://stackoverflow.com/questions/649154/android-bitmap-save-to-location>http://stackoverflow.com/questions/649154/android-bitmap-save-to-location</a>
	 * @param path File directory where the Picture is to be saved
	 * @param imgOnDisplay Bitmap to save
	 */
	public Picture takePicture(File path, Bitmap imgOnDisplay, String albumName) {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-sss");
		String timestamp = sdf.format(cal.getTime());
		String fname = "img-" + timestamp + ".PNG";
		String fpath = path.toString() + "/";

		try {
			OutputStream outStream = null;
			File file = new File(path, fname);

			outStream = new FileOutputStream(file);
			imgOnDisplay.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();

			// adds the new picture to the db and returns a picture object
			return createPicture(fpath + fname, cal.getTime(), fname, albumName);

		} catch (IOException e) {
			Log.d("ERROR", "Unable to write " + fpath + fname);
			return null;
		}
		
	}
	
	/**
	 * Takes the current Picture object, and using it's unique ID, updates
	 * the databases to reflect the current information.
	 * @param picture New Picture with updated information.
	 */
	public void savePicture(Picture picture) {
		PictureQueryGenerator pQG = new PictureQueryGenerator(context);
		pQG.updatePictureByID(picture, PictureQueryGenerator.TABLE_NAME, picID);
		saveTags();
	}

	/**
	 * Get this picture
	 * @return Picture associated with this PictureManager
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
		new TagQueryGenerator(context).deleteAllTagsFromPicture(picID);
		new PictureQueryGenerator(context).deletePictureByID(picID);
	}

	/**
	 * Adds a new tag to a picture, using a String instead of a tag object.
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
	}
	
	/**
	 * Remove a tag from the picture associated with this PictureManager object.
	 * 
	 * @param tag
	 *            Tag to be deleted.
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
	
	private Picture createPicture(String fpath, Date date, String fname, String albumName) {

		Picture newPic = new Picture("", fpath,
				albumName, date, new ArrayList<Tag>());

		newPic.setID((int) new PictureQueryGenerator(context)
				.insertPicture(newPic));
		Log.d("SAVE", "Saving " + fpath);

		return newPic;
	}
}
