package com.cs301w01.meatload.model.querygenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.Tag;

/**
 * Creates queries dealing with Pictures in the database.
 * <p>
 * Converts values from the database to usable data by the rest of the application. 
 * 
 * @author Derek Dowling
 *
 */
public class PictureQueryGenerator extends QueryGenerator {

	// vars for pictures table
	public static final String TABLE_NAME = "pictures";
	public static final String PICTURES_COL_PATH = "path";
	public static final String PICTURES_COL_DATE = "date";

	public static final String CREATE_TABLE_QUERY = 
								"CREATE TABLE " + TABLE_NAME + 
								" (" + COL_ID + " INTEGER PRIMARY KEY, " + 
								PICTURES_COL_DATE + " Date, " + 
								PICTURES_COL_PATH + " TEXT, " + 
								COL_NAME + " TEXT, " + 
								COL_ALBUMID + " INTEGER, " + 
									"FOREIGN KEY(" + COL_ALBUMID + ") REFERENCES " + 
									AlbumQueryGenerator.TABLE_NAME + "( " + COL_ID + "));";

	/**
	 * Constructor, creates a PictureQueryGenerator with the given Context.
	 * 
	 * @param context
	 *            To be used in Database operations.
	 */
	public PictureQueryGenerator(Context context) {
		super(context);

	}

	/**
	 * Takes a Picture object and pushes it to the database.
	 * Note: this function does not update the tags,
	 * Use addTagsToPicture and deleteTagsFromPicture in tagQueryGenerator for this
	 * 
	 * @param p
	 *            Picture object to be pushed to database
	 * @param id
	 *            ID value
	 */
	public void updatePictureByID(Picture p) {

		ContentValues cv = new ContentValues();
		
		AlbumQueryGenerator AQG = new AlbumQueryGenerator(this.context);
		
		int albumID = AQG.selectAlbumIDByName(p.getAlbumName());

		// add picture info to cv
		cv.put(COL_NAME, p.getName());
		cv.put(COL_ALBUMID, albumID);

		db.update(TABLE_NAME, cv, COL_ID + "=" + p.getPictureID(), null);
		
		AQG.setAlbumModified((long)albumID);
	}

	/**
	 * Inserts a picture into the pictures table and corresponding tags into the
	 * tag table.
	 * 
	 * @param p
	 *            Picture to be inserted
	 * @return long ID of picture in database
	 */
	public long insertPicture(Picture p) {

		ContentValues cv = new ContentValues();

		AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
		int albumID = albumGen.selectAlbumIDByName(p.getAlbumName());

		// add picture info to cv
		cv.put(PICTURES_COL_DATE, dateToString(p.getDate()));
		cv.put(PICTURES_COL_PATH, p.getPath());
		cv.put(COL_ALBUMID, albumID);
		cv.put(COL_NAME, p.getName());
		Log.d(TABLE_NAME, "ALBUM NAME OF Picture: " + p.getAlbumName());

		// insert picture into picture tables
		long pid = db.insert(TABLE_NAME, COL_ID, cv);

		// insert picture's tags into tags table
		for (Tag tag : p.getTags()) {

			ContentValues tcv = new ContentValues();

			cv.put(COL_NAME, tag.getName());
			cv.put(COL_PICTUREID, pid);

			// insert tag tuple into tags table
			db.insert(TagQueryGenerator.TABLE_NAME, COL_ID, tcv);

			Log.d(TABLE_NAME, "Tag inserted: " + tag.getName() + " w/ pid: " + pid);

		}
		
		albumGen.setAlbumModified((long)albumID);

		return pid;
	}

	/**
	 * Returns the total number of pictures in the database.
	 * 
	 * @return int
	 */
	public int getPictureCount() {

		Cursor c = db.performRawQuery("SELECT COUNT(*) AS numPictures FROM " + TABLE_NAME);

		if (c == null) {
			return 0;
		}

		int picCount = Integer.parseInt(c.getString(c.getColumnIndex("numPictures")));

		c.close();

		return picCount;

	}

	/**
	 * Returns a Picture object from the database using the provided Picture ID.
	 * 
	 * @param pictureID
	 *            The ID of the picture to return.
	 * @return Picture
	 */
	public Picture selectPictureByID(int pictureID) {

		Cursor c = db.performRawQuery("SELECT * " + "FROM " + TABLE_NAME + " WHERE " + COL_ID
				+ " = '" + pictureID + "'");

		if (c == null) {
			return null;
		}

		AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
		String albumName = albumGen.getAlbumNameOfPicture(c.getInt(c.getColumnIndex(COL_ID)));

		String pictureName = c.getString(c.getColumnIndex(COL_NAME));
		String path = c.getString(c.getColumnIndex(PICTURES_COL_PATH));
		Date date = stringToDate(c.getString(c.getColumnIndex(PICTURES_COL_DATE)));

		TagQueryGenerator tagGen = new TagQueryGenerator(this.context);
		Collection<Tag> tags = tagGen.selectPictureTags(c.getInt(c.getColumnIndex(COL_ID)));

		Picture p = new Picture(pictureName, path, albumName, date, (ArrayList<Tag>) tags);
		p.setID(pictureID);

		c.close();

		return p;

	}

	/**
	 * Deletes a given Picture, specified by ID, from the database.
	 * 
	 * @param pictureID
	 */
	public void deletePictureByID(int pictureID) {

		deleteByID(pictureID, TABLE_NAME);
		
		AlbumQueryGenerator AQG = new AlbumQueryGenerator(this.context);
		AQG.setAlbumModified(getAlbumIdOfPicture(pictureID));

		new TagQueryGenerator(context).deleteAllTagsFromPicture(pictureID);
	}
	
	/**
	 * Returns a collection of Picture objects representing all pictures in the
	 * database.
	 * 
	 * @return Collection<Picture>
	 */
	public Collection<Picture> selectAllPictures() {

		return selectPicturesByQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY "
				+ PICTURES_COL_DATE);
	}

	/**
	 * Gets all Pictures from an album from database.
	 * 
	 * @param albumName the name of the album
	 * @return Collection<Picture>
	 */
	public Collection<Picture> selectPicturesFromAlbum(int albumID) {

		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ALBUMID + " = " + "'"
				+ albumID + "'" + " ORDER BY " + PICTURES_COL_DATE;

		return selectPicturesByQuery(query);

	}
	
	private String getTagsQuery(Collection<String> tags) {
		if (tags.isEmpty()) {
			return "";
		}
		
		String query = "SELECT p." + COL_NAME + " AS " + COL_NAME + ", p." + PICTURES_COL_PATH
						+ " AS " + PICTURES_COL_PATH + ", p." + COL_ID + " AS " + COL_ID + ", p."
						+ PICTURES_COL_DATE + " AS " + PICTURES_COL_DATE + " FROM " + TABLE_NAME
						+ " p LEFT JOIN " + TagQueryGenerator.TABLE_NAME + " t ON (p." + COL_ID + " = t."
						+ COL_PICTUREID + ") WHERE ";

		boolean loopedOnce = false;
		
		for (String tag : tags) {
		
			if (loopedOnce) {
				query += " OR ";
			}
			
			loopedOnce = true;
		
			query += "t." + COL_NAME + " = '" + tag + "'";
		}
		
		query += " GROUP BY p." + COL_ID + " HAVING COUNT(t." + COL_ID + ") = " + tags.size();
		
		return query;
	}

	/**
	 * Gets all pictures with the given tags from the database.
	 * 
	 * @param tags
	 *            A collection of Tags represented as Strings to be used in the
	 *            query
	 * @return Collection<Picture>
	 */
	public Collection<Picture> selectPicturesByTags(Collection<String> tags) {

		if (tags.size() == 0) {
			return selectAllPictures();
		}
		
		String query = getTagsQuery(tags);

		return selectPicturesByQuery(query);

	}
	
	public Collection<Picture> selectPicturesBySearch(String picName, Date startDate, Date endDate,
			ArrayList<String> tagNames) {
		if (picName.equals("") && startDate == null && endDate == null && tagNames.isEmpty()) {
			return selectAllPictures();
		}
		
		String tagsQuery = getTagsQuery(tagNames);
		String searchQuery = "SELECT * " +
								" FROM " + TABLE_NAME +
								" WHERE ";
		if (!picName.equals("")) {
			searchQuery += COL_NAME + " LIKE '%" + picName + "%' ";
		}
		
		if (startDate != null) {
			if (!picName.equals("")) {
				searchQuery += "AND ";
			}
			searchQuery += PICTURES_COL_DATE + " >= '" + dateToString(startDate) + "' ";
		}
		
		if (endDate != null) {
			if(!picName.equals("") || startDate != null)
				searchQuery += "AND ";
			searchQuery += PICTURES_COL_DATE + " <= '" + dateToString(endDate) + "' ";
		}
		
		String query = "";
		
		if (tagNames.isEmpty()) {
			query = searchQuery;
		} else if (picName.equals("") && startDate == null && endDate == null) {
			query = tagsQuery;
		} else {
			query = tagsQuery + " INTERSECT " + searchQuery;
		}
		
		return selectPicturesByQuery(query);
	}

	/**
	 * Returns the number of unique Pictures that have one or more of the tags
	 * in the provided collection.
	 * 
	 * @param tags
	 * 			  A collection of Tags represented as Strings to be used in the
	 *            query
	 * @return int
	 */
	public int getPictureCountByTags(Collection<String> tags) {

		return selectPicturesByTags(tags).size();

	}

	/**
	 * Deletes all pictures from an Album specified by its ID.
	 * 
	 * @param albumID
	 */
	public void deletePicturesFromAlbum(int albumID) {

		new TagQueryGenerator(this.context).deleteAllTagsFromAlbum(albumID);
		
		String deletePictureQuery = "DELETE FROM " + TABLE_NAME + 
									" WHERE " + COL_ALBUMID + " = '" + albumID + "'";

		Log.d(TABLE_NAME, "Performing delete: " + deletePictureQuery);

		db.performRawQuery(deletePictureQuery);
	}
	
	/**
	 * Returns the album ID of the album that the given picture belongs to
	 * 
	 * @param pictureID The ID of the picture in question
	 * 
	 * @return int
	 */
	public int getAlbumIdOfPicture(int pictureID) {
		String query = "SELECT " + COL_ALBUMID + 
						" FROM " + TABLE_NAME + 
						" WHERE " + COL_ID + " = '" + pictureID + "'";
		
		Cursor c = db.performRawQuery(query);
		
		if(c == null) {
			return 0;
		}
		
		return Integer.parseInt(c.getString(c.getColumnIndex(COL_ALBUMID)));
	}

	private Collection<Picture> selectPicturesByQuery(String pictureQuery) {

		Cursor c = db.performRawQuery(pictureQuery);
		ArrayList<Picture> pictures = new ArrayList<Picture>();

		if (c == null) {
			return pictures;
		}

		while (!c.isAfterLast()) {

			AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
			String albumName = albumGen.getAlbumNameOfPicture(c.getInt(c.getColumnIndex(COL_ID)));

			int id = Integer.parseInt(c.getString(c.getColumnIndex(COL_ID)));
			String path = c.getString(c.getColumnIndex(PICTURES_COL_PATH));
			Date date = stringToDate(c.getString(c.getColumnIndex(PICTURES_COL_DATE)));
			String name = c.getString(c.getColumnIndex(COL_NAME));

			// String name, String path, String album, Date date,
			// Collection<String> tags
			Picture p = new Picture(name, path, albumName, date,
					(ArrayList<Tag>) new TagQueryGenerator(context).selectPictureTags(id));
			p.setID(id);

			pictures.add(p);

			c.moveToNext();
		}

		c.close();

		return pictures;
	}

}
