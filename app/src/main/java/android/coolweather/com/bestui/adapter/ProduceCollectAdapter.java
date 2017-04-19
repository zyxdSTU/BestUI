package android.coolweather.com.bestui.adapter;

/**
 * Created by Administrator on 2017/4/15.
 */

import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class ProduceCollectAdapter extends RecyclerView.Adapter<ProduceCollectAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Produce> mProduceList;
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

    public ProduceCollectAdapter(ArrayList<Produce> produceArrayList) {
        mProduceList= produceArrayList;
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
                Produce produce = mProduceList.get(position);
                Intent intent=new Intent(mContext, ProduceItemActivity.class);
                intent.putExtra("produce",new Gson().toJson(produce));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Produce produce = mProduceList.get(position);
        holder.produceImage.setImageResource(produce.getImageId());
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
        return mProduceList.size();
    }


}
