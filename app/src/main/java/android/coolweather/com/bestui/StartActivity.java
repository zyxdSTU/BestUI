package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.util.PreferenceManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    private final int DELAY_TIME = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toActivity();
            }
        }, DELAY_TIME);
    }

    public void toActivity() {
        finish();
        if(PreferenceManager.getInstance().preferenceManagerGet("currentUser").equals("")) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        } else {
            Intent intent = new Intent(StartActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
