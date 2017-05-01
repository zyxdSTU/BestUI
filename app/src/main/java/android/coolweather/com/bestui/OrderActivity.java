package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.JavaBean.Orsder;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.Produces;
import android.coolweather.com.bestui.JavaBean.SerializableMap;
import android.coolweather.com.bestui.adapter.ProduceOrderAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Produces> producesList;
    private Address address = new Address();
    private ImageButton addAddressButton;
    private Button submitButton;
    private TextView totalMoneyText;
    private RecyclerView recyclerView;
    private TextView nameText;
    private TextView phoneNumText;
    private TextView addressText;
    private ProduceOrderAdapter produceOrderAdapter;
    private boolean addressFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        producesList = new ArrayList<Produces>();
        initList();
        nameText = (TextView) findViewById(R.id.name_text);
        phoneNumText = (TextView) findViewById(R.id.phoneNum_text);
        addressText = (TextView) findViewById(R.id.address_text);

        addAddressButton = (ImageButton) findViewById(R.id.add_address_button);
        submitButton = (Button)findViewById(R.id.submit_button);
        totalMoneyText = (TextView) findViewById(R.id.totalMoney_text);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        produceOrderAdapter = new ProduceOrderAdapter(producesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(produceOrderAdapter);

        totalMoneyText.setText(getIntent().getStringExtra("totalMoney"));

        addAddressButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    public void initList() {
        Bundle bundle = getIntent().getExtras();
        SerializableMap buyMapF = (SerializableMap) bundle.get("buyMapF");
        HashMap<ProduceCart, Double> buyMap = buyMapF.getMap();

        Iterator iterator = buyMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ProduceCart key = (ProduceCart) entry.getKey();
            Double value = (Double) entry.getValue();
            producesList.add(new Produces(key, value));
        }
    }

    public void setAddress() {
        List<Address> addressList = DataSupport.where("status = 1").find(Address.class);
        if(addressList.size() != 0) {
            addressFlag = true;
            address = addressList.get(0);
            nameText.setText(address.getName());
            phoneNumText.setText(address.getPhoneNum());
            addressText.setText(address.getAddress());
        } else {
            addressFlag = false;
            Toast.makeText(OrderActivity.this, "没有默认地址,请添加", Toast.LENGTH_SHORT).show();
            nameText.setText("null");
            phoneNumText.setText("null");
            addressText.setText("null");
        }
    }
    @Override
    protected void onResume() {
        setAddress();
        super.onResume();
    }

    public void test() {
        SimpleDateFormat formatter =  new  SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        Orsder order = new Orsder();
        order.setAddress(address);
        order.setProduceList(producesList);
        order.setId(str);
        order.save();

        List<Orsder> orderTempList = DataSupport.findAll(Orsder.class);
        Log.d("MainActivity", String.valueOf(orderTempList.size()));
        //Orsder orderTemp = orderTempList.get(0);
        //Log.d("MainActivity", orderTemp.getAddress().getAddress());
       //Log.d("MainActivity", orderTemp.getProduceList().get(0).getName());
        //Log.d("MainActivity", orderTemp.getId());
        for(Orsder orsder: orderTempList) {
            Log.d("MainActivity", orsder.getId());
            Address address = orsder.getAddress();
            if(address != null) {
                Log.d("MainActivity", address.getAddress());
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.add_address_button:
                addAddress();
                break;
            case R.id.submit_button:
                submit();
                break;
            default:
                break;
        }
    }

    public void addAddress() {
        startActivity(new Intent(this, AddressActivity.class));
    }

    public void submit() {
        if(addressFlag) {
            test();
        } else{
            Toast.makeText(OrderActivity.this, "没有默认地址,请添加", Toast.LENGTH_SHORT).show();
        }
    }
}
