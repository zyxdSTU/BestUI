package android.coolweather.com.bestui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.util.HttpUtil;
import android.coolweather.com.bestui.util.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.coolweather.com.bestui.JavaBean.Produce;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.coolweather.com.bestui.util.HttpUtil.urlDownload;
import static android.coolweather.com.bestui.util.HttpUtil.urlImage;

/**
 * Created by Administrator on 2017/4/13.
 */

public class ProduceAdapter extends RecyclerView.Adapter<ProduceAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Produce> mProduceList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView produceImage;
        TextView produceName;
        TextView producePrice;

        public ViewHolder(View view) {
            super(view);
            produceImage = (ImageView) view.findViewById(R.id.produce_image);
            produceName = (TextView) view.findViewById(R.id.produce_name);
            producePrice = (TextView) view.findViewById(R.id.produce_price);
        }
    }

    public ProduceAdapter(ArrayList<Produce> produceArrayList) {
        mProduceList= produceArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.produce_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        /**
         * 图片点击事件
         */
        holder.produceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Produce produce = mProduceList.get(position);
                Intent intent=new Intent(mContext, ProduceItemActivity.class);
                intent.putExtra("produce",new Gson().toJson(produce));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Produce produce = mProduceList.get(position);
        String tempUrl = urlImage + produce.getImage();
        loadImage(holder);
        holder.produceName.setText(produce.getName());
        holder.producePrice.setText("¥" + String.valueOf(produce.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mProduceList.size();
    }


    public void loadImage(final ViewHolder holder) {
        int position = holder.getAdapterPosition();
        final Produce produce = mProduceList.get(position);

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
