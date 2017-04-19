package android.coolweather.com.bestui.fragment;

import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.adapter.ProduceCartAdapter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/9.
 */

public class CartFragment extends BaseFragment implements View.OnClickListener{
    private ArrayList<Produce> produceList = new ArrayList<Produce>();
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
    public View initView() {
        initList();
        View view =  View.inflate(mContext, R.layout.layout_cart, null);

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
        adapter = new ProduceCartAdapter(produceList, handler);
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

    @Override
    public View initData() {
        return null;
    }

    /**
     * 临时的函数
     */
    public void initList() {
        for(int i = 0; i < 10; i++) {
            produceList.add(new Produce(R.drawable.apple, "苹果", 22, "十分新鲜"));
            produceList.add(new Produce(R.drawable.pear, "梨", 33, "很不错"));
            produceList.add(new Produce(R.drawable.grape, "葡萄", 22, "很美味"));
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
     * 删除收藏
     */
    public void deleteItem() {
        ArrayList<Produce> tempProduceList = new ArrayList<>();
        for(int i = 0; i < produceList.size(); i++) {
            if(!adapter.checkList.contains(new Integer(i))) {
                tempProduceList.add(produceList.get(i));
            }
        }
        produceList.clear();
        produceList.addAll(tempProduceList);
        adapter.checkList.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 总钱数
     */
    public void calculate() {

    }

    /**
     * 全选
     */
    public void selectedAll() {
        for(int i = 0; i < produceList.size(); i++) {
            if(!adapter.checkList.contains(new Integer(i)))
            adapter.checkList.add(new Integer(i));
        }
        adapter.notifyDataSetChanged();

        /**
         * 发送消息
         */
        adapter.buyMap.putAll(adapter.checkMap);
        Message msg = new Message();
        msg.what = UPDATE_TEXT;
        handler.sendMessage(msg);
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
        Message msg = new Message();
        msg.what = UPDATE_TEXT;
        handler.sendMessage(msg);
    }

    /**
     * 更新总金额
     */
    public void updateText() {
        double total = 0;
        Iterator iterator = adapter.buyMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Produce key = (Produce) entry.getKey();
            Double value = (Double) entry.getValue();
            total += value * key.getPrice();
        }

        totalText.setText(String.valueOf(total));
    }
}
