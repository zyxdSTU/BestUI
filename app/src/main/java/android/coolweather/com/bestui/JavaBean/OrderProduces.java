package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/5/1.
 */

public class OrderProduces extends DataSupport{
    private String identifier;
    private String produceName;
    private double quantity;

    public String getIdentifier() { return identifier; }
    public String getProduceName() { return produceName; }
    public double getQuantity() { return quantity; }

    public void setIdentifier(String identifier) { this.identifier = identifier;}
    public void setProduceName(String produceName) { this.produceName = produceName; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}
