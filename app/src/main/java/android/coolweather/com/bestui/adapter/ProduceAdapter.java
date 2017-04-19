package android.coolweather.com.bestui.adapter;

import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.coolweather.com.bestui.JavaBean.Produce;

import com.google.gson.Gson;

import java.util.ArrayList;

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
        holder.produceImage.setImageResource(produce.getImageId());
        holder.produceName.setText(produce.getName());
        holder.producePrice.setText("¥" + String.valueOf(produce.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mProduceList.size();
    }
}
