import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {


    public static String getCashDir(Context context) {

        File cacheDir;
        if (android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable())
            cacheDir = context.getExternalCacheDir();
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();

        return cacheDir.getAbsolutePath();


    }
}
