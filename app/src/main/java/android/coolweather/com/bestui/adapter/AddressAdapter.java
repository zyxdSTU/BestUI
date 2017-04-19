package android.coolweather.com.bestui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.AddressActivity;
import android.coolweather.com.bestui.EditActivity;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.R;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    private ArrayList<Address> mList;
    private Context mContext;
    private Handler handler;
    private final int REFRESH = 1;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView phoneNumText;
        TextView addressText;
        CheckBox defaultCheckbox;
        TextView editText;
        TextView deleteText;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.name_text);
            phoneNumText = (TextView) view.findViewById(R.id.phoneNum_text);
            addressText = (TextView) view.findViewById(R.id.address_text);
            defaultCheckbox = (CheckBox) view.findViewById(R.id.default_checkbox);
            editText = (TextView) view.findViewById(R.id.edit_text);
            deleteText = (TextView) view.findViewById(R.id.delete_text);
        }
    }

    public AddressAdapter(ArrayList<Address> mList, Handler handler) {
        this.mList = mList;
        this.handler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Address address = mList.get(position);

        holder.nameText.setText(address.getName());
        holder.phoneNumText.setText(address.getPhoneNum());
        holder.addressText.setText(address.getAddress());
        holder.defaultCheckbox.setChecked(address.getStatus());

        holder.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /**有bug**/
               DataSupport.deleteAll(Address.class, "name = ? and phoneNum = ? and address = ?",
                       mList.get(position).getName(), mList.get(position).getPhoneNum(), mList.get(position).getAddress()
               );

                //通知主界面刷新
                Message msg = new Message();
                msg.what = REFRESH;
                handler.sendMessage(msg);
            }
        });

        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("edit_address", new Gson().toJson(mList.get(position)));

                //返回栈中去掉
                if (mContext instanceof AddressActivity) {
                    AddressActivity activity = (AddressActivity)mContext;
                    activity.finish();
                }
                mContext.startActivity(intent);
            }
        });

        holder.defaultCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            /**
             * 只有一个默认状态
             */
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b) {
                   ContentValues contentValues = new ContentValues();
                   contentValues.put("status", "0");
                   DataSupport.updateAll(Address.class, contentValues, "status = ?", "1");
               }
               mList.get(position).setStatus(b);
               mList.get(position).save();

               //通知主界面刷新
               Message msg = new Message();
               msg.what = REFRESH;
               handler.sendMessage(msg);
           }
      });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

