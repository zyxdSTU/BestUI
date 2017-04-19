package android.coolweather.com.bestui.fragment;

import android.content.Intent;
import android.coolweather.com.bestui.AddressActivity;
import android.coolweather.com.bestui.CollectActivity;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.R;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/9.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener{
    private TextView infoText;
    private TextView orderText;
    private TextView collectText;
    private TextView addressText;
    private TextView systemText;
    private TextView helpText;
    private Button exitButton;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.layout_user, null);

        infoText = (TextView) view.findViewById(R.id.info_text);
        orderText = (TextView) view.findViewById(R.id.order_text);
        collectText = (TextView) view.findViewById(R.id.collect_text);
        addressText = (TextView) view.findViewById(R.id.address_text);
        systemText = (TextView) view.findViewById(R.id.system_text);
        helpText = (TextView) view.findViewById(R.id.help_text);
        exitButton = (Button) view.findViewById(R.id.exit_button);

        infoText.setOnClickListener(this);
        orderText.setOnClickListener(this);
        collectText.setOnClickListener(this);
        addressText.setOnClickListener(this);
        systemText.setOnClickListener(this);
        helpText.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        return view;
    }

    @Override
    public View initData() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.info_text:
                Toast.makeText(mContext, "info_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_text:
                Toast.makeText(mContext, "order_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.collect_text:
                gotoCollect();
                break;
            case R.id.address_text:
                gotoAddressActivity();
                break;
            case R.id.system_text:
                Toast.makeText(mContext, "system_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help_text:
                Toast.makeText(mContext, "help_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_button:
                Toast.makeText(mContext, "exit_button", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void gotoCollect() {
        Intent intent = new Intent(mContext, CollectActivity.class);
        startActivity(intent);
    }

    public void gotoAddressActivity() {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        startActivity(intent);
    }
}
