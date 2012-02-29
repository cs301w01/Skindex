package com.cs301w01.meatload;

import java.util.Collection;


public class Album {

	/** 
	 * @uml.property name="picture"
	 * @uml.associationEnd inverse="album:com.cs301w01.meatload.Picture"
	 */
	private Picture picture;

	/** 
	 * Getter of the property <tt>picture</tt>
	 * @return  Returns the picture.
	 * @uml.property  name="picture"
	 */
	public Picture getPicture() {
		return picture;
	}

	/** 
	 * Setter of the property <tt>picture</tt>
	 * @param picture  The picture to set.
	 * @uml.property  name="picture"
	 */
	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	/**
	 * @uml.property  name="moleFinder"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="album:com.cs301w01.meatload.MoleFinder"
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
	 * @uml.property  name="tag"
	 * @uml.associationEnd  multiplicity="(1 -1)" inverse="album:com.cs301w01.meatload.Tag"
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

	/**
	 * @uml.property  name="pictureDB"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="album:com.cs301w01.meatload.PictureDB"
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
