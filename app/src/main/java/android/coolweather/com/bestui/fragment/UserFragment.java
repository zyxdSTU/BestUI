package android.coolweather.com.bestui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.coolweather.com.bestui.AddressActivity;
import android.coolweather.com.bestui.CollectActivity;
import android.coolweather.com.bestui.HistoryOrderActivity;
import android.coolweather.com.bestui.JavaBean.Address;
import android.coolweather.com.bestui.LoginActivity;
import android.coolweather.com.bestui.R;
import android.coolweather.com.bestui.util.PreferenceManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
/**
 * Created by Administrator on 2017/4/9.
 */

public class UserFragment extends Fragment implements View.OnClickListener{
    private TextView infoText;
    private TextView orderText;
    private TextView collectText;
    private TextView addressText;
    private TextView systemText;
    private TextView helpText;
    private Button exitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user, container, false);
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
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.info_text:
                Toast.makeText(getContext(), "info_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_text:
                gotoOrderActivity();
                break;
            case R.id.collect_text:
                gotoCollect();
                break;
            case R.id.address_text:
                gotoAddressActivity();
                break;
            case R.id.system_text:
                Toast.makeText(getContext(), "system_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help_text:
                Toast.makeText(getContext(), "help_text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_button:
                exit();
                break;
            default:
                break;
        }
    }

    public void gotoCollect() {
        Intent intent = new Intent(getContext(), CollectActivity.class);
        startActivity(intent);
    }

    public void gotoAddressActivity() {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        startActivity(intent);
    }

    public void gotoOrderActivity() {
        Intent intent = new Intent(getContext(), HistoryOrderActivity.class);
        startActivity(intent);
    }

    public void exit() {
        PreferenceManager.getInstance().preferenceManagerRemove("currentUser");
        getActivity().finish();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }
}
