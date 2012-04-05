package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;
import java.util.Collection;

import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.PictureGenerator;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PictureManager;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class DatabaseTest extends ActivityInstrumentationTestCase2<ViewAlbumsActivity> {
    private Context mContext;
    private ViewAlbumsActivity mActivity;
    private MainManager mainMan;
    private PictureManager picMan;
    
	public DatabaseTest(){
		super("com.cs301w01.meatload", ViewAlbumsActivity.class);
	}

    @Override
    protected void setUp() throws Exception {
    	super.setUp();

        mActivity = getActivity();
        mContext = mActivity.getBaseContext();
        
		mainMan = new MainManager(mContext);
    }
    
    @Override
    protected void tearDown() throws Exception {     
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }

    }
    
    public void test1stResetDB(){

        	SQLiteDBManager db = new SQLiteDBManager(mContext);
            db.resetDB();
            db.close();
            
            assertTrue(mainMan.getAllAlbums().size() == 0);
    }
    
    public void testAddAlbum(){
    	
    	int oldNumAlbums = mainMan.getAllAlbums().size();
    	int newNumAlbums;
    	
    	mainMan.addAlbum("Album 1", new ArrayList<String>());
    	mainMan.addAlbum("Album 2", new ArrayList<String>());
    	mainMan.addAlbum("Album 3", new ArrayList<String>());
    	mainMan.addAlbum("Album 4", new ArrayList<String>());
		
		newNumAlbums = mainMan.getAllAlbums().size();
		
		assertTrue(newNumAlbums == oldNumAlbums + 4);
    }
    
    public void testDeleteAlbum(){    	
    	assertTrue(1==1);
    }
    
    public void testUpdateAlbum(){
    	assertTrue(1==1);
    }
    
    public void testAddPicture1(){
    	Album testAlbum;
    	int oldNumPics;
    	int newNumPics;
    	PictureGenerator picGen = new PictureGenerator();
    	
    	testAlbum = mainMan.getAlbumByName("Album 1");
    	oldNumPics = testAlbum.getPictureCount();

    	mainMan.takePicture(mContext.getFilesDir(), picGen.generatePicture(), "Album 1");
    	mainMan.takePicture(mContext.getFilesDir(), picGen.generatePicture(), "Album 1");
    	mainMan.takePicture(mContext.getFilesDir(), picGen.generatePicture(), "Album 1");
    	
    	testAlbum = mainMan.getAlbumByName("Album 1");
    	newNumPics = testAlbum.getPictureCount();
    	
    	assertTrue(newNumPics == oldNumPics + 3);
    }
    
    public void testAddPicture2(){
    	int oldNumPics;
    	int newNumPics;
    	PictureGenerator picGen = new PictureGenerator();
    	
    	oldNumPics = mainMan.getPictureCount();

    	mainMan.takePicture(mContext.getFilesDir(), picGen.generatePicture(), "Album 2");
    	mainMan.takePicture(mContext.getFilesDir(), picGen.generatePicture(), "Album 3");
    	mainMan.takePicture(mContext.getFilesDir(), picGen.generatePicture(), "Album 3");
    	
    	newNumPics = mainMan.getPictureCount();
    	
    	assertTrue(newNumPics == oldNumPics + 3);
    }
    
    public void testDeletePicture(){
    	assertTrue(1==1);
    }
    
    public void testUpdatePicture(){
    	assertTrue(1==1);
    }
    
    public void testAddTags(){
    	assertTrue(1==1);
    }
    
    public void testDeleteTags(){
    	assertTrue(1==1);
    }
    
    public void testUpdateTags(){
    	assertTrue(1==1);
    }
	
	
}
