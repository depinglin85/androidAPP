package lin.android.MyApi;

import lin.android.UI.UIApater;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MyApi extends Activity {
    /** Called when the activity is first created. */
	TextApiView mTextApiView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        mTextApiView = new TextApiView(this);
        setContentView(mTextApiView);
        new UIApater(this);
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mTextApiView.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mTextApiView.onPause();
	}
    
}