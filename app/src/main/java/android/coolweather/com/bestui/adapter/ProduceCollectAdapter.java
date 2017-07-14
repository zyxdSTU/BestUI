package android.coolweather.com.bestui.adapter;

/**
 * Created by Administrator on 2017/4/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.ProduceCollect;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.util.DataBase;
import android.coolweather.com.bestui.util.HttpUtil;
import android.coolweather.com.bestui.util.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.coolweather.com.bestui.util.HttpUtil.urlDownload;
import static android.coolweather.com.bestui.util.HttpUtil.urlImage;

/**
 * Created by Administrator on 2017/4/13.
 */

public class ProduceCollectAdapter extends RecyclerView.Adapter<ProduceCollectAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ProduceCollect> mProduceCollectList;
    public ArrayList<Integer> checkList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView produceImage;
        TextView produceName;
        TextView producePrice;
        CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            produceImage = (ImageView) view.findViewById(R.id.produce_image);
            produceName = (TextView) view.findViewById(R.id.produce_name);
            producePrice = (TextView) view.findViewById(R.id.produce_price);
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
        }
    }

    public ProduceCollectAdapter(ArrayList<ProduceCollect> produceCollectList) {
        mProduceCollectList= produceCollectList;
        checkList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.produce_collect_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        /**
         * 图片点击事件
         */
        holder.produceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ProduceCollect produceCollect = mProduceCollectList.get(position);
                Intent intent=new Intent(mContext, ProduceItemActivity.class);
                Produce produce = DataBase.selectProduceByName(produceCollect.getName());
                intent.putExtra("produce",new Gson().toJson(produce));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProduceCollect produceCollect = mProduceCollectList.get(position);
        Produce produce = DataBase.selectProduceByName(produceCollect.getName());
        /*String tempUrl = urlImage + produce.getImage();
        Glide.with(mContext).load(tempUrl).into(holder.produceImage);*/
        loadImage(holder);
        holder.produceName.setText(produce.getName());
        holder.producePrice.setText("¥" + String.valueOf(produce.getPrice()));
        /**
         * 防止checkbox乱序
         */
        holder.checkBox.setTag(new Integer(position));
        if(checkList != null) {
            if (checkList.contains(new Integer(position))) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(!checkList.contains(holder.checkBox.getTag())) {
                        checkList.add((Integer)holder.checkBox.getTag());
                    }
                }else {
                    if(checkList.contains(holder.checkBox.getTag())){
                        checkList.remove(holder.checkBox.getTag());
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mProduceCollectList.size();
    }

    public void loadImage(final ProduceCollectAdapter.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        final ProduceCollect produceCollect = mProduceCollectList.get(position);
        final Produce produce = DataBase.selectProduceByName(produceCollect.getName());

        /**如果缓存有直接从缓存中加载**/
        if (!PreferenceManager.getInstance().preferenceManagerGet(String.valueOf(produce.getImage())).equals("")) {
            String imageString = PreferenceManager.getInstance().preferenceManagerGet(String.valueOf(produce.getImage()));
            byte[] imageByte = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
            Glide.with(mContext).load(imageByte).into(holder.produceImage);
        } else {
            /**从网络加载进缓存**/
            HttpUtil.sendOkHttpRequest(urlDownload + String.valueOf(produce.getImage()), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("MainActivity", "从网络加载图片失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final byte[] imageByte = response.body().bytes();
                    if (mContext instanceof Activity) {
                        Activity mActivity = (Activity) mContext;
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (imageByte.length > 0) {
                                    Glide.with(mContext).load(imageByte).into(holder.produceImage);
                                    /**添加进缓存**/
                                    String imageString = new String(Base64.encodeToString(imageByte, Base64.DEFAULT));
                                    PreferenceManager.getInstance().preferenceManagerSave(String.valueOf(produce.getImage()), imageString);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}

