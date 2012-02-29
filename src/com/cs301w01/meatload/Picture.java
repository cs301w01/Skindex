package com.cs301w01.meatload;

import java.util.Collection;


/**
 * @uml.dependency   supplier="com.cs301w01.meatload.Album"
 */
public class Picture {

	/** 
	 * @uml.property name="album"
	 * @uml.associationEnd multiplicity="(1 1)" aggregation="composite" inverse="picture:com.cs301w01.meatload.Album"
	 */
	private Album album = new com.cs301w01.meatload.Album();

	/** 
	 * Getter of the property <tt>album</tt>
	 * @return  Returns the album.
	 * @uml.property  name="album"
	 */
	public Album getAlbum() {
		return album;
	}

	/** 
	 * Setter of the property <tt>album</tt>
	 * @param album  The album to set.
	 * @uml.property  name="album"
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}

	/** 
	 * @uml.property name="tag"
	 * @uml.associationEnd multiplicity="(1 -1)" inverse="picture:com.cs301w01.meatload.Tag"
	 */
	private Collection<Tag> tag;

	/** 
	 * Getter of the property <tt>tag</tt>
	 * @return  Returns the tag.
	 * @uml.property  name="tag"
	 */
	public Collection<Tag> getTag() {
		return tag;
	}

	/** 
	 * Setter of the property <tt>tag</tt>
	 * @param tag  The tag to set.
	 * @uml.property  name="tag"
	 */
	public void setTag(Collection<Tag> tag) {
		this.tag = tag;
	}

}
