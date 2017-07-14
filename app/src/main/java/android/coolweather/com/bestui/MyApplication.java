package android.coolweather.com.bestui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.coolweather.com.bestui.util.PreferenceManager;

import org.litepal.LitePal;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

/**
 * 初始化环信SDK
 */
public class MyApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        /*初始化数据库，和缓存*/
        LitePal.initialize(mContext);
        PreferenceManager.init(mContext);
    }
}