package medicine.glide;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

public class MainActivity extends Activity {
    @ViewInject(R.id.testview)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        onCall(true);
        String filePath = "/storage/emulated/0/hubeichengjian.mp4";
        Glide.with(this)
                .load(Uri.fromFile(new File(filePath)))
                .into(imageView);

    }

    public void onCall(Boolean result) {

        if (Build.VERSION.SDK_INT >= 23) {
            int WRITEPhonePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            int CAMERAPhonePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (WRITEPhonePermission != PackageManager.PERMISSION_GRANTED&&CAMERAPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
                //ActivityCompat.requestPermissions(Function_Activity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                return;
            }
        }
    }
}
