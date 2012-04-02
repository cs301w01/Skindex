package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;
import java.util.Date;

import com.cs301w01.meatload.activities.TakePictureActivity;
import com.cs301w01.meatload.controllers.PictureManager;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.PictureGenerator;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

import android.content.Context;
import android.graphics.Bitmap;

public class DatabaseTestingTools {

	static long[] populateAlbums(Context context){
		long aid[] = new long[4];
		AlbumQueryGenerator albumQGen = new AlbumQueryGenerator(context);
		aid[0] = albumQGen.insertAlbum("Album 1");
		aid[1] = albumQGen.insertAlbum("Album 2");
		aid[2] = albumQGen.insertAlbum("Album 3");
		aid[3] = albumQGen.insertAlbum("Album 4");	
		return aid;
	}
	
	static Picture[] populatePictures(Context context){
		PictureGenerator picGen = new PictureGenerator();
		Picture[] picList = new Picture[7];
		
		picList[0] = new PictureManager(context,"Album 1").takePicture(context.getFilesDir(),
				picGen.generatePicture());
		picList[1] = new PictureManager(context,"Album 1").takePicture(context.getFilesDir(),
				picGen.generatePicture());
		picList[2] = new PictureManager(context,"Album 1").takePicture(context.getFilesDir(),
				picGen.generatePicture());
		picList[3] = new PictureManager(context,"Album 1").takePicture(context.getFilesDir(),
				picGen.generatePicture());	
		picList[4] = new PictureManager(context,"Album 3").takePicture(context.getFilesDir(),
				picGen.generatePicture());
		picList[5] = new PictureManager(context,"Album 3").takePicture(context.getFilesDir(),
				picGen.generatePicture());
		picList[6] = new PictureManager(context,"Album 4").takePicture(context.getFilesDir(),
				picGen.generatePicture());
		
		return picList;		
	}
}
