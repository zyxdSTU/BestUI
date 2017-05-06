package android.coolweather.com.bestui.JavaBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/5/1.
 */

public class OrderAddress extends DataSupport{
    private String identifier;
    private String name;
    private String phoneNum;
    private String address;

    public String getIdentifier() { return identifier;}
    public String getName() { return name; }
    public String getPhoneNum() { return phoneNum; }
    public String getAddress () {return address; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public void setName (String name) { this.name = name; }
    public void setPhoneNum (String phoneNum) { this.phoneNum = phoneNum; }
    public void setAddress (String address) { this.address = address; }

    public Address getAddressObject() {
        Address addressObject = new Address();
        addressObject.setName(name);
        addressObject.setPhoneNum(phoneNum);
        addressObject.setAddress(address);
        return addressObject;
    }

    public void setAddressObject(Address addressObject) {
        name = addressObject.getName();
        phoneNum = addressObject.getPhoneNum();
        address = addressObject.getAddress();
    }
}
