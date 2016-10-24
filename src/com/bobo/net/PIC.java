package com.bobo.net;

import android.graphics.Bitmap;

public class PIC {
	public static Bitmap bitmap;
	public static Boolean isreach = true;
	

	public static Boolean getIsreach() {
		return isreach;
	}

	public static void setIsreach(Boolean isreach) {
		PIC.isreach = isreach;
	}

	public static Bitmap getBitmap() {
		return bitmap;
	}

	public static void setBitmap(Bitmap bitmap) {
		PIC.bitmap = bitmap;
	}

}
