package lin.android.MyApi;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class TextApiView extends GLSurfaceView{

	TextApiRenderer mTextApiRenderer;
	
	public TextApiView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mTextApiRenderer = new TextApiRenderer(context);
		setRenderer(mTextApiRenderer);
	}

}
