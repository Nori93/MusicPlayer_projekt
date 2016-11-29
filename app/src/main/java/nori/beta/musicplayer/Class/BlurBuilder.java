package nori.beta.musicplayer.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**<####_Norbert_####>
 * BlurBuilder
 * Class for blur image for example background
 *
 *  @param context give context of Activity
 *
 *  @param image give a image to blur
 */
public class BlurBuilder {
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 0.5f;

    public static Bitmap blur(Context context,Bitmap image){

        // width & height of image multiply of BITMAP_SCALE
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        // create a empty bitmap with size of width & height
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image,width,height,false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // Here make the blur image the bluere in ScriptIntrinsicBlur
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs,inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs,outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        //Return that output Image that was blur
        return  outputBitmap;
    }
}
