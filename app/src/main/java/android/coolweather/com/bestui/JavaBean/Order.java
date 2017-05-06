package android.coolweather.com.bestui.JavaBean;

import android.coolweather.com.bestui.util.DataBase;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Order {
    private Address address;
    private List<Produces> producesList;
    private String identifier;
    public Order() {}
    public Order(Address address, List<Produces> producesList, String identifier) {
        this.address = address;
        this.producesList = producesList;
        this.identifier = identifier;
    }
    public Address getAddress() { return address;}
    public List<Produces> getProducesList() {return producesList;}
    public String getIdentifier() { return identifier; }

    public void setAddress(Address address) {this.address = address;}
    public void setProducesList(List<Produces> producesList) {this.producesList = producesList;}
    public void setIdentifier(String identifier) { this.identifier = identifier;}

    public double getTotalMoney() {
        double totalMoney = 0;
        for(Produces produces : producesList) {
            Produce produce = DataBase.selectProduceByName(produces.getName());
            if(produce == null) {
                totalMoney += 0 * produces.getQuantity();
            } else {
                totalMoney += produce.getPrice() * produces.getQuantity();
            }
        }
        return totalMoney;
    }
}

