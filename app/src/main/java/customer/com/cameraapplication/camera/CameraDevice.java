package customer.com.cameraapplication.camera;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by yuxumou on 17/9/11.
 */

public class CameraDevice {


    public enum Orientation {
        BACK,
        Front
    }

    public static String TAG = CameraDevice.class.getSimpleName();

    private static CameraDevice mCameraDevice = null;

    private Camera mCamera = null;

    private CameraDevice() {

    }

    public synchronized static CameraDevice getInstance() {
        if (mCameraDevice == null) {
            mCameraDevice = new CameraDevice();
        }
        return mCameraDevice;
    }


    public  Camera open() {
        if (mCamera != null){
            return mCamera;
        }
        try {
            mCamera = Camera.open();
            return mCamera;
        } catch (Exception e) {
            Log.i(TAG, "打开相机失败");
            return null;
        }

    }

    public Camera openFrontCamera() {
        if (mCamera != null){
            return mCamera;
        }
        try {
            mCamera = Camera.open(getFrontCameraId());
            return mCamera;
        } catch (Exception e) {
            Log.i(TAG, "打开相机失败");
            return null;
        }
    }

    public  Camera openBackCamera() {
        if (mCamera != null){
            return mCamera;
        }
        try {
            mCamera = Camera.open(getBackCameraId());
            return mCamera;
        } catch (Exception e) {
            Log.i(TAG, "打开相机失败");
            return null;
        }
    }


    public  int getBackCameraId() {
        return getCameraId(Orientation.BACK);
    }

    public  int getFrontCameraId() {
        return getCameraId(Orientation.Front);
    }

    public  int getCameraId(Orientation orientation) {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo

            if (orientation == Orientation.Front) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                    return camIdx;
                }
            } else {

                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                    return camIdx;
                }
            }

        }
        return -1;
    }


    public  void release() {
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                Log.i(TAG, "释放相机失败");
            }

        }
    }


}
