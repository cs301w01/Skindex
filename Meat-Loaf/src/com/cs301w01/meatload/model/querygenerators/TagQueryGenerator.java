package com.cs301w01.meatload.model.querygenerators;

import java.util.ArrayList;
import java.util.Collection;

import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.Tag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class TagQueryGenerator extends QueryGenerator {
	
	 //vars for tags table
    public static final String TABLE_NAME = "tags";

    public static final String CREATE_TABLE_QUERY =
        "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_PICTUREID + " INTEGER, " +
            COL_NAME + " TEXT, " +
            "FOREIGN KEY(" + COL_PICTUREID + ") REFERENCES " +
            	PictureQueryGenerator.TABLE_NAME + "( " + COL_ID + "));";
    
    public TagQueryGenerator(Context context) {
		super(context);
	}

	private ArrayList<Tag> selectTagsByQuery(String tagQuery) {
    	
		Cursor c = db.performRawQuery(tagQuery);
    	
    	ArrayList<Tag> tags = new ArrayList<Tag>();
    	
    	if (c == null) {
    		return tags;
    	}

        while (!c.isAfterLast()) {
            
            String tagName = c.getString(c.getColumnIndex(COL_NAME));
            int numPictures = new Integer(c.getString(c.getColumnIndex("numPictures")));
            
            tags.add(new Tag(tagName, numPictures));

            c.moveToNext();
        }

        return tags;
    }
    
    /**
     * Return all tags associated with a picture.
     * @param pictureID
     * @return Collection of Strings
     */
    public ArrayList<Tag> selectPictureTags(int pictureID) {
        
        String getTags = "SELECT " + COL_NAME +
        					" FROM " + TagQueryGenerator.TABLE_NAME +
        					" WHERE " + COL_PICTUREID + " = '" + pictureID + "'";
        
        return selectTagsByQuery(getTags);
        
    }
    
    public ArrayList<Tag> selectAllTags() {
    	
    	String tagQuery = "SELECT t." + COL_NAME + " AS " + COL_NAME + ", COUNT(*) AS numPictures" 
    						+ 
    						" FROM " + TABLE_NAME + 
    						" t LEFT JOIN " + PictureQueryGenerator.TABLE_NAME +
    						" p ON (t." + COL_PICTUREID + " = p." + COL_ID + ")" + 
    						" GROUP BY t." + COL_NAME;
    	
    	return selectTagsByQuery(tagQuery);
    }
    
    public int getTagPictureCount(String tagName) {
    	String countQuery = "SELECT COUNT(*) AS numPictures" + 
    						" FROM " + TABLE_NAME +
    						" t LEFT JOIN " + PictureQueryGenerator.TABLE_NAME +
    						" P ON (t." + COL_PICTUREID + " = p." + COL_ID + ")" +
    						" WHERE t." + COL_NAME + " = '" + tagName + "'";
    	
    	Cursor c = db.performRawQuery(countQuery);
    	
    	if (c == null) {
    		return 0;
    	}
            return new Integer(c.getString(c.getColumnIndex("numPictures")));
    }
    
    public void updateTags(int pictureID, ArrayList<Tag> tags) {
    	ArrayList<Tag> oldTags = selectPictureTags(pictureID);
    	for (Tag tag : tags) {
    		if(!oldTags.contains(tag)) {
    			addTag(pictureID, tag);
    		}
    	}
    }
    
    public long addTag(int pictureID, Tag tag) {
    	ContentValues cv = new ContentValues();
        
        //add tag info to cv
        cv.put(COL_NAME, tag.getName());
        cv.put(COL_PICTUREID, pictureID);

        //insert picture into picture tables
        return db.insert(TABLE_NAME, COL_ID, cv);
    }
    
    /**
    * Performs a join on a collection of Strings with a delimiter between each String.
    * @param strings Collection of Strings to be joined
    * @param delimiter String to be placed between each String
    * @return String representation of joined Strings
    */
    public String stringJoin(Collection<String> strings, String delimiter) {
		 String newString = "";
		 boolean isFirst = true;
		 
		 for (String curr : strings) {
			 newString += curr;
			 
			 if (isFirst) {
				 isFirst = false;
				 newString += delimiter;
			 }
		 }	 
		 return newString;
    }    
}
