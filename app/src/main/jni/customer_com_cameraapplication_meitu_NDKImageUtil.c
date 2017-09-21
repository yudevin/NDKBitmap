//
// Created by yuxumou on 17/9/13.
//

#include<jni.h>
#include <math.h>
#include <android/log.h>



JNIEXPORT jintArray JNICALL Java_customer_com_cameraapplication_meitu_NDKImageUtil_getNDKBitmap(JNIEnv* env, jclass jclazz, jintArray buffer, jint width, jint height)
{
    jint* source = (*env)->GetIntArrayElements(env,buffer,0);
    float brightness = 0.2f; //亮度
    float constraint = 0.2f; //对度度
    int w = width;
    int h = height;
    int a, r, g, b;
    int bab = (int) brightness * 255;
    float cos = 1.0f + constraint;
    int newSize = w * h;
    int r1, g1, b1;
    int  x;
    int  y;
    for (x = 0; x < w; x++) {
        for (y = 0; y < h; y++) {
            int color = source[y * width + x];
            a = color >> 24;
            b = color & 0xFF;
            g = (color >> 8) & 0xFF;
            r = (color >> 16) & 0xFF;

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


            source[y * width + x] = (a << 24) | (r << 16) | (g << 8) | b;


        }
    }
    //第一个释放 第二个保存
    jintArray result = (*env)->NewIntArray(env,newSize);
    (*env)->SetIntArrayRegion(env,result,0,newSize,source);
    (*env)->ReleaseIntArrayElements(env,buffer,source,0);
    return result;
}


JNIEXPORT jintArray JNICALL Java_customer_com_cameraapplication_meitu_NDKImageUtil_toHahajing
        (JNIEnv* env,jobject thiz, jintArray buf, jint width, jint height,jint centerX, jint centerY, jint radius, jfloat multiple)
{
    jint * cbuf = (*env)->GetIntArrayElements(env, buf, 0);
    int newSize = width * height;
    float xishu = multiple;
    int real_radius = (int)(radius / xishu);
    int i = 0, j = 0;
    for (i = 0; i < width; i++)
    {
        for (j = 0; j < height; j++)
        {
            int curr_color = cbuf[j * width + i];


            int pixA = curr_color >> 24;
            int pixB = curr_color & 0xFF;
            int pixG = (curr_color >> 8) & 0xFF;
            int pixR = (curr_color >> 16) & 0xFF;

            int newR = pixR;
            int newG = pixG;
            int newB = pixB;
            int newA = pixA;
            int distance = (int) ((centerX - i) * (centerX - i) + (centerY - j) * (centerY - j));
            if (distance < radius * radius)
            {
                // 图像放大效果
                int src_x = (int)((float)(i - centerX) / xishu);
                int src_y = (int)((float)(j - centerY) / xishu);

                src_x = (int)(src_x * (sqrt(distance) / real_radius));
                src_y = (int)(src_y * (sqrt(distance) / real_radius));
                src_x = src_x + centerX;
                src_y = src_y + centerY;


                int src_color = cbuf[src_y * width + src_x];


                newB = src_color & 0xFF;
                newG = (src_color >> 8) & 0xFF;
                newR = (src_color >> 16) & 0xFF;
                newA =  src_color >> 24;

            }

            //边境检测
            newR = newR > 255 ? 255 : (newR < 0 ? 0 : newR);
            newG = newG > 255 ? 255 : (newG < 0 ? 0 : newG);
            newB = newB > 255 ? 255 : (newB < 0 ? 0 : newB);



            __android_log_print(ANDROID_LOG_INFO, "tag", "A %d  R %d G %d B %d",newA,newR,newG,newB);

            int modif_color = (newA << 24) | (newR << 16) | (newG << 8) | newB;
            cbuf[j * width + i] = modif_color;
        }
    }
    jintArray result = (*env)->NewIntArray(env, newSize);
    (*env)->SetIntArrayRegion(env, result, 0, newSize, cbuf);
    (*env)->ReleaseIntArrayElements(env, buf, cbuf, 0);
    return result;
}
