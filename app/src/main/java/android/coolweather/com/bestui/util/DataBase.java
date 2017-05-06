package android.coolweather.com.bestui.util;

import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.ProduceCollect;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/5/6.
 */

public class DataBase {
    public static Produce selectProduceByName(String name) {
        List<Produce> list = DataSupport.where("name = ?", name).find(Produce.class);
        if(list.size() > 0) return list.get(0);
        else return null;
    }


    public static boolean isExitInCart(String name) {
        List<ProduceCart> list = DataSupport.where("name = ?", name).find(ProduceCart.class);
        if(list.size() > 0) return true;
        else return false;
    }


    public static boolean isExitInCollect(String name) {
        List<ProduceCollect> list = DataSupport.where("name = ?", name).find(ProduceCollect.class);
        if(list.size() > 0) return true;
        else return false;
    }

    public static boolean isExitProduce(String name) {
        List<Produce> list = DataSupport.where("name = ?", name).find(Produce.class);
        if(list.size() > 0) return true;
        else return false;
    }
}
