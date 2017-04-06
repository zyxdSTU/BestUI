package android.coolweather.com.bestui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumText;
    private EditText passwordText;
    private Button loginButton;
    private Button registerButton;

    private String phoneNum;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumText = (EditText) findViewById(R.id.phone_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * 跳转到首页
             */
            public void onClick(View view) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * 跳转到注册页面
             */
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
