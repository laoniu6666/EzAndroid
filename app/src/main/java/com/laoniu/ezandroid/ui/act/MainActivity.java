package com.laoniu.ezandroid.ui.act;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laoniu.ezandroid.BaseBindingActivity;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.databinding.ActMainBinding;
import com.laoniu.ezandroid.databinding.AppBarMainBinding;
import com.laoniu.ezandroid.ui.fmt.TestFmt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseBindingActivity<ActMainBinding> {

    List<Fragment> fragments;
    AppBarMainBinding main;

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initView() {
        main = binding.appbarmain;
        setTitle("主页");
        //viewpager
        setViewPager();
    }

    public void setViewPager() {
        fragments = new ArrayList<>();
        fragments.add(TestFmt.newInstance(getBundle(getString(R.string.bottomBar_1))));
        fragments.add(TestFmt.newInstance(getBundle(getString(R.string.bottomBar_2))));
        fragments.add(TestFmt.newInstance(getBundle(getString(R.string.bottomBar_3))));
        fragments.add(TestFmt.newInstance(getBundle(getString(R.string.bottomBar_4))));

        main.viewpager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });
        // ViewPager 滑动事件监听
        main.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                main.bnv.getMenu().getItem(position).setChecked(true);
            }
        });
        //BottomNavigationView 点击事件监听
        main.bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.tab_1:
                        main.viewpager.setCurrentItem(0);
                        break;
                    case R.id.tab_2:
                        main.viewpager.setCurrentItem(1);
                        break;
                    case R.id.tab_3:
                        main.viewpager.setCurrentItem(2);
                        break;
                    case R.id.tab_4:
                        main.viewpager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_main2, menu);
        return true;
    }

}