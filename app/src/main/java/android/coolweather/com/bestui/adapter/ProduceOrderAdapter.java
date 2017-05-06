package android.coolweather.com.bestui.adapter;

import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.JavaBean.Produces;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.util.DataBase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.coolweather.com.bestui.util.HttpUtil.urlImage;

/**
 * Created by Administrator on 2017/4/30.
 */

public class ProduceOrderAdapter extends RecyclerView.Adapter<ProduceOrderAdapter.ViewHolder> {

    private Context mContext;
    private List<Produces> mProducesList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView produceImage;
        TextView produceName;
        TextView producePrice;
        TextView produceQuantity;

        public ViewHolder(View view) {
            super(view);
            produceImage = (ImageView) view.findViewById(R.id.produce_image);
            produceName = (TextView) view.findViewById(R.id.produce_name);
            producePrice = (TextView) view.findViewById(R.id.produce_price);
            produceQuantity = (TextView) view.findViewById(R.id.produceQuantity_text);
        }
    }

    public ProduceOrderAdapter(List<Produces> mProducesList) {
       this.mProducesList = mProducesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.produce_order_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        /**
         * 图片点击事件
         */
        holder.produceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Produces produces = mProducesList.get(position);
                Produce produce = changetoProduce(produces);
                Intent intent=new Intent(mContext, ProduceItemActivity.class);
                intent.putExtra("produce",new Gson().toJson(produce));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Produces produces = mProducesList.get(position);
        Produce produce = DataBase.selectProduceByName(produces.getName());
        String tempUrl = urlImage + produce.getImage();
        Glide.with(mContext).load(tempUrl).into(holder.produceImage);
        holder.produceName.setText(produce.getName());
        holder.producePrice.setText("¥" + String.valueOf(produce.getPrice()));
        holder.produceQuantity.setText("× " + String.valueOf(produces.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mProducesList.size();
    }

    public Produce changetoProduce(Produces produces) {
        List<Produce> list = DataSupport.where("name = ?", produces.getName()).find(Produce.class);
        Produce produce = list.get(0);
        return produce;
    }
}
