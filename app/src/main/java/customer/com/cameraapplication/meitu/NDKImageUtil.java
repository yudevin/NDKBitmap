package customer.com.cameraapplication.meitu;

/**
 * Created by yuxumou on 17/9/13.
 */

public class NDKImageUtil {
    static {
        System.loadLibrary("native-bitmap-lib");
    }
    public  static native int[] getNDKBitmap(int[] buffer,int w,int h);

    public  static native int[] toHahajing(int[] buffer,int w,int h,int centerX, int centerY, int radius, float multiple);
}
