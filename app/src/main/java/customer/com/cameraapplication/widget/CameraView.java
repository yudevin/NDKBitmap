package customer.com.cameraapplication.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;

import android.util.AttributeSet;
import android.view.TextureView;



import customer.com.cameraapplication.camera.CameraDevice;

/**
 * Created by yuxumou on 17/9/11.
 */

public class CameraView extends TextureView implements TextureView.SurfaceTextureListener{
    public CameraView(Context context) {
        this(context,null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSurfaceTextureListener(this);
    }

    public  void openPreview(){

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        try {
            CameraDevice.getInstance().openBackCamera().setPreviewTexture(surfaceTexture);
            CameraDevice.getInstance().openBackCamera().startPreview();
            CameraDevice.getInstance().openBackCamera().setDisplayOrientation(90);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        CameraDevice.getInstance().release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
