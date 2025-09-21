package com.example.tutorialproject.constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ConvertImage {

	public static Bitmap convertImage(Context context, int imageResId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 10;

		return BitmapFactory.decodeResource(context.getResources(), imageResId, options);
	}
}
