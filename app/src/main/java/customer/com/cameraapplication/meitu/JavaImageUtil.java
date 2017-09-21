package customer.com.cameraapplication.meitu;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by yuxumou on 17/9/12.
 */

public class JavaImageUtil {

    private static final float brightness = 0.2f; //亮度
    private static final float constraint = 0.2f; //对度度

    public static Bitmap getJavaImage(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int a, r, g, b;
        int bab = (int) brightness * 255;
        float cos = 1.0f + constraint;
        Bitmap result = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        int r1, g1, b1;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int color = bitmap.getPixel(x, y);
                a = Color.alpha(color);
                b = Color.blue(color);
                g = Color.green(color);
                r = Color.red(color);

                //亮度
                b1 = b + bab;
                g1 = g + bab;
                r1 = r + bab;

                //边境检测
                b = b1 > 255 ? 255 : (b1 < 0 ? 0 : b1);
                g = g1 > 255 ? 255 : (g1 < 0 ? 0 : g1);
                r = r1 > 255 ? 255 : (r1 < 0 ? 0 : r1);


                //对比度

                r1 = (int) ((r - 128) * cos);
                r1 = r + 128 + r1;



                b1 = (int)((b - 128) * cos);
                b1  =  b + 128 + b1;

                g1 = (int)((g - 128) * cos);
                g1  =  g + 128 + g1;


                //边境检测
                b = b1 > 255 ? 255 : (b1 < 0 ? 0 : b1);
                g = g1 > 255 ? 255 : (g1 < 0 ? 0 : g1);
                r = r1 > 255 ? 255 : (r1 < 0 ? 0 : r1);


                result.setPixel(x,y,Color.argb(a,r,g,b));


            }
        }


        return result;
    }

    //哈哈镜
    public static Bitmap MagicMirror(Bitmap bitmap, int radius){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);

        int[] oldPx = new int[w * h];
        int[] newPx = new int[w * h];
        int r, g, b, a, color;
        float xishu = 2;
        int real_radius = (int)(radius / xishu);

        bitmap.getPixels(oldPx, 0, w, 1, 1, w - 1, h - 1);

        for (int i = 0; i < w; i++){
            for (int j = 0; j < h; j++){
                color = oldPx[j * w + i];
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
                a = Color.alpha(color);

                int newR = r;
                int newG = g;
                int newB = b;
                int newA = a;

                int centerX = w / 2;
                int centerY = h / 2;

                int distance = (int) ((centerX - i) * (centerX - i) + (centerY - j) * (centerY - j));
                //对半径范围内的图像进行处理，其余部分的图像不做处理
                if (distance < radius * radius){
                    // 放大镜的凹凸效果
                    int src_x = (int) ((float) (i - centerX) / xishu);
                    int src_y = (int) ((float) (j - centerY) / xishu);
                    src_x = (int)(src_x * (Math.sqrt(distance) / real_radius));
                    src_y = (int)(src_y * (Math.sqrt(distance) / real_radius));
                    src_x = src_x + centerX;
                    src_y = src_y + centerY;

                    color = oldPx[src_y * w + src_x];
                    newR = Color.red(color);
                    newG = Color.green(color);
                    newB = Color.blue(color);
                    newA = Color.alpha(color);
                }
                //检查像素值是否超出0~255的范围
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                newA = Math.min(255, Math.max(0, newA));

                newPx[j * w + i] = Color.argb(newA, newR, newG, newB);
            }
        }
        result.setPixels(newPx, 0, w, 0, 0, w, h);
        return result;
    }

}
