/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class customer_com_cameraapplication_meitu_NDKImageUtil */

#ifndef _Included_customer_com_cameraapplication_meitu_NDKImageUtil
#define _Included_customer_com_cameraapplication_meitu_NDKImageUtil
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     customer_com_cameraapplication_meitu_NDKImageUtil
 * Method:    getNDKBitmap
 * Signature: ([III)[I
 */
JNIEXPORT jintArray JNICALL Java_customer_com_cameraapplication_meitu_NDKImageUtil_getNDKBitmap
  (JNIEnv *, jclass, jintArray, jint, jint);

JNIEXPORT jintArray JNICALL Java_customer_com_cameraapplication_meitu_NDKImageUtil_toHahajing
        (JNIEnv* env,jobject thiz, jintArray buf, jint width, jint height,jint centerX, jint centerY, jint radius, jfloat multiple);

#ifdef __cplusplus
}
#endif
#endif
