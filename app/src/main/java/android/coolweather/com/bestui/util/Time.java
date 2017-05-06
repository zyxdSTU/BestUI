package android.coolweather.com.bestui.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/1.
 */

public class Time {
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = df.format(new Date());
        return currentTime;
    }
}
