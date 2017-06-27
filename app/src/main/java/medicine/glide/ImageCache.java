package medicine.glide;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2017/6/27.
 */

public class ImageCache {
    LruCache<String,Bitmap> bitmapLruCache;
    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {
        //计算可用最大内存
        final int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        //取四分之一的可用内存作为缓存
        final int cacheSize=maxMemory/4;
        bitmapLruCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    public void put(String url,Bitmap bitmap){
        bitmapLruCache.put(url,bitmap);
    }
    public Bitmap get(String url){
        return bitmapLruCache.get(url);
    }
}
