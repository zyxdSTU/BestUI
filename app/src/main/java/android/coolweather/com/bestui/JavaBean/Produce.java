package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Produce extends DataSupport{
    private long image;
    private String name;
    private double  price;
    private String description;

    public Produce() {}

    public Produce(long image, String name, double price, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
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
