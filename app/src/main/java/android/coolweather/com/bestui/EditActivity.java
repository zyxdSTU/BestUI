package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEditText;
    private EditText phoneNumEditText;
    private EditText addressEditText;
    private CheckBox defaultCheckBox;
    private Button saveButton;
    private int position;
    private Address address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        nameEditText = (EditText) findViewById(R.id.name_editText);
        phoneNumEditText = (EditText) findViewById(R.id.phoneNum_EditText);
        addressEditText = (EditText) findViewById(R.id.address_editText);
        defaultCheckBox = (CheckBox) findViewById(R.id.default_checkbox);
        saveButton = (Button) findViewById(R.id.save_button);

        position = getIntent().getIntExtra("position", -1);
        String addressJson = getIntent().getStringExtra("edit_address");
        if(addressJson != null) address = new Gson().fromJson(addressJson, Address.class);

        if(address != null) {
            nameEditText.setText(address.getName());
            phoneNumEditText.setText(address.getPhoneNum());
            addressEditText.setText(address.getAddress());
            defaultCheckBox.setChecked(address.getStatus());
        }
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.save_button:
                gotoAddressActivity();
        }
    }

    public void gotoAddressActivity() {
        Intent intent = new Intent(EditActivity.this, AddressActivity.class);
        String name = nameEditText.getText().toString().trim();
        String phoneNum = phoneNumEditText.getText().toString().trim();
        String addressText = addressEditText.getText().toString().trim();
        Address address = new Address(name, phoneNum, addressText, defaultCheckBox.isChecked());

        if(name == null)  { nameEditText.setError("收货人不能为空"); return;}
        if(phoneNum == null) { phoneNumEditText.setError("电话号码不能为空"); return; }
        if(address == null) { addressEditText.setError("地址不能为空"); return; }

        intent.putExtra("edit_address", new Gson().toJson(address));
        intent.putExtra("position", position);
        finish();
        startActivity(intent);
    }
}
