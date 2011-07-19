package lin.android.UI;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLUtils;
import android.util.Log;

public class GraphicUtil {

	/**
	 * 
	 * @param gl
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 * @param u
	 * @param v
	 * @param tex_w
	 * @param tex_h
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public static final void drawTexture(GL10 gl, float x, float y, float width, float height, 
										int texture, float u, float v, float tex_w, float tex_h, 
										float r, float g, float b, float a){
		
		float[] vertices = {
				-0.5f * width + x, -0.5f * height + y,
				 0.5f * width + x, -0.5f * height + y,
				-0.5f * width + x,  0.5f * height + y,
				 0.5f * width + x,  0.5f * height + y,
		};
		float[] colors = {
				r, g, b, a,
				r, g, b, a,
				r, g, b, a,
				r, g, b, a,
			};
		float[] coords = {
			        u, 	v + tex_h,
			u + tex_w, 	v + tex_h,
			        u,  v,
			u + tex_w,  v,
		};
		
		FloatBuffer polygonVertices = makeFloatBuffer(vertices);
		FloatBuffer polygonColors = makeFloatBuffer(colors);
		FloatBuffer texCoords = makeFloatBuffer(coords);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	 
//		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_BLEND);
//		gl.glBlendFunc(GL11.GL_ONE,GL11.GL_ONE_MINUS_SRC_ALPHA);
//		gl.glEnable(GL10.GL_BLEND);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	 
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	
	
	
	/**
	 * from text to bitmap for opengl texture
	 * @param text
	 * @param textSize
	 * @param lengthLimit
	 * @return
	 */
	public static Bitmap makeTextToTexture(String text, int textSize, int lengthLimit, Bitmap.Config config){
		
		int[] bitmapSize;
		Bitmap bitmap;
		String str = fixLength(text, textSize, lengthLimit);
		bitmapSize = fixBitmapSize(str);
		int size = textSize+10;
		bitmap = Bitmap.createBitmap(bitmapSize[1]*size, bitmapSize[0]*size, config);
		Canvas canvas = new Canvas(bitmap);
		Paint textPaint = new Paint();
//		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(textSize);
		
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xff, 0xff, 0xff, 0xff);
		
		String[] string = str.split("\n");
		for(int i=0 ;i<string.length; i++){
			canvas.drawText(string[i], 0, size*(i+1), textPaint);
		}
		return bitmap;
	}
	public static Bitmap makeTextToTexture(String text, int textSize, int lengthLimit){
		return makeTextToTexture(text, textSize, lengthLimit, Bitmap.Config.ARGB_8888);
	}
	
	
	public static int loadBitmapToTexture(GL10 gl, Bitmap bitmap){
		int[] textures = new int[1];
		if(bitmap == null){
			return 0;
		}
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		
		bitmap.recycle();
		
		return textures[0];
		
	}
	
	public static String fixLength(String text, int textSize, int lengthLimit){
		StringBuilder stringBuilder = new StringBuilder();
		String[] str = text.split("\n");
		int max = lengthLimit/textSize;
		for(int i=0; i<str.length; i++){
			int j=0;
			while(true){
				if((j+1)*max > str[i].length()){
					stringBuilder.append(str[i].substring(j*max, str[i].length()));
					stringBuilder.append("\n");
					break;
				}else{
					stringBuilder.append(str[i].substring(j*max, (j+1)*max));
					stringBuilder.append("\n");
				}
				j++;
			}
		}
		return stringBuilder.toString();
	}
	/**
	 * 
	 * @param text
	 * @return
	 */
	public static int[] fixBitmapSize(String text){
		int[] size = new int[2];
		String[] str = text.split("\n");
		size[0] = str.length;
		int maxLength = 0;
		for(int i=0; i<str.length; i++){
			if(str[i].length() > maxLength){
				maxLength = str[i].length();
			}
		}
		size[1] = maxLength;
		return size;
	}
	
	public static float getXToGLX(Bitmap bitmap, int x){
		return -1.0f +(float)(bitmap.getWidth()+2*x)/UIApater.getInstance().getLocalWidth();
	}
	public static float getYToGLY(Bitmap bitmap, int y){
		return 1.0f-((float)bitmap.getHeight()/UIApater.getInstance().getLocalHeight()) ;
	}
	
	public static Bitmap loadBitmapFromResource(Resources resources, int recsourcID, BitmapFactory.Options options){
		Bitmap bitmap = BitmapFactory.decodeResource(resources, recsourcID, options);
		return bitmap;
	}
	
	
	public static FloatBuffer makeFloatBuffer(float[] array){
		ByteBuffer bb = ByteBuffer.allocateDirect(array.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(array);
		fb.position(0);
		return fb;
	}
	
}
