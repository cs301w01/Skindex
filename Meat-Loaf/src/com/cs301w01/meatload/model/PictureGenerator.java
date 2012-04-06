package com.cs301w01.meatload.model;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Used for generating a random picture.
 * 
 * @author Joel Burford
 */
public class PictureGenerator {

	public PictureGenerator() {

	}

	/**
	 * Generates a random bitmap
	 * 
	 * @return Bitmap of generated picture
	 * @see <a
	 *      href="http://developer.android.com/reference/android/graphics/Bitmap.html#createBitmap%28int[],%20int,%20int,%20int,%20int,%20android.graphics.Bitmap.Config%29">
	 *      http://developer.android.com/reference/android/graphics/Bitmap.html#createBitmap%28int[],%20int,%20int,%20int,%20int,%20android.graphics.Bitmap.Config%29</a>
	 * @see <a
	 *      href="http://developer.android.com/reference/android/graphics/Color.html">
	 *      http://developer.android.com/reference/android/graphics/Color.html</a>
	 * @see <a
	 *      href="http://developer.android.com/reference/android/widget/ImageView.html">
	 *      http://developer.android.com/reference/android/widget/ImageView.html</a>
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Random.html">
	 *      http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Random.html</a>
	 * @see <a
	 *      href="http://developer.android.com/reference/android/graphics/BitmapFactory.html">
	 *      http://developer.android.com/reference/android/graphics/BitmapFactory.html</a>
	 */
	public Bitmap generatePicture() {
		int height = 200;
		int width = 200;
		int[] colors = new int[height * width];
		int r, g, b;
		int test = 0;

		Random gen = new Random(System.currentTimeMillis());
		r = gen.nextInt(256);
		g = gen.nextInt(256);
		b = gen.nextInt(256);

		for (int i = 0; i < height; i++) {
			if (test > 15) {
				r = gen.nextInt(256);
				g = gen.nextInt(256);
				b = gen.nextInt(256);
				test = 0;
			} else {
				test++;
			}

			for (int j = 0; j < width; j++) {
				colors[i * height + j] = Color.rgb(r, g, b);
			}
		}

		Bitmap imgOnDisplay = Bitmap.createBitmap(colors, width, height, Bitmap.Config.RGB_565);

		return imgOnDisplay;
	}
}
