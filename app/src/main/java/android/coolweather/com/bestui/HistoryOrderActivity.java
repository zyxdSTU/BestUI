package android.coolweather.com.bestui;

import android.coolweather.com.bestui.JavaBean.Order;
import android.coolweather.com.bestui.JavaBean.OrderAddress;
import android.coolweather.com.bestui.JavaBean.OrderProduces;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.Produces;
import android.coolweather.com.bestui.adapter.ProduceHistoryOrderAdapter;
import android.coolweather.com.bestui.util.DataBase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity {

    private LinearLayout addLinearLayout;
    private RecyclerView recyclerView;
    private ProduceHistoryOrderAdapter adapter;
    private List<Order> orderList = new ArrayList<Order>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        initList();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new ProduceHistoryOrderAdapter(orderList);
        recyclerView.setAdapter(adapter);
    }

    public void initList() {
        List<OrderAddress> orderAddressList = DataSupport.findAll(OrderAddress.class);

        for(OrderAddress orderAddress : orderAddressList) {
            Order order = new Order();
            order.setAddress(orderAddress.getAddressObject());
            order.setIdentifier(orderAddress.getIdentifier());
            String identifier = orderAddress.getIdentifier();
            List<OrderProduces> orderProducesList = DataSupport.where("identifier = ?", identifier).find(OrderProduces.class);

            List<Produces> producesList = new ArrayList<Produces>();
            for(OrderProduces orderProduces : orderProducesList) {
                String produceName = orderProduces.getProduceName();
                double  quantity = orderProduces.getQuantity();
                Produces produces = new Produces(produceName, quantity);
                producesList.add(produces);
            }
            order.setProducesList(producesList);
            orderList.add(order);
        }
    }
}
