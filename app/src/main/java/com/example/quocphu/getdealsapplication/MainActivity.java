package com.example.quocphu.getdealsapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.quocphu.getdealsapplication.model.Store;
import com.example.quocphu.getdealsapplication.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    android.support.v7.widget.Toolbar toolbar;
    NavigationView navigation;
    TabLayout tab_main;
    TabItem tab_map,tab_store,tab_deal;
    ViewPager vPager;
    FrameLayout flContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigation = findViewById(R.id.nvView);
        tab_main = findViewById(R.id.tab_main);
        tab_map = findViewById(R.id.map);
        tab_store = findViewById(R.id.store);
        tab_deal = findViewById(R.id.deal);
        vPager = findViewById(R.id.viewPager);
        flContent = findViewById(R.id.flContent);

        flContent.setVisibility(View.GONE);
        MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        vPager.setAdapter(fragmentAdapter);
        vPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_main));
        tab_main.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        tab_main.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int positon = tab.getPosition();
                android.support.v4.app.Fragment fragment = null;
                Class classFragment = null;
                if(positon==0){
                    classFragment = MapFragment.class;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                xulymenu(item);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    public void xulymenu(MenuItem item){
        android.support.v4.app.Fragment fragment = null;
        Class classFragment = null;
        if(item.getItemId()==R.id.myprofile){
            classFragment = MapFragment.class;
            flContent.setVisibility(View.VISIBLE);
        }
        try {
            fragment  = (android.support.v4.app.Fragment) classFragment.newInstance();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().
                    replace(R.id.flContent,fragment).
                    commit();
        }catch (Exception e){

        }
        item.setChecked(true);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
    }
    //Class Adapter Viewpager
    class MyFragmentAdapter extends FragmentStatePagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position){
                case 0: fragment = new MapFragment();
                    break;
                case 1: fragment = new MapFragment();
                    break;
                case 2: fragment = new MapFragment();
                    break;
                default: return null;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3; //Trả về số fragment tương ứng với số Tab
        }
    }
}
