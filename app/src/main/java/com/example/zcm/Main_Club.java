package com.example.zcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.zcm.adapter.ClubPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class Main_Club extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ClubPageAdapter clubPageAdapter;
    Toolbar topToolBar;
    String Path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_club);
        Path = getIntent().getExtras().getString("number");
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        topToolBar=findViewById(R.id.topToolBar);
        setSupportActionBar(topToolBar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager=findViewById(R.id.viewPager);
        addTabs();

        clubPageAdapter= new ClubPageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(clubPageAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void addTabs() {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_add_fill));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_user));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_info));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2){
                    if(tab.getPosition()==0){
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add_fill);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_user);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_info);
                    }
                    else if(tab.getPosition()==1){
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_user_fill);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_info);
                    }
                    else if(tab.getPosition()==2){
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_user);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_info_fill);
                    }

                    clubPageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent1 = new Intent(Main_Club.this, EditProfile.class);
                intent1.putExtra("number",Path);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent intent2 = new Intent(Main_Club.this, Main_login.class);
                startActivity(intent2);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}