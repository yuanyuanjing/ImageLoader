package medicine.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class mImageLoader {
       ImageCache imageCache=new ImageCache();
       ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void displatyImage(final String url, final ImageView imageView){
        final Bitmap bitmap=imageCache.get(url);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1=downloadImage(url);
                if (bitmap1==null){
                    return;
                }
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap1);
                }
                imageCache.put(url,bitmap);
            }
        });
    }
    public Bitmap downloadImage(String imageUrl){
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            final HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
