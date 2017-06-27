package medicine.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/27.
 */

public class ImageLoader {
    LruCache<String,Bitmap> bitmapLruCache; //图片缓存
    //线程池
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader(){
        initImageCaChe();
    }

    private void initImageCaChe() {
        //计算可使用最大内存
        final int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        //取四分之一作为缓存
        final int cacheSize=maxMemory/4;
        bitmapLruCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    public void displayImage(final String url, final ImageView imageView){
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {


            }
        });
    }

    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            try {
                final HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                bitmap= BitmapFactory.decodeStream(conn.getInputStream());
                conn.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
