package android.coolweather.com.bestui.adapter;

import android.coolweather.com.bestui.fragment.BaseFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    //碎片数据
    private ArrayList<BaseFragment> mList;

    public FragmentAdapter(FragmentManager fragmentManager,ArrayList<BaseFragment>mList) {
        super(fragmentManager);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
