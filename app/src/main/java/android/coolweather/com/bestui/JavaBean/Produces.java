package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Produces extends DataSupport {
    private String name;
    private double quantity;

    public Produces() {}

    public Produces(ProduceCart produceCart, double quantity) {
        this.name = produceCart.getName();
        this.quantity = quantity;
    }
    public Produces(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public double getQuantity() { return quantity; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(double quantity) { this.quantity = quantity;}
}
