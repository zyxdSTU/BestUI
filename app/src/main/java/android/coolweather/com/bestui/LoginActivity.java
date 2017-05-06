package android.coolweather.com.bestui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.coolweather.com.bestui.util.HttpUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

import static android.coolweather.com.bestui.util.HttpUtil.urlVerify;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumText;
    private EditText passwordText;
    private Button loginButton;
    private Button registerButton;

    private String phoneNum;
    private String password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            showProgressDialog();
            verify();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * 跳转到注册页面
             */
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void verify() {
        phoneNum = phoneNumText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        String url = urlVerify + "?name=" + phoneNum +"&password=" + password;
        HttpUtil.verifyOKHttp(url, new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "系统未响应", Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                closeProgressDialog();
                final String result = response.body().string();
                Log.d("MainActivity", result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("true"))
                        {
                            finish();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void showProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在验证...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    public void closeProgressDialog() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
