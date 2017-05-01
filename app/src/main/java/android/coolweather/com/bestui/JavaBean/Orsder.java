package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Orsder extends DataSupport {
    private Address address;
    private List<Produces> producesList;
    private String idString;
    public Orsder() {}
    public Orsder(Address address, List<Produces> producesList, String idString) {
        this.address = address;
        this.producesList = producesList;
        this.idString = idString;
    }
    public Address getAddress() { return address;}
    public List<Produces> getProduceList() {return producesList;}
    public String getId() { return idString; }

    public void setAddress(Address address) {this.address = address;}
    public void setProduceList(List<Produces> producesList) {this.producesList = producesList;}
    public void setId(String idString) { this.idString = idString;}
    public double getTotalMoney() {
        double totalMoney = 0;
        for(Produces produces : producesList) {
            totalMoney += produces.getPrice() * produces.getQuantity();
        }
        return totalMoney;
    }
}

