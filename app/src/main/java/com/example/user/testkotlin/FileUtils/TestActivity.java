package com.example.user.testkotlin.FileUtils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.testkotlin.R;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {

    Button btn_write, btn_read;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);

        btn_write = findViewById(R.id.btn_write);
        btn_read = findViewById(R.id.btn_read);
        getFile();
    }


    private void getFile() {

        btn_write.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {











                                         }
                                     }


        );


    }




    //打开相机
    fun openCama() {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss")
            .format(Date())
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //  拍照后保存图片的绝对路径
        var path = FilePathUtils.getCachePath(this @PurchasePersonActivity)+"/app/mypic/"
        val savedir = File(path)
        if (!savedir.exists()) {
            savedir.mkdirs()
        }
        cameraPath = path + timeStamp + ".jpg"
        val file = File(cameraPath)

        var fileUri:Uri
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(this @PurchasePersonActivity,
            BuildConfig.APPLICATION_ID + ".fileprovider", file)
        } else {
            fileUri = Uri.fromFile(file)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent, REQUEST_CODE_CAMARA)


    }




    public void takePhotoNoCompress(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = FileProvider.getUriForFile(this, "com.zhy.android7.fileprovider", file);

            List<ResolveInfo> resInfoList = getPackageManager()
                .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

}



