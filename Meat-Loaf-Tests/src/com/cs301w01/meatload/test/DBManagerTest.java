package com.cs301w01.meatload.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;
import com.cs301w01.meatload.*;
import com.cs301w01.meatload.model.DBManager;

public class DBManagerTest extends AndroidTestCase {
	DBManager mClassToTest;
 
	protected void setUp() throws Exception {
		Context c = getContext();
		mClassToTest = new DBManager(c);
		populateDB1();
		super.setUp();
	}
 
	protected void tearDown() throws Exception {
		mClassToTest.resetDB();
		mClassToTest.close();
		super.tearDown();
	}
	
	//PopulateDB1 inserts 4 albums, this asserts 4 albums returned by selectAllAlbums
	public void testPopulateDB1(){		
		assertEquals(mClassToTest.selectAllAlbums().size(), 4);
	}
	
	//an Album with a blank string should not be inserted
	//In the future this could change if it creates with default unique name
	public void testInsertEmptyAlbum(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.insertAlbum("", new ArrayList<String>());
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//nulls not valid input, insert fails
	public void testInsertNullTagsAlbum(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.insertAlbum("test", null);
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//nulls not valid input, insert fails
	public void testInsertNullNameAlbum(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.insertAlbum(null, new ArrayList<String>());
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
		
	//Insert 3 albums, size should grow by 3
	public void testInsertMultipleAlbums(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		
		ArrayList<String> tag1 = new ArrayList<String>();
		tag1.add("red");
		ArrayList<String> tag2 = new ArrayList<String>();
		tag1.add("Larry Bird");tag1.add("Elf");tag1.add("MoleyMole");
		ArrayList<String> tag3 = new ArrayList<String>();
		tag1.add("blood");tag1.add("puss");tag1.add("bruise");tag1.add("ear");
		
		mClassToTest.insertAlbum("TestAlbum 111", tag1);
		mClassToTest.insertAlbum("TestAlbum 222", tag2);
		mClassToTest.insertAlbum("TestAlbum 333", tag3);
		
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize + 3);
		
	}
	
	//Albums cannot have the same name, only the first should be added
	public void testInsertAlbumSameName(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		
		mClassToTest.insertAlbum("ThisIsAName", new ArrayList<String>());
		mClassToTest.insertAlbum("ThisIsAName", new ArrayList<String>());
		
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize + 1);
	}


	//deleteAlbum no string, delete fails does not affect size
	public void testDeleteAlbumNoName(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		
		mClassToTest.deleteAlbumByName("");
		
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//deleteAlbum create and delete, size constant
	public void testCreateDeleteAlbum(){
		ArrayList<HashMap<String,String>> albumList;
		boolean test = false;
		
		mClassToTest.insertAlbum("DeleteThis", new ArrayList<String>());
		mClassToTest.deleteAlbumByName("DeleteThis");
		albumList = mClassToTest.selectAllAlbums();
		
		for(int i = 0; i < albumList.size(); i++){
			if (albumList.get(i).get("DeleteThis") != null){
				test = true;
			}
		}
		assertFalse(test);
	}
	
	//deleteAlbum name not in database
	public void testDeleteNonExistingAlbum(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.deleteAlbumByName("ThisNameDoesNotExist");
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//deleteAlbum name == null
	public void testDeleteAlbumNull(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.deleteAlbumByName(null);
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//delete same album twice
	public void testDeleteAlbumTwice(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.deleteAlbumByName("DeleteOnce");
		mClassToTest.deleteAlbumByName("DeleteOnce");
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize - 1);
	}
	
	//Case insensitive comparisons of album name
	public void testCaseSensitiveAlbum(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.insertAlbum("CaPiTaL", new ArrayList<String>());
		mClassToTest.deleteAlbumByName("cApItAl");
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//Try to add an album with the same name but different cases, only add one
	public void testAddSameNameAlbumDifferentCase(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.insertAlbum("CaPiTaL", new ArrayList<String>());
		mClassToTest.insertAlbum("cApItAl", new ArrayList<String>());
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize + 1);
	}
	
	/*
	IMPLEMENT THESE TESTS
	public void resetDB()
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
	public int updatePhotoByID(Photo p, String tableName, int id)
	public void insertPhoto(Photo p)
	public int getTotalPhotos()
	public Collection<Tag> selectAllTags() 
	public Collection<String> selectPhotoTags(int photoID)
	public ArrayList<HashMap<String,String>> selectAllPhotos()
	public Photo selectPhotoByID(int photoID)
	public void deletePhotoByID(int photoID)
	public String getAlbumNameOfPhoto(int photoID) 
	public ArrayList<HashMap<String, String>> selectPhotosFromAlbum(String albumName) 
	public ArrayList<HashMap<String, String>> selectPhotosByTag(Collection<String> tags)
	public String dateToString(Date date)
	public Date stringToDate(String date)
	public String stringJoin(Collection<String> strings, String delimiter)
	
	THESE ARE TESTED
	public void insertAlbum(String albumName, Collection<String> tags)
	public ArrayList<HashMap<String,String>> selectAllAlbums()
	public void deleteAlbumByName(String name)
	*/
	
	
	
	public void populateDB1(){
		ArrayList<String> tag1 = new ArrayList<String>();
		tag1.add("red");tag1.add("blue");tag1.add("green");tag1.add("orange");
		ArrayList<String> tag2 = new ArrayList<String>();
		tag1.add("red");tag1.add("blue");tag1.add("green");tag1.add("orange");
		ArrayList<String> tag3 = new ArrayList<String>();
		tag1.add("blood");tag1.add("puss");tag1.add("bruise");tag1.add("ear");
		
		mClassToTest.insertAlbum("Album 1", tag1);
		mClassToTest.insertAlbum("Album 2", tag2);
		mClassToTest.insertAlbum("Album 3", tag3);
		mClassToTest.insertAlbum("Album 4", new ArrayList<String>());
	}

}