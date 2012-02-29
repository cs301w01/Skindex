package com.cs301w01.meatload;

import android.app.Activity;
import android.os.Bundle;
import java.util.Collection;

public class MoleFinder extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	/**
	 * @uml.property  name="album"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="moleFinder:com.cs301w01.meatload.Album"
	 */
	private Collection<Album> album;

	/**
	 * Getter of the property <tt>album</tt>
	 * @return  Returns the album.
	 * @uml.property  name="album"
	 */
	public Collection<Album> getAlbum() {
		return album;
	}

	/**
	 * Setter of the property <tt>album</tt>
	 * @param album  The album to set.
	 * @uml.property  name="album"
	 */
	public void setAlbum(Collection<Album> album) {
		this.album = album;
	}

	/**
	 * @uml.property  name="pictureDB"
	 * @uml.associationEnd  multiplicity="(1 1)" aggregation="composite" inverse="moleFinder:com.cs301w01.meatload.PictureDB"
	 */
	private PictureDB pictureDB = new com.cs301w01.meatload.PictureDB();

	/**
	 * Getter of the property <tt>pictureDB</tt>
	 * @return  Returns the pictureDB.
	 * @uml.property  name="pictureDB"
	 */
	public PictureDB getPictureDB() {
		return pictureDB;
	}

	/**
	 * Setter of the property <tt>pictureDB</tt>
	 * @param pictureDB  The pictureDB to set.
	 * @uml.property  name="pictureDB"
	 */
	public void setPictureDB(PictureDB pictureDB) {
		this.pictureDB = pictureDB;
	}
}
