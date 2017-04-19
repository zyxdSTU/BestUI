package android.coolweather.com.bestui.JavaBean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Produce {
    private int imageId;
    private String name;
    private double  price;
    private String text;

    public Produce(int imageId, String name, double price, String text) {
        this.imageId = imageId;
        this.name = name;
        this.price = price;
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getText() {
        return text;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setText(String text) {
        this.text = text;
    }

}
