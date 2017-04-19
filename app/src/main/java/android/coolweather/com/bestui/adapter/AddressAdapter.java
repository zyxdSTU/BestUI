package android.coolweather.com.bestui.adapter;

import android.content.Context;
import android.content.Intent;
import android.coolweather.com.bestui.AddressActivity;
import android.coolweather.com.bestui.EditActivity;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.R;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    private ArrayList<Address> mList;
    Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView phoneNumText;
        TextView addressText;
        CheckBox defaultCheckbox;
        ImageButton editButton;
        ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.name_text);
            phoneNumText = (TextView) view.findViewById(R.id.phoneNum_text);
            addressText = (TextView) view.findViewById(R.id.address_text);
            defaultCheckbox = (CheckBox) view.findViewById(R.id.default_checkbox);
            editButton = (ImageButton) view.findViewById(R.id.edit_button);
            deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
        }
    }

    public AddressAdapter(ArrayList<Address> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Address address = mList.get(position);

        holder.nameText.setText(address.getName());
        holder.phoneNumText.setText(address.getPhoneNum());
        holder.addressText.setText(address.getAddress());
        if(address.getStatus()) holder.defaultCheckbox.setChecked(true);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("edit_address", new Gson().toJson(mList.get(position)));
                mContext.startActivity(intent);
            }
        });

        holder.defaultCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mList.get(position).setStatus(b);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

