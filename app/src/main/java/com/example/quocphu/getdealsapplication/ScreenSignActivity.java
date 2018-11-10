package com.example.quocphu.getdealsapplication;

import android.drm.DrmStore;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScreenSignActivity extends AppCompatActivity {

    TabLayout tab_sign;
    TabItem tab_signUp,tab_signIn;
    ViewPager vPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_sign);
        findViewById(); //anh xa
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //Ẩn actionbar
        MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        vPager.setAdapter(fragmentAdapter);
        vPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_sign));
        tab_sign.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vPager.setCurrentItem(tab.getPosition()); //set Frament tại vị trí tab thứ ....
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    class MyFragmentAdapter extends FragmentStatePagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position){
                case 0: fragment = new FragmentSignUp();
                    break;
                case 1: fragment = new FragmentSignIn();
                    break;
                default: return null;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2; //Trả về số fragment tương ứng với số Tab
        }
    }
    public void findViewById(){
        tab_sign = findViewById(R.id.tab_sign);
        tab_signUp = findViewById(R.id.tab_signUp);
        tab_signIn = findViewById(R.id.tab_signIn);
        vPager = findViewById(R.id.viewPager);
    }
}
