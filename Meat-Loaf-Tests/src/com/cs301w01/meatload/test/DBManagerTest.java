package com.cs301w01.meatload.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.test.AndroidTestCase;
import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Picture;

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
		
		boolean insertSuccess = false;
		
		try {
			
			mClassToTest.insertAlbum("test", null);
			insertSuccess = true;
			
			
		} catch (NullPointerException e) {
			
			insertSuccess = false;
			
		}
		
		assertFalse(insertSuccess);
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
		
		boolean deleteSuccess = false;
		
		try {
			
			mClassToTest.deleteAlbumByName("");
			deleteSuccess = true;
			
			
		} catch (NullPointerException e) {
			
			deleteSuccess = false;
			
		}
		
		assertFalse(deleteSuccess);

		
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
		
		
		boolean deleteSuccess;

		try {
			
			mClassToTest.deleteAlbumByName("cApItAl");
			deleteSuccess = true;
			
			
		} catch (NullPointerException e) {
			
			deleteSuccess = false;
			
		}
		
		assertFalse(deleteSuccess);
		
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize);
	}
	
	//Try to add an album with the same name but different cases, only add one
	public void testAddSameNameAlbumDifferentCase(){
		int oldSize = mClassToTest.selectAllAlbums().size();
		mClassToTest.insertAlbum("CaPiTaL", new ArrayList<String>());
		mClassToTest.insertAlbum("cApItAl", new ArrayList<String>());
		assertEquals(mClassToTest.selectAllAlbums().size(),oldSize + 1);
	}
	
	public void testGetAlbumNameOfPicture1() {
		ArrayList<HashMap<String, String>> pictures = mClassToTest.selectAllPictures();
		String id = pictures.get(0).get("id");
		int idInt = new Integer(id);
		Picture picture = mClassToTest.selectPictureByID(idInt);
		assertEquals(picture.getAlbumName(), "Album 3");
	}
	
public void testGetAlbumNameOfPicture2() {
		
		
		Collection<String> tags = new ArrayList<String>();
		tags.add("Mole"); tags.add("Wart");
		
		String testAlbum = "Album 1";
		
		Picture p = new Picture("testname", "testpath", testAlbum, new Date(), tags);
		
		int actualID = (int) mClassToTest.insertPicture(p);
		
		String albumName = mClassToTest.getAlbumNameOfPicture(actualID);
		
		assertTrue(albumName.equals(testAlbum));
		
	}
		
	public void testSelectPicturesFromAlbum() {
		ArrayList<HashMap<String, String>> albumPictures;
		albumPictures = mClassToTest.selectPicturesFromAlbum("Album 3");
		assertEquals(albumPictures.size(), 1);
	}
	
	public void testSelectPicturesByTag() {
		ArrayList<HashMap<String, String>> tagPictures;
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("blood");
		tagPictures = mClassToTest.selectPicturesByTag(tags);
		assertEquals(tagPictures.size(), 1);
	}
	
	public void testDateToString() {
		Date date = new Date();
		date.setYear(112);
		date.setMonth(10);
		date.setDate(20);
		date.setHours(11);
		date.setMinutes(55);
		date.setSeconds(54);
		String dateString = mClassToTest.dateToString(date);
		assertTrue(dateString.equals("2012-10-20 11:55:54"));
	}
	
	public void testStringToDate() {
		String dateString = "2012-10-20 11:55:54";
		Date date = mClassToTest.stringToDate(dateString);
		assertEquals(date.getYear(), 112);
		assertEquals(date.getMonth(), 10);
		assertEquals(date.getDate(), 20);
		assertEquals(date.getHours(), 11);
		assertEquals(date.getMinutes(), 55);
		assertEquals(date.getSeconds(), 54);
	}
	
	public void testStringJoin() {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Butts");
		strings.add("lmao");
		strings.add("Seriously though");
		String joinedString = mClassToTest.stringJoin(strings, " ");
		assertTrue(joinedString.equals("Butts lmao Seriously though"));
	}
	

	public void testSelectAllTags() {
		
		//assume 8 tags by default
		
		assertEquals(mClassToTest.selectAllTags().size(), 8);
		
	}
	
	public void testGetAlbumNameOfPicture() {
		
		
		Collection<String> tags = new ArrayList<String>();
		tags.add("Mole"); tags.add("Wart");
		
		String testAlbum = "Album 1";
		
		Picture p = new Picture("testname", "testpath", testAlbum, new Date(), tags);
		
		int actualID = (int) mClassToTest.insertPicture(p);
		
		String albumName = mClassToTest.getAlbumNameOfPicture(actualID);
		
		assertTrue(albumName.equals(testAlbum));
		
	}
	
	public void testDeletePictureByID() {
		
		int photoCount = mClassToTest.getPictureCount();
		
		Collection<String> tags = new ArrayList<String>();
		tags.add("Mole"); tags.add("Wart");
		
		Picture p = new Picture("testname", "testpath", "Album 1", new Date(), tags);
		
		int actualID = (int) mClassToTest.insertPicture(p);
		
		//test insertion occurred
		assertEquals(photoCount + 1, mClassToTest.getPictureCount());
		
		mClassToTest.deletePictureByID(actualID);
		
		assertEquals(photoCount, mClassToTest.getPictureCount());
		
	}
	
	public void testSelectPictureByID() {
		
		Collection<String> tags = new ArrayList<String>();
		tags.add("Mole"); tags.add("Wart");
		
		Picture p = new Picture("testname", "testpath", "Album 1", new Date(), tags);
		
		int actualID = (int) mClassToTest.insertPicture(p);
		
		Picture rPic = mClassToTest.selectPictureByID(actualID);
		
		assertEquals("testname", rPic.getName());
		
	}
	
	public void testSelectAllPictures() {
		
		assertEquals(mClassToTest.getPictureCount(), 0);
		
		for(int i = 0; i < 4; i++) {
		
			Collection<String> tags = new ArrayList<String>();
			tags.add("Mole"); tags.add("Wart");
			
			Picture p = new Picture("testname", "testpath", "Album 1", new Date(), tags);
			
			mClassToTest.insertPicture(p);
			
		}
		
		assertEquals(mClassToTest.getPictureCount(), 4);
		
	}
	
	public void testSelectPictureTags() {
		
		Collection<String> tags = new ArrayList<String>();
		tags.add("Mole"); tags.add("Wart");
		
		Picture p = new Picture("testname", "testpath", "Album 1", new Date(), tags);
		
		int id = (int) mClassToTest.insertPicture(p);
		
		Collection<String> returnedTags = mClassToTest.selectPictureTags(id);
		
		assertEquals(returnedTags.size(), 2);
		
	}
	
	
	public void populateDB1(){
		
		ArrayList<String> tag1 = new ArrayList<String>();
		tag1.add("red");tag1.add("blue");tag1.add("green");tag1.add("orange");
		ArrayList<String> tag2 = new ArrayList<String>();
		tag2.add("red");tag2.add("blue");tag2.add("green");tag2.add("orange");
		ArrayList<String> tag3 = new ArrayList<String>();
		tag3.add("blood");tag3.add("puss");tag3.add("bruise");tag3.add("ear");
		
		mClassToTest.insertAlbum("Album 1", tag1);
		mClassToTest.insertAlbum("Album 2", tag2);
		mClassToTest.insertAlbum("Album 3", tag3);
		mClassToTest.insertAlbum("Album 4", new ArrayList<String>());
		
		mClassToTest.insertPicture(new Picture("", "", "Album 3" , new Date(), tag3));

	}

}
