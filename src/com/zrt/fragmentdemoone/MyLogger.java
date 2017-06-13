package com.zrt.fragmentdemoone;

import android.util.Log;

public class MyLogger {
	public static boolean flag = true;
	public static String TAG;
	static{
		TAG = ">>>>";
	}
	public static void i(String content){
		if (flag){
			Log.i(TAG, content);
		}
	}
	
	public static void e(String content){
		if (flag){
			Log.e(TAG, content);
		}
	}

}
