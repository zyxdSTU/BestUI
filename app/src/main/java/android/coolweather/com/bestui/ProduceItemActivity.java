package android.coolweather.com.bestui;

import android.content.Intent;
import android.coolweather.com.bestui.JavaBean.Produce;
import android.coolweather.com.bestui.util.Quantity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ProduceItemActivity extends AppCompatActivity implements View.OnClickListener{
    private Produce produce;
    private ImageView produceImage;
    private TextView produceName;
    private TextView producePrice;
    private Button addButton;
    private TextView quantityText;
    private Button minusButton;
    private TextView produceText;

    private Button homeButton;
    private Button collectButton;
    private Button cartButton;
    private Button buyNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 状态栏与图片一体化
         */
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_produce_item);

        String produceJson=getIntent().getStringExtra("produce");
        produce = new Gson().fromJson(produceJson, Produce.class);

        produceImage = (ImageView) findViewById(R.id.produce_image);
        produceName = (TextView) findViewById(R.id.produce_name);
        producePrice = (TextView) findViewById(R.id.produce_price);
        produceText = (TextView) findViewById(R.id.produce_text);

        addButton = (Button) findViewById(R.id.add_button);
        quantityText = (TextView) findViewById(R.id.quantity_text);
        minusButton = (Button) findViewById(R.id.minus_button);

        homeButton = (Button) findViewById(R.id.home_button);
        collectButton = (Button) findViewById(R.id.collect_button);
        cartButton = (Button) findViewById(R.id.cart_button);
        buyNowButton = (Button) findViewById(R.id.buy_button);

        addButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        collectButton.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        buyNowButton.setOnClickListener(this);

        initData();
    }

    public void initData() {
        produceImage.setImageResource(produce.getImageId());
        produceName.setText(produce.getName());
        producePrice.setText("¥" + String.valueOf(produce.getPrice()));
        produceText.setText(produce.getText());
        quantityText.setText("0.5");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.add_button:
                Quantity.addDispose(quantityText);
                break;
            case R.id.minus_button:
                Quantity.minusDispose(quantityText);
                break;
            case R.id.home_button:
                gotoHome();
                break;
            default:
                break;
        }
    }

    public void gotoHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
