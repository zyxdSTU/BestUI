package android.coolweather.com.bestui.adapter;

import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.ProduceCart;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.util.DataBase;
import android.coolweather.com.bestui.util.Quantity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * Created by Administrator on 2017/4/13.
 */

public class ProduceCartAdapter extends RecyclerView.Adapter<ProduceCartAdapter.ViewHolder> {

    private Context mContext;
    private Handler mhandler;
    final int UPDATE_TEXT = 1;
    private ArrayList<ProduceCart> mProduceCartList;
    public ArrayList<Integer> checkList;
    public HashMap<ProduceCart, Double> buyMap;
    public HashMap<ProduceCart, Double> checkMap;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView produceImage;
        TextView produceName;
        TextView producePrice;
        TextView quantityText;
        CheckBox checkBox;
        Button addButton;
        Button minusButton;

        public ViewHolder(View view) {
            super(view);
            produceImage = (ImageView) view.findViewById(R.id.produce_image);
            produceName = (TextView) view.findViewById(R.id.produce_name);
            producePrice = (TextView) view.findViewById(R.id.produce_price);
            quantityText = (TextView) view.findViewById(R.id.quantity_text);
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
            addButton = (Button) view.findViewById(R.id.add_button);
            minusButton = (Button) view.findViewById(R.id.minus_button);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }
    }

    public ProduceCartAdapter(ArrayList<ProduceCart> produceCartList, Handler handler) {
        mProduceCartList= produceCartList;
        checkList = new ArrayList<Integer>();
        buyMap = new HashMap<>();
        checkMap = new HashMap<>();
        initCheckMap();
        mhandler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.produce_cart_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        /**
         * 图片点击事件
         */
        holder.produceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ProduceCart produceCart = mProduceCartList.get(position);
                Produce produce = DataBase.selectProduceByName(produceCart.getName());
                Intent intent = new Intent(mContext, ProduceItemActivity.class);
                intent.putExtra("produce",new Gson().toJson(produce));
                mContext.startActivity(intent);
            }
        });

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quantity.addDispose(holder.quantityText);
                synchMap(holder);

            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quantity.minusDispose(holder.quantityText);
                synchMap(holder);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProduceCart produceCart = mProduceCartList.get(position);
        Produce produce = DataBase.selectProduceByName(produceCart.getName());
        String tempUrl = urlImage + produce.getImage();
        Glide.with(mContext).load(tempUrl).into(holder.produceImage);
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
                        /**
                         * 发送事件
                         */
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        mhandler.sendMessage(msg);
                        buyMap.put(mProduceCartList.get(position), Double.parseDouble(holder.quantityText.getText().toString()));
                        checkList.add((Integer)holder.checkBox.getTag());
                    }
                }else {
                    if(checkList.contains(holder.checkBox.getTag())){
                        /**
                         * 发送事件
                         */
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        mhandler.sendMessage(msg);
                        buyMap.remove(mProduceCartList.get(position));

                        checkList.remove(holder.checkBox.getTag());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProduceCartList.size();
    }

    public void initCheckMap() {
        for(int i = 0; i < mProduceCartList.size(); i++) {
            checkMap.put(mProduceCartList.get(i), 0.5);
        }
    }

    public void synchMap(ViewHolder holder) {
        checkMap.put(mProduceCartList.get(holder.getAdapterPosition()), Double.parseDouble(holder.quantityText.getText().toString()));

        if(buyMap.containsKey(mProduceCartList.get(holder.getAdapterPosition()))) {
            buyMap.put(mProduceCartList.get(holder.getAdapterPosition()), Double.parseDouble(holder.quantityText.getText().toString()));
            Message msg = new Message();
            msg.what = UPDATE_TEXT;
            mhandler.sendMessage(msg);
        }
    }
}
