package work.cano.savesensorvaluetocsv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class Utils {

    public static String getNowDate() {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.JAPAN);
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

}