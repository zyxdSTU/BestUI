package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEditText;
    private EditText phoneNumEditText;
    private EditText addressEditText;
    private CheckBox defaultCheckBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = (EditText) findViewById(R.id.name_editText);
        phoneNumEditText = (EditText) findViewById(R.id.phoneNum_EditText);
        addressEditText = (EditText) findViewById(R.id.address_editText);
        defaultCheckBox = (CheckBox) findViewById(R.id.default_checkbox);
        saveButton = (Button) findViewById(R.id.save_button);

        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id) {
            case R.id.save_button:
                gotoAddressActivity();
                break;
            default:
                break;
        }
    }


    public void gotoAddressActivity() {
        Intent intent = new Intent(AddActivity.this, AddressActivity.class);
        String name = nameEditText.getText().toString().trim();
        String phoneNum = phoneNumEditText.getText().toString().trim();
        String addressText = addressEditText.getText().toString().trim();
        Address address = new Address(name, phoneNum, addressText, defaultCheckBox.isChecked());

        if(name == null)  { nameEditText.setError("收货人不能为空"); return;}
        if(phoneNum == null) { phoneNumEditText.setError("电话号码不能为空"); return; }
        if(address == null) { addressEditText.setError("地址不能为空"); return; }

        intent.putExtra("add_address", new Gson().toJson(address));
        finish();
        startActivity(intent);
    }
}

