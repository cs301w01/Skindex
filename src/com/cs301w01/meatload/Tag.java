package com.cs301w01.meatload;

import java.util.Collection;


public class Tag {

	/**
	 * @uml.property  name="album"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="tag:com.cs301w01.meatload.Album"
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
	 * @uml.property name="picture"
	 * @uml.associationEnd multiplicity="(1 -1)" aggregation="shared" inverse="tag:com.cs301w01.meatload.Picture"
	 */
	private Collection<Picture> picture;

	/** 
	 * Getter of the property <tt>picture</tt>
	 * @return  Returns the picture.
	 * @uml.property  name="picture"
	 */
	public Collection<Picture> getPicture() {
		return picture;
	}

	/** 
	 * Setter of the property <tt>picture</tt>
	 * @param picture  The picture to set.
	 * @uml.property  name="picture"
	 */
	public void setPicture(Collection<Picture> picture) {
		this.picture = picture;
	}

}
