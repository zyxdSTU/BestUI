package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Produces extends DataSupport{
    private long image;
    private String name;
    private double  price;
    private String description;
    private double quantity;

    public Produces() {}

    public Produces(Produce produce, double quantity) {
        this.image = produce.getImage();
        this.name = produce.getName();
        this.price = produce.getPrice();
        this.description = produce.getDescription();
        this.quantity = quantity;
    }

    public Produces(ProduceCart produceCart, double quantity) {
        this.image = produceCart.getImage();
        this.name = produceCart.getName();
        this.price = produceCart.getPrice();
        this.description = produceCart.getDescription();
        this.quantity = quantity;
    }
    public Produces(long image, String name, double price, String description, double quantity) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public double getQuantity() { return quantity; }

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

    public void setQuantity(double quantity) { this.quantity = quantity;}
}
