package com.Circuit.test;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CircuitSolverActivity extends Activity {
    /** Called when the activity is first created. */  	
	
	private Context context;
	//RelativeLayout mLayout;
	//ArrayList<ResistorView> mElements = new ArrayList<ResistorView>();
	TextView tVNumOfResistors;
	public ResistorView mResistor, mResistorTemp;
	
	Animation ccw, cw;

	
	@Override    
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this; 
        
        mResistorTemp = new ResistorView(this);
        ccw = AnimationUtils.loadAnimation(this, R.anim.resistor_rotate_ccw);
		cw = AnimationUtils.loadAnimation(this, R.anim.resistor_rotate_cw);
        Global.mLayout = (RelativeLayout)findViewById(R.id.elementContainer);
        tVNumOfResistors = (TextView) findViewById(R.id.bNumberOfResistors);        

    }
	
	public void bAddResistor(View view){
		
		mResistor = new ResistorView(context);
		if (Global.mLayout == null){
			
			//Log.d("If statement", "mLayout is null!");
		}else {
			
			//Log.d("If statement", "mLayout is not null!");
			mResistor.setBackgroundColor(Color.TRANSPARENT);
			//mResistor.setBackgroundColor(Color.WHITE);
			Global.mElements.add(mResistor);
			mResistor.setId(Global.mElements.size());			
			Global.mLayout.addView(mResistor);				
			//mLayout.addView(mResistor, mElements.size() * -1);
		}
		
		tVNumOfResistors.setText(Integer.toString(Global.mElements.size()));		
		
	}
	
	public void bRemoveResistor(View view){
		
		View tempView;
		int numberOfElements = Global.mElements.size();
		int tempNumOfElementsDeleated = 0;
		/*if (mLayout == null){
			
			Log.d("If statement", "mLayout is null!");
		}else {
			
			Log.d("If statement", "mLayout is not null!");
			mElements.remove(mElements.size() - 1);
			mLayout.removeViewAt(mElements.size());
			
		}*/
		
		for (int i = 0; i < numberOfElements; i++){			
			
			tempView = Global.mLayout.getChildAt(i - tempNumOfElementsDeleated);
			if (tempView.isSelected()){
				
				//Log.d("bDeleate", "View @ " + i + " is selected: " + tempView.isSelected());
				Global.mElements.remove(Global.mElements.size() - 1);
				//mLayout.removeViewAt(i);
				tempNumOfElementsDeleated++;
				Global.mLayout.removeView(tempView);
			} else {
				
				//Log.d("bRotate", "View @ " + i + " is selected: " + tempView.isSelected());
			}
		}
		
		tVNumOfResistors.setText(Integer.toString(Global.mElements.size()));
	}
    
	public void bRotate(View view){		
		
		View tempView;
		//mResistor.setOrientation(ccw, cw);
		//Log.d("bRotate", "Inside, mElements size is " + Global.mElements.size());
		for (int i = 0; i < Global.mElements.size(); i++){			
			
			tempView = Global.mLayout.getChildAt(i);
			if (tempView.isSelected()){
				
				//Log.d("bRotate", "View @ " + i + " is selected: " + tempView.isSelected());
				mResistor = (ResistorView) tempView;
				mResistor.setOrientation();
			} else {
				
				//Log.d("bRotate", "View @ " + i + " is selected: " + tempView.isSelected());
			}
		}
		
	}
	
	public int checkSnap(View v){
		
		//The view that was dropped.
		mResistor = (ResistorView) v;
		//Log.d("checkSnap", "Inside, mElements size is " + Global.mElements.size());
		
		for (int i = 0; i < Global.mElements.size(); i++){
			
			//Log.d("checkSnap", "For loop executed " + i);
			if (mResistor != Global.mLayout.getChildAt(i)){				
			
				mResistorTemp = (ResistorView) Global.mLayout.getChildAt(i);
				
				//Check if Node 1 of the dropped view is close to the Node 1 of the temporary view.
				if ((Math.abs(mResistorTemp.node1.x - mResistor.node1.x) <= (40 * Global.mScaleFactor)) && (Math.abs(mResistorTemp.node1.y - mResistor.node1.y) <= (40 * Global.mScaleFactor))){
					
					//mResistor.node1.x = mResistorTemp.node1.x;
					//mResistor.node1.y = mResistorTemp.node1.y;
					RelativeLayout.LayoutParams mResistorDroppedParams = (RelativeLayout.LayoutParams) mResistor.getLayoutParams();
					RelativeLayout.LayoutParams mResistorTempParams = (RelativeLayout.LayoutParams) mResistorTemp.getLayoutParams();

					if ((mResistor.resistorOrientation == 0 && mResistorTemp.resistorOrientation == 0) || (mResistor.resistorOrientation == 1 && mResistorTemp.resistorOrientation == 1)){
						
						mResistorDroppedParams.leftMargin = mResistorTempParams.leftMargin;
						mResistorDroppedParams.topMargin = mResistorTempParams.topMargin;
					} 
					
					if (mResistor.resistorOrientation == 0 && mResistorTemp.resistorOrientation == 1){
						
						mResistorDroppedParams.leftMargin = mResistorTempParams.leftMargin - (int)(10 * Global.mScaleFactor);
						mResistorDroppedParams.topMargin = mResistorTempParams.topMargin + (int)(10 * Global.mScaleFactor);
					}
					
					if (mResistor.resistorOrientation == 1 && mResistorTemp.resistorOrientation == 0){
						
						mResistorDroppedParams.leftMargin = mResistorTempParams.leftMargin + (int)(10 * Global.mScaleFactor);
						mResistorDroppedParams.topMargin = mResistorTempParams.topMargin - (int)(10 * Global.mScaleFactor);
					}
					
					mResistor.setLayoutParams(mResistorDroppedParams);
					
					return 1;
				}
			
				//Check if Node 1 of the dropped view is close to the Node 2 of the temporary view.
				if ((Math.abs(mResistorTemp.node2.x - mResistor.node1.x) <= 30) && (Math.abs(mResistorTemp.node2.y - mResistor.node1.y) <= 30)){
				
					RelativeLayout.LayoutParams mResistorDroppedParams = (RelativeLayout.LayoutParams) mResistor.getLayoutParams();
					RelativeLayout.LayoutParams mResistorTempParams = (RelativeLayout.LayoutParams) mResistorTemp.getLayoutParams();

					if ((mResistor.resistorOrientation == 0 && mResistorTemp.resistorOrientation == 0) || (mResistor.resistorOrientation == 1 && mResistorTemp.resistorOrientation == 1)){
						
						mResistorDroppedParams.leftMargin = mResistorTempParams.leftMargin;
						mResistorDroppedParams.topMargin = mResistorTempParams.topMargin;
					} 
					
					if (mResistor.resistorOrientation == 0 && mResistorTemp.resistorOrientation == 1){
						
						mResistorDroppedParams.leftMargin = (mResistorTempParams.leftMargin + 200) - (int)(10 * Global.mScaleFactor);
						mResistorDroppedParams.topMargin = mResistorTempParams.topMargin + (int)(10 * Global.mScaleFactor);
					}
					
					if (mResistor.resistorOrientation == 1 && mResistorTemp.resistorOrientation == 0){
						
						mResistorDroppedParams.leftMargin = mResistorTempParams.leftMargin + (int)(10 * Global.mScaleFactor);
						mResistorDroppedParams.topMargin = (mResistorTempParams.topMargin + 200) - (int)(10 * Global.mScaleFactor);
					}
					
					mResistor.setLayoutParams(mResistorDroppedParams);
					
					return 2;
				}
			
				//Check if Node 2 of the dropped view is close to the Node 1 of the temporary view.
				if ((Math.abs(mResistorTemp.node1.x - mResistor.node2.x) <= 30) && (Math.abs(mResistorTemp.node1.y - mResistor.node2.y) <= 30)){
				
					mResistor.node2.x = mResistorTemp.node1.x;
					mResistor.node2.y = mResistorTemp.node1.y;
					
					return 3;
				}
			
				//Check if Node 2 of the dropped view is close to the Node 2 of the temporary view.
				if ((Math.abs(mResistorTemp.node2.x - mResistor.node2.x) <= 30) && (Math.abs(mResistorTemp.node2.y - mResistor.node2.y) <= 30)){
				
					mResistor.node2.x = mResistorTemp.node2.x;
					mResistor.node2.y = mResistorTemp.node2.y;
					
					return 4;
				}			
			
			}		
			
		}
		
		return 0;
	}
	
	//Not used for anything right now.
	public void updateWhenZooming(){
		
		View tempView;
		
		for (int i = 0; i < Global.mElements.size(); i++){
			
			
		}
			
	}
	
}
