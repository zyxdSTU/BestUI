package android.coolweather.com.bestui.JavaBean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/1.
 */

public class SerializableMap implements Serializable {
    private HashMap<ProduceCart, Double> map;

    public void setMap(HashMap<ProduceCart, Double> map) { this.map = map;}
    public HashMap<ProduceCart, Double>  getMap() { return map;}
}
