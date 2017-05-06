package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/29.
 */

/**仅用做数据库填充**/
public class ProduceCollect extends DataSupport {
    private String name;

    public ProduceCollect() {}

    public ProduceCollect(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

