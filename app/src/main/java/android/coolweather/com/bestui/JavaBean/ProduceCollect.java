package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/29.
 */

/**仅用做数据库填充**/
public class ProduceCollect extends DataSupport {
    private long image;
    private String name;
    private double  price;
    private String description;

    public ProduceCollect() {}

    public ProduceCollect(Produce produce) {
        this.name = produce.getName();
        this.price = produce.getImage();
        this.description = produce.getDescription();
        this.image = produce.getImage();
    }

    public ProduceCollect(long image, String name, double price, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProduceCollect(ProduceCollect produceCollect) {
        this.image = produceCollect.getImage();
        this.name = produceCollect.getName();
        this.price = produceCollect.getPrice();
        this.description = produceCollect.getDescription();
    }

    public long getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(long image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

