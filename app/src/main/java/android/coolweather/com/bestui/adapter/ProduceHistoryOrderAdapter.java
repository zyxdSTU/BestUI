package android.coolweather.com.bestui.adapter;

import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Order;
import android.coolweather.com.bestui.JavaBean.OrderAddress;
import android.coolweather.com.bestui.JavaBean.OrderProduces;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.JavaBean.Produces;
import android.coolweather.com.bestui.ProduceItemActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.util.DataBase;
import android.coolweather.com.bestui.util.HttpUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.coolweather.com.bestui.util.HttpUtil.urlImage;

/**
 * Created by Administrator on 2017/5/1.
 */

public class ProduceHistoryOrderAdapter extends RecyclerView.Adapter<ProduceHistoryOrderAdapter.ViewHolder> {

    private Context mContext;
    private List<Order> mOrderList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout addLinearLayout;
        TextView identityText;
        TextView totalQuantityText;
        TextView totalMoneyText;
        Button cancelButton;

        public ViewHolder(View view) {
            super(view);
            identityText = (TextView) view.findViewById(R.id.identity_text);
            totalQuantityText = (TextView) view.findViewById(R.id.totalQuantity_text);
            totalMoneyText = (TextView) view.findViewById(R.id.totalMoney_text);
            addLinearLayout = (LinearLayout) view.findViewById(R.id.add_linearLayout);
            cancelButton = (Button) view.findViewById(R.id.cancel_button);
        }
    }

    public ProduceHistoryOrderAdapter(List<Order> mOrderList) {
        this.mOrderList = mOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_order_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Order order = mOrderList.get(position);
                DataSupport.deleteAll(OrderAddress.class, "identifier = ?", order.getIdentifier());
                DataSupport.deleteAll(OrderProduces.class, "identifier= ?", order.getIdentifier());
                mOrderList.remove(position);
                ProduceHistoryOrderAdapter.this.notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrderList.get(position);
        holder.identityText.setText("订单号： " +order.getIdentifier());
        holder.totalQuantityText.setText("共计" + order.getProducesList().size() + "件商品    合计：");
        holder.totalMoneyText.setText("¥" + order.getTotalMoney());

        /**删除所有的view**/
        holder.addLinearLayout.removeAllViews();

        List<Produces> producesList = order.getProducesList();

        for(Produces produces : producesList) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.produce_order_item, null);

            ImageView produceImage = (ImageView) view.findViewById(R.id.produce_image);
            TextView produceName = (TextView) view.findViewById(R.id.produce_name);
            TextView producePrice = (TextView) view.findViewById(R.id.produce_price);
            TextView produceQuantity = (TextView) view.findViewById(R.id.produceQuantity_text);
            /**点击事件要用**/
            produceImage.setTag(R.id.produces, produces);

            Produce produce = DataBase.selectProduceByName(produces.getName());
            if(produce == null) {
                produce = new Produce();
                produce.setName("已下架");
                produce.setImage(0);
                produce.setPrice(0.0);
                produce.setDescription("商品已下架");
            }
            String tempUrl = urlImage + produce.getImage();
            Glide.with(mContext).load(tempUrl).into(produceImage);
            produceName.setText(produce.getName());
            producePrice.setText("¥" + String.valueOf(produce.getPrice()));
            produceQuantity.setText("× " + String.valueOf(produces.getQuantity()));

            /**
             * 图片点击事件
             */
            produceImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Produces produces = (Produces) view.getTag(R.id.produces);
                    Produce produce = changetoProduce(produces);
                    if(produce != null) {
                        Intent intent = new Intent(mContext, ProduceItemActivity.class);
                        intent.putExtra("produce", new Gson().toJson(produce));
                        mContext.startActivity(intent);
                    }
                }
            });
            View line = LayoutInflater.from(mContext).inflate(R.layout.line, null);
            holder.addLinearLayout.addView(view);
            holder.addLinearLayout.addView(line);
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }


    public Produce changetoProduce(Produces produces) {
        return DataBase.selectProduceByName(produces.getName());
    }
}
