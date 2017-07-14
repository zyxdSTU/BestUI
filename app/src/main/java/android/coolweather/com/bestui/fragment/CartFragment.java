package android.coolweather.com.bestui.fragment;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.SerializableMap;
import android.coolweather.com.bestui.OrderActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.adapter.ProduceCartAdapter;
import android.coolweather.com.bestui.util.DataBase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/9.
 */

public class CartFragment extends Fragment implements View.OnClickListener{
    private ArrayList<ProduceCart> produceCartList = new ArrayList<ProduceCart>();
    private ProduceCartAdapter adapter;
    private RecyclerView recyclerView;
    private ImageButton trashButton;

    private CheckBox checkBox;
    private TextView totalText;
    private TextView wayText;
    private Button calculateButton;

    private Handler handler;
    final int UPDATE_TEXT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initList();
        View view = inflater.inflate(R.layout.layout_cart, container, false);
        /**
         * 消息事件
         */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case UPDATE_TEXT:
                        updateText();
                        break;
                    default:
                        break;
                }
            }
        };
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        trashButton = (ImageButton) view.findViewById(R.id.trash_button);
        checkBox = (CheckBox) view.findViewById(R.id.check_box);
        totalText = (TextView) view.findViewById(R.id.total_text);
        wayText = (TextView) view.findViewById(R.id.way_text);
        calculateButton = (Button) view.findViewById(R.id.calculate_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProduceCartAdapter(produceCartList, handler);

        recyclerView.setAdapter(adapter);

        trashButton.setOnClickListener(this);
        calculateButton.setOnClickListener(this);

        /**
         * 全选按钮
         */
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    selectedAll();
                } else selectedNo();
            }
        });

        return view;
    }

    /**
     * 填充list
     */
    public void initList() {
        produceCartList.clear();
        for(ProduceCart produceCart : DataSupport.findAll(ProduceCart.class)) {
            if(DataBase.isExitProduce(produceCart.getName())) {
                produceCartList.add(produceCart);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.trash_button:
                deleteItem();
                break;
            case R.id.calculate_button:
                calculate();
                break;
            default:
                break;
        }
    }

    /**
     * 删除
     */
    public void deleteItem() {
        ArrayList<ProduceCart> tempProduceList = new ArrayList<>();
        for(int i = 0; i < produceCartList.size(); i++) {
            if(!adapter.checkList.contains(new Integer(i))) {
                tempProduceList.add(produceCartList.get(i));
            }
        }

        /**更新数据库** bug*/
        DataSupport.deleteAll(ProduceCart.class);
        for(ProduceCart produceCart : tempProduceList) {
            new ProduceCart(produceCart.getName()).save();
        }

        produceCartList.clear();
        produceCartList.addAll(tempProduceList);

        /**维护checkMap,buyMap**/
        changeMap();
        checkBox.setChecked(false);
        adapter.checkList.clear();
        adapter.notifyDataSetChanged();
        sendMessage();
    }

    /**
     * 总钱数
     */
    public void calculate() {
        Intent intent = new Intent(getActivity(), OrderActivity.class);

        /**传递map**/
        Bundle bundle = new Bundle();
        SerializableMap serializableMap = new SerializableMap();
        serializableMap.setMap(adapter.buyMap);
        bundle.putSerializable("buyMapF", serializableMap);
        intent.putExtras(bundle);
        intent.putExtra("totalMoney",totalText.getText().toString());
        startActivity(intent);
    }

    /**
     * 全选
     */
    public void selectedAll() {
        for(int i = 0; i < produceCartList.size(); i++) {
            if(!adapter.checkList.contains(new Integer(i)))
            adapter.checkList.add(new Integer(i));
        }
        adapter.notifyDataSetChanged();

        /**
         * 发送消息,更新buyMap
         */
        adapter.buyMap.clear();
        adapter.buyMap.putAll(adapter.checkMap);
        sendMessage();
    }

    /**
     * 全不选
     */
    public void selectedNo() {
        adapter.checkList.clear();
        adapter.notifyDataSetChanged();
        /**
         * 发送消息
         */
        adapter.buyMap.clear();
        sendMessage();
    }

    /**
     * 更新总金额
     */
    public void updateText() {
        double total = 0;
        Iterator iterator = adapter.buyMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ProduceCart key = (ProduceCart) entry.getKey();
            Double value = (Double) entry.getValue();

            Produce produce = DataBase.selectProduceByName(key.getName());
            total += value * produce.getPrice();
        }
        totalText.setText("¥" + String.valueOf(total));
        calculateButton.setText("去结算 (" + adapter.buyMap.size()+")");
    }


    public void changeMap() {
            /**从checkMap中删除元素**/
            for(ProduceCart produceCart : adapter.buyMap.keySet()) {
                adapter.checkMap.remove(produceCart);
            }
            adapter.buyMap.clear();
    }

    /**可见时调用**/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            /**更新UI**/
            initList();
            adapter.checkList.clear();
            adapter.checkMap.clear();
            adapter.initCheckMap();
            adapter.buyMap.clear();
            adapter.notifyDataSetChanged();
            checkBox.setChecked(false);
            sendMessage();
        }
    }

    public void sendMessage() {
        Message msg = new Message();
        msg.what = UPDATE_TEXT;
        handler.sendMessage(msg);
    }
}
