package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.adapter.AddressAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {

    private AddressAdapter adapter;
    private ArrayList<Address> mList;
    private RecyclerView recyclerView;
    private Button addAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mList = new ArrayList<>();
        adapter = new AddressAdapter(mList);

        /**
         * 临时添加
         */
        mList.add(new Address("周坤", "18283476278", "长沙市国防科技大学255号", false));
        mList.add(new Address("周坤", "18283476278", "长沙市国防科技大学255号", false));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        addAddressButton = (Button) findViewById(R.id.addAddress_button);

        /**
         * 新增跳转
         */
        String addressAddJson=getIntent().getStringExtra("add_address");
        if(addressAddJson != null) {
            mList.add(new Gson().fromJson(addressAddJson, Address.class));
            adapter.notifyDataSetChanged();
        }

        /**
         * 编辑跳转
         */
        String addressEditJson = getIntent().getStringExtra("edit_address");
        int position = getIntent().getIntExtra("position", 0);
        if(addressEditJson != null && position != -1) {
            Address address = new Gson().fromJson(addressEditJson, Address.class);
            mList.get(position).setName(address.getName());
            mList.get(position).setPhoneNum(address.getPhoneNum());
            mList.get(position).setAddress(address.getAddress());
            mList.get(position).setStatus(address.getStatus());
            adapter.notifyDataSetChanged();
        }

        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddActivity();
            }
        });
    }

    public void gotoAddActivity() {
        Intent intent = new Intent(AddressActivity.this, AddActivity.class);
        startActivity(intent);
    }
}
