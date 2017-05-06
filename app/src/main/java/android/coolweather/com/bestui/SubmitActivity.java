package android.coolweather.com.bestui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {

    private TextView sureText;
    private ImageButton sureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        sureText = (TextView) findViewById(R.id.sure_text);
        sureButton = (ImageButton) findViewById(R.id.sure_button);
        sureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHome();
            }
        });

        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHome();
            }
        });
    }

    public void gotoHome() {
        startActivity(new Intent(SubmitActivity.this, HomeActivity.class));
        finish();
    }
}
