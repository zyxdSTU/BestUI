package android.coolweather.com.bestui;

import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.ProduceCollect;
import android.coolweather.com.bestui.adapter.ProduceCollectAdapter;
import android.coolweather.com.bestui.util.DataBase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<ProduceCollect> produceCollectList = new ArrayList<ProduceCollect>();
    private ProduceCollectAdapter adapter;
    private ImageButton trashButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        initList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProduceCollectAdapter(produceCollectList);
        recyclerView.setAdapter(adapter);
        trashButton = (ImageButton) findViewById(R.id.trash_button);
        trashButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.trash_button:
                deleteItem();
                break;
            default:
                break;
        }
    }

    public void deleteItem() {
        ArrayList<ProduceCollect> tempProduceList = new ArrayList<>();

        for(int i = 0; i < produceCollectList.size(); i++) {
            if(!adapter.checkList.contains(new Integer(i))) {
                tempProduceList.add(produceCollectList.get(i));
            }
        }

        /**更新数据库**/
        DataSupport.deleteAll(ProduceCollect.class);
        for(ProduceCollect produceCollect : tempProduceList) {
           new ProduceCollect(produceCollect.getName()).save();
        }

        produceCollectList.clear();
        produceCollectList.addAll(tempProduceList);
        adapter.checkList.clear();
        adapter.notifyDataSetChanged();
    }

    public void initList() {
        produceCollectList.clear();
        for(ProduceCollect produceCollect : DataSupport.findAll(ProduceCollect.class)) {
            if(DataBase.isExitProduce(produceCollect.getName())) {
                produceCollectList.add(produceCollect);
            }
        }
    }
}