package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
/**
 * Created by Administrator on 2017/4/14.
 */
/**仅用做数据库填充**/
public class ProduceCart extends DataSupport implements Serializable{
    private String name;

    public ProduceCart() {}

    public ProduceCart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
