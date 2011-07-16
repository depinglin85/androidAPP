package lin.android.UI;

import android.app.Activity;
import android.view.Display;

public class UIApater {
	
	public static int CANCASWIDTH = 480;
	public static int CANVASHEIGHT = 854;
	public int localWidth;
	public int localHeight;
	private float rateX = 0.0f;
	private float rateY = 0.0f;
	
	private static UIApater _ins;
	
	public UIApater(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		localWidth = display.getWidth();
		localHeight = display.getHeight();
		rateX = localWidth/CANCASWIDTH;
		rateY = localHeight/CANVASHEIGHT;
		_ins = this;
	}
	
	public static UIApater getInstance(){
		return _ins;
	}
	
	public void setLocalWidth(int width){
		localWidth = width;
	}
	public int getLocalWidth(){
		return localWidth;
	}
	public void setLocalHeight(int height){
		localHeight = height;
	}
	public int getLocalHeight(){
		return localHeight;
	}
	
	public int getFixLocalWidth(int width){
		return (int) rateX*width;
	}
	public int getFixLocalHeight(int height){
		return (int) rateY*height;
	}

	
}
