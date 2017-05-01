package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
/**
 * Created by Administrator on 2017/4/14.
 */
/**仅用做数据库填充**/
public class ProduceCart extends DataSupport implements Serializable{
    private long image;
    private String name;
    private double  price;
    private String description;

    public ProduceCart() {}

    public ProduceCart(Produce produce) {
        this.name = produce.getName();
        this.price = produce.getPrice();
        this.description = produce.getDescription();
        this.image = produce.getImage();
    }

    public ProduceCart(long image, String name, double price, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProduceCart(ProduceCart produceCart) {
        this.image = produceCart.getImage();
        this.name = produceCart.getName();
        this.price = produceCart.getPrice();
        this.description = produceCart.getDescription();
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
