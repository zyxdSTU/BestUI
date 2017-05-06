package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.JavaBean.OrderAddress;
import android.coolweather.com.bestui.JavaBean.OrderProduces;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.Produces;
import android.coolweather.com.bestui.JavaBean.SerializableMap;
import android.coolweather.com.bestui.adapter.ProduceOrderAdapter;
import android.coolweather.com.bestui.util.HttpUtil;
import android.coolweather.com.bestui.util.Time;
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

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.coolweather.com.bestui.util.HttpUtil.urlOrderAddress;
import static android.coolweather.com.bestui.util.HttpUtil.urlOrderProduces;

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
        if(buyMapF != null) {
            HashMap<ProduceCart, Double> buyMap = buyMapF.getMap();

            Iterator iterator = buyMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                ProduceCart key = (ProduceCart) entry.getKey();
                Double value = (Double) entry.getValue();
                producesList.add(new Produces(key, value));
            }
        }
        String producesJson = getIntent().getStringExtra("produces");
        if(producesJson != null) {
            producesList.add(new Gson().fromJson(producesJson, Produces.class));
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
            String identifier = Time.getCurrentTime();
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setAddressObject(address);
            orderAddress.setIdentifier(identifier);
            orderAddress.save();

            List<OrderProduces> orderProducesList = new ArrayList<>();
            for(Produces produces : producesList) {
                OrderProduces orderProduces = new OrderProduces();
                orderProduces.setIdentifier(identifier);
                orderProduces.setProduceName(produces.getName());
                orderProduces.setQuantity(produces.getQuantity());
                orderProducesList.add(orderProduces);
                orderProduces.save();
            }
            /**发送订单数据**/
            String orderAddressStr = new Gson().toJson(orderAddress);
            String orderProducesListStr = new Gson().toJson(orderProducesList);
            Log.d("MainActivity", orderAddressStr);
            Log.d("MainActivity", orderProducesListStr);
            sendToServer(orderAddressStr, orderProducesListStr);

            startActivity(new Intent(OrderActivity.this, SubmitActivity.class));
        } else{
            Toast.makeText(OrderActivity.this, "没有默认地址,请添加", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendToServer(String orderAddressStr, String orderProducesListStr) {
        HttpUtil.sendOKHttpPost(orderAddressStr, urlOrderAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OrderActivity.this, "发送订单数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        HttpUtil.sendOKHttpPost(orderProducesListStr, urlOrderProduces, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OrderActivity.this, "发送订单数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
