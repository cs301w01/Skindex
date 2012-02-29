package com.cs301w01.meatload;

import java.util.Collection;


public class PictureDB {

	/**
	 * @uml.property  name="moleFinder"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="pictureDB:com.cs301w01.meatload.MoleFinder"
	 */
	private MoleFinder moleFinder = new com.cs301w01.meatload.MoleFinder();

	/**
	 * Getter of the property <tt>moleFinder</tt>
	 * @return  Returns the moleFinder.
	 * @uml.property  name="moleFinder"
	 */
	public MoleFinder getMoleFinder() {
		return moleFinder;
	}

	/**
	 * Setter of the property <tt>moleFinder</tt>
	 * @param moleFinder  The moleFinder to set.
	 * @uml.property  name="moleFinder"
	 */
	public void setMoleFinder(MoleFinder moleFinder) {
		this.moleFinder = moleFinder;
	}

	/**
	 * @uml.property  name="album"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="composite" inverse="pictureDB:com.cs301w01.meatload.Album"
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

}
