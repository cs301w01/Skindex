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

/**
 * Implements the Controller logic required to create a Picture in the database. 
 * 
 * @author Blake Bouchard
 */
public class PictureCreator {

	private Context context;
	
	public PictureCreator(Context context) {
		this.context = context;
	}

	/**
	 * Saves the Bitmap provided to the file path provided and adds the
	 * appropriate information to the DB.
	 * 
	 * @see <a
	 *      href=http://stackoverflow.com/questions/649154/android-bitmap-save
	 *      -to
	 *      -location>http://stackoverflow.com/questions/649154/android-bitmap
	 *      -save-to-location</a>
	 * @param path
	 *            File directory where the Picture is to be saved
	 * @param imgOnDisplay
	 *            Bitmap to save
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

	private Picture createPicture(String fpath, Date date, String fname,
			String albumName) {

		Picture newPic = new Picture("", fpath, albumName, date,
				new ArrayList<Tag>());

		newPic.setID((int) new PictureQueryGenerator(context)
				.insertPicture(newPic));
		Log.d("SAVE", "Saving " + fpath);

		return newPic;
	}
}
