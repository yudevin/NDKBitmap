package customer.com.cameraapplication;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import customer.com.cameraapplication.meitu.JavaImageUtil;
import customer.com.cameraapplication.meitu.NDKImageUtil;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.getData().get("bitmap");
            imageView.setImageBitmap(bitmap);
        }
    };



    ImageView imageView;

    @Override
    protected void onResume() {
        super.onResume();
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.d);
        imageView = (ImageView) findViewById(R.id.img);
        imageView.setImageBitmap(bitmap);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(bitmap);
            }
        });


        findViewById(R.id.java_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                imageView.setImageBitmap(JavaImageUtil.getJavaImage(bitmap));
                time = (System.currentTimeMillis() - time);
                Toast.makeText(MainActivity.this, "java 处理bitmap 时间 " + time, Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.ndk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();


                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int[] buffer = new int[w * h];
                bitmap.getPixels(buffer, 0, w, 1, 1, w - 1, h - 1);


                //ndk获取
                int[] ndkImage = NDKImageUtil.getNDKBitmap(buffer, w, h);

                Bitmap rebitmap = Bitmap.createBitmap(ndkImage,w, h, Bitmap.Config.ARGB_8888);

                imageView.setImageBitmap(rebitmap);


                time = (System.currentTimeMillis() - time);
                Toast.makeText(MainActivity.this, "NDK 处理bitmap 时间 " + time, Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.ndk_hahajing_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //ndk获取
//                int[] ndkImage = NDKImageUtil.getNDKBitmap(buffer, w, h);
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        long time = System.currentTimeMillis();
//
//
//                        final int w = bitmap.getWidth();
//                        final int h = bitmap.getHeight();
//
//                        final int[] buffer = new int[w * h];
//                        bitmap.getPixels(buffer, 0, w, 1, 1, w - 1, h - 1);
//                        int[] ndkImage = NDKImageUtil.toHahajing(buffer, w, h,w/2,h/2,h/9,0.3f);
//                        Bitmap rebitmap = Bitmap.createBitmap(ndkImage,w, h, Bitmap.Config.ARGB_8888);
//                        Message msg = Message.obtain();
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("bitmap",rebitmap);
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
//                        time = (System.currentTimeMillis() - time);
//                        Toast.makeText(MainActivity.this, "NDK 处理 hha bitmap 时间 " + time, Toast.LENGTH_LONG).show();
//                    }
//                }).start();



                long time = System.currentTimeMillis();


                final int w = bitmap.getWidth();
                final int h = bitmap.getHeight();

                final int[] buffer = new int[w * h];
                bitmap.getPixels(buffer, 0, w, 1, 1, w - 1, h - 1);
//                int[] ndkImage = NDKImageUtil.toHahajing(buffer, w, h,w/2,h/2,w / 4,2f);
//                Bitmap rebitmap = Bitmap.createBitmap(ndkImage,w, h, Bitmap.Config.RGB_565);

                Bitmap rebitmap = JavaImageUtil.MagicMirror(bitmap,bitmap.getWidth() );
                imageView.setImageBitmap(rebitmap);

                time = (System.currentTimeMillis() - time);

                Toast.makeText(MainActivity.this, "NDK 处理 hha bitmap 时间 " + time, Toast.LENGTH_LONG).show();



            }
        });


    }

}
