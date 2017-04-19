package android.coolweather.com.bestui.fragment;

import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.adapter.ProduceAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 */

public class HomeFragment extends BaseFragment {
    private ArrayList<Produce> produceList = new ArrayList<Produce>();

    @Override
    public View initView() {
        initList();
        View view =  View.inflate(mContext, R.layout.layout_home, null);
        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        ProduceAdapter adapter = new ProduceAdapter(produceList);
        recycleView.setAdapter(adapter);
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
        for(int i = 0; i < 40; i++) {
            produceList.add(new Produce(R.drawable.apple, "苹果", 22, "十分新鲜"));
            produceList.add(new Produce(R.drawable.pear, "梨", 33, "很不错"));
            produceList.add(new Produce(R.drawable.grape, "葡萄", 22, "很美味"));
        }
    }
}
