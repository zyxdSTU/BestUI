package android.coolweather.com.bestui.JavaBean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class Address {
    private String name;
    private String phoneNum;
    private String address;
    private boolean status;

    public Address(String name, String phoneNum, String address, boolean status) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.status = status;
    }

    public String getName() { return name; }
    public String getPhoneNum() { return phoneNum; }
    public String getAddress () {return address; }
    public boolean getStatus () {return status; }

    public void setName (String name) { this.name = name; }
    public void setPhoneNum (String phoneNum) { this.phoneNum = phoneNum; }
    public void setAddress (String address) { this.address = address; }
    public void setStatus (boolean status) { this.status = status; }
}
