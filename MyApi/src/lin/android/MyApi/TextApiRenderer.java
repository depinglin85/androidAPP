package lin.android.MyApi;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import lin.android.UI.GraphicUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;

public class TextApiRenderer implements Renderer{

	Context mContext;
	int mTextureId;
	private float w;
	private float h;
	private float w2;
	private float h2;
	private int mTextureIdbg;
	private float cx;
	private float cy;
	private float ctx;
	private float cty;
	private int mTextTextureId;
	private float tw;
	private float th;
	public TextApiRenderer(Context context){
		mContext = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		Bitmap bitmap = GraphicUtil.loadBitmapFromResource(mContext.getResources(),R.drawable.bg1, options);
		
		w = (float)bitmap.getWidth()/480*2;
		h = (float)bitmap.getHeight()/854*2;
		cx = GraphicUtil.getXToGLX(bitmap, 100);
		cy = GraphicUtil.getYToGLY(bitmap, 0);
		
		mTextureId = GraphicUtil.loadBitmapToTexture(gl, bitmap);
		
		bitmap = GraphicUtil.loadBitmapFromResource(mContext.getResources(),R.drawable.bg2, options);
		w2 = (float)bitmap.getWidth()/480*2;
		h2 = (float)bitmap.getHeight()/854*2;
		mTextureIdbg = GraphicUtil.loadBitmapToTexture(gl, bitmap);
		
		String str ="モバイル・コンテンツ・フォーラム（MCF）は、\n2010年1月～12月のモバイルコンテンツ市場およびモバイルコンテンツ市場の調査結果を公表した。\n今回よりスマートフォンなどのオープンプラットフォーム\n市場についても調査結果がまとめられている。";
		bitmap = GraphicUtil.makeTextToTexture(str, 20, 450);
		ctx = GraphicUtil.getXToGLX(bitmap, 18);
		cty = GraphicUtil.getYToGLY(bitmap, 0);
		tw = (float)bitmap.getWidth()/480*2;
		th = (float)bitmap.getHeight()/854*2;
		mTextTextureId = GraphicUtil.loadBitmapToTexture(gl, bitmap);
		
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 2.0f, 3.0f, mTextureIdbg, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		GraphicUtil.drawTexture(gl, 0, 0, 2.0f ,3.0f, mTextureId, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		GraphicUtil.drawTexture(gl, cx, 0, w ,h, mTextureId, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
		GraphicUtil.drawTexture(gl, ctx, cty, tw, th, mTextTextureId, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f);
		
	}

}
