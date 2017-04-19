package android.coolweather.com.bestui;

import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.adapter.ProduceCollectAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Produce> produceList = new ArrayList<Produce>();
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
        adapter = new ProduceCollectAdapter(produceList);
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

    public void initList() {
        for(int i = 0; i < 10; i++) {
            produceList.add(new Produce(R.drawable.apple, "苹果", 22, "十分新鲜"));
            produceList.add(new Produce(R.drawable.pear, "梨", 33, "很不错"));
            produceList.add(new Produce(R.drawable.grape, "葡萄", 22, "很美味"));
        }
    }

}