package android.coolweather.com.bestui;

import android.content.ContentValues;
import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.adapter.AddressAdapter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {

    private AddressAdapter adapter;
    private ArrayList<Address> mList;
    private RecyclerView recyclerView;
    private Button addAddressButton;
    private final int REFRESH = 1;
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case REFRESH:
                    refresh();
                    break;
                default:
                    break;
            }
        }
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        /**
         * 数据库更新
         */
        mList = new ArrayList<>();
        updateList();

        adapter = new AddressAdapter(mList, handler);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        addAddressButton = (Button) findViewById(R.id.addAddress_button);


        /**
         * 编辑跳转
         */
        String addressEditJson = getIntent().getStringExtra("edit_address");
        int position = getIntent().getIntExtra("position", -1);
        if(addressEditJson != null && position != -1) {
            Address address = new Gson().fromJson(addressEditJson, Address.class);

            /**唯一默认地址**/
            if(address.getStatus()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("status", "0");
                DataSupport.updateAll(Address.class, contentValues, "status = ?", "1");
            }

            mList.get(position).setName(address.getName());
            mList.get(position).setPhoneNum(address.getPhoneNum());
            mList.get(position).setAddress(address.getAddress());
            mList.get(position).setStatus(address.getStatus());
            mList.get(position).save();
            updateList();
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
        finish();
        startActivity(intent);
    }

    /**
     * 从数据库读取数据
     */
    public void updateList() {
        mList.clear();
        mList.addAll(DataSupport.findAll(Address.class));
    }


    public void refresh() {
        finish();
        startActivity(new Intent(AddressActivity.this, AddressActivity.class));
    }
}
