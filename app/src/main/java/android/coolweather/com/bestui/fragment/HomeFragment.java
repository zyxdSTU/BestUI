package android.coolweather.com.bestui.fragment;

import android.app.ProgressDialog;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.adapter.ProduceAdapter;
import android.coolweather.com.bestui.util.HttpUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.coolweather.com.bestui.util.HttpUtil.urlProduce;

/**
 * Created by Administrator on 2017/4/9.
 */

public class HomeFragment extends Fragment{
    private ArrayList<Produce> produceList = new ArrayList<Produce>();
    private ProduceAdapter adapter;
    private ProgressDialog progressDialog;
    private final static int UPDATE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           int action = msg.what;
           switch (action) {
               case UPDATE:
                   update();
                   break;
               default:
                   break;
           }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initList();
        View view = inflater.inflate(R.layout.layout_home, container, false);
        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        adapter = new ProduceAdapter(produceList);
        recycleView.setAdapter(adapter);
        return view;
    }
    /**
     * 填充list
     */
    public void initList() {
        if(produceList.size() == 0) {
            produceList.addAll(DataSupport.findAll(Produce.class));
            /**数据库没数据， 网络加载**/
            if(produceList.size() == 0) {
                queryProduce(urlProduce);
            }
        }
    }

    /**刷新画面问题**/
    public void queryProduce(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                HttpUtil.handleProduceResponse(responseText);

                /**发送更新消息**/
                Message msg = new Message(); msg.what = UPDATE;
                handler.sendMessage(msg);
            }
        });
    }

    private void update() {
        initList();
        adapter.notifyDataSetChanged();
    }
}
