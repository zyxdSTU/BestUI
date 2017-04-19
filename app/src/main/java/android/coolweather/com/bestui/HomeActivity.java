package android.coolweather.com.bestui;

import android.coolweather.com.bestui.adapter.FragmentAdapter;
import android.coolweather.com.bestui.fragment.BaseFragment;
import android.coolweather.com.bestui.fragment.CartFragment;
import android.coolweather.com.bestui.fragment.HomeFragment;
import android.coolweather.com.bestui.fragment.UserFragment;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton homeButton;
    private ImageButton cartButton;
    private ImageButton userButton;
    private ViewPager viewPager;

    private TextView homeTextView;
    private TextView cartTextView;
    private TextView userTextView;

    //碎片数组
    private ArrayList<BaseFragment> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeButton = (ImageButton)findViewById(R.id.home_button);
        cartButton = (ImageButton)findViewById(R.id.cart_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        homeTextView = (TextView) findViewById(R.id.home_text_view);
        cartTextView = (TextView) findViewById(R.id.cart_text_view);
        userTextView = (TextView) findViewById(R.id.user_text_view);

        //碎片加入数组
        mList = new ArrayList<BaseFragment>();
        mList.add(new HomeFragment());
        mList.add(new CartFragment());
        mList.add(new UserFragment());

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(fragmentAdapter);

        homeButton.setOnClickListener(this);
        cartButton.setOnClickListener(this);
        userButton.setOnClickListener(this);

        homeButton.setSelected(true);
        homeTextView.setTextColor(Color.parseColor("#4CAF50"));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.home_button:
                changeTab(0);
                break;
            case R.id.cart_button:
                changeTab(1);
                break;
            case R.id.user_button:
                changeTab(2);
                break;
            default:
                break;
        }
    }

    private void changeTab(int position) {
        homeButton.setSelected(false);
        homeTextView.setTextColor(Color.parseColor("#757575"));
        cartButton.setSelected(false);
        cartTextView.setTextColor(Color.parseColor("#757575"));
        userButton.setSelected(false);
        userTextView.setTextColor(Color.parseColor("#757575"));

        switch(position) {
            case 0:
                homeButton.setSelected(true);
                homeTextView.setTextColor(Color.parseColor("#4CAF50"));
                break;
            case 1:
                cartButton.setSelected(true);
                cartTextView.setTextColor(Color.parseColor("#4CAF50"));
                break;
            case 2:
                userButton.setSelected(true);
                userTextView.setTextColor(Color.parseColor("#4CAF50"));
                break;
            default:
                break;
        }
        viewPager.setCurrentItem(position);
    }
}
