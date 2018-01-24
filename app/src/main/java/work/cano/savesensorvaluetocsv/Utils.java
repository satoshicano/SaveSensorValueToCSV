package work.cano.savesensorvaluetocsv;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static String getNowDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS", Locale.JAPAN);
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    public static File getStorageDir(String name) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), name);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

}