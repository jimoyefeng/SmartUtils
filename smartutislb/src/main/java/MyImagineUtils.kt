package com.example.user.testkotlin.FileUtils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by admin on 2018/2/12.
 */

public class MyImagineUtils {

    fun showMsg(context: Context, msg: String?) {

        if (null != msg)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()


    }


    fun getBtpathFormUri(context: Context, uri: Uri): String? {

        var input = context.contentResolver.openInputStream(uri)
        val onlyBoundsOptions = BitmapFactory.Options()
        onlyBoundsOptions.inJustDecodeBounds = true
        onlyBoundsOptions.inDither = true//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
        input!!.close()
        val originalWidth = onlyBoundsOptions.outWidth
        val originalHeight = onlyBoundsOptions.outHeight
        if (originalWidth == -1 || originalHeight == -1)
            return null
        //图片分辨率以480x800为标准
        val hh = 800f//这里设置高度为800f
        val ww = 480f//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (originalWidth / ww).toInt()
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (originalHeight / hh).toInt()
        }
        if (be <= 0)
            be = 1
        //比例压缩
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = be//设置缩放比例
        bitmapOptions.inDither = true//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
        input = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
        input!!.close()

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中


        Log.i("TEST", "压缩图片z前的大小-=-=》" + baos.toByteArray().size / 1024)
        var options = 100
        //        Log.i("TEST","压缩图片前的大小-=-=》"+baos.toByteArray().length / 1024);
        while (baos.toByteArray().size / 1024 > 200) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset()//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)//这里压缩options%，把压缩后的数据存放到baos中
            options -= 20//每次都减少30
        }


        val foloder = FilePathUtils.getCachePath(context) + "/app/mypic/"
        val savedir = File(foloder)
        if (!savedir.exists()) {
            savedir.mkdirs()
        }
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss")
                .format(Date())
        // 照片命名
        val picName = timeStamp + ".jpg"
        //  裁剪头像的绝对路径
        val CameraImgPath = foloder + picName

        val file = File(CameraImgPath)//将要保存图片的路径
        baos.writeTo(FileOutputStream(file))

        Log.i("TEST", "压缩图片后的大小-=-=》" + file.length() / 1024)

        baos.flush()
        baos.close()


        return CameraImgPath
    }
}
