package com.Circuit.test;

import java.util.ArrayList;

import android.widget.RelativeLayout;

public class Global {
	
	public static boolean setAlphaWhenMoving = false;
	public static float mScaleFactor = 1.f;	
	public static boolean rotated = false;
	public static ArrayList<ResistorView> mElements = new ArrayList<ResistorView>();
	public static RelativeLayout mLayout;
	public static boolean selectedResistor = false;
	
	static{
		
		setAlphaWhenMoving = false;
		rotated = false;
		mElements.clear();
		//mLayout.findViewById(R.id.elementContainer);
		selectedResistor = false;
		mScaleFactor = 1.f;
	}	
	
	public static void setAlphaWhenMoving(boolean bool){
		
		setAlphaWhenMoving = bool;
	}
	
	public static void setScaleFactor(float scaleFactor){
		
		mScaleFactor = scaleFactor;
	}
	
	public static void setSelectedResistor(boolean bool){
		
		selectedResistor = bool;
	}
	
	public static boolean getSelectedResistor(){
		
		return selectedResistor;
	}
	
}
