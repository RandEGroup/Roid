package com.randegroup.roid;

import java.util.Map;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.graphics.Bitmap;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;

public class MainActivity extends AppCompatActivity{
	Toolbar toolbar;
	DrawerLayout mDrawerLayout;
	NavigationView mNavView;
	ActionBarDrawerToggle mDrawerToggle;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bindViews();
		setupToolbar();
	}
	// 绑定控件
	public void bindViews(){
		toolbar = (Toolbar)findViewById(R.id.main_toolbar);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawerlayout);
		mNavView = (NavigationView)findViewById(R.id.main_nav_view);
	}
	// 设置Toolbar和抽屉
	public void setupToolbar(){
		toolbar.setTitle(getTitle());
		setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
				// 打开DrawerLayout
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
				// 关闭DrawerLayout
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
		mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				// 点击事件
				menuItem.setChecked(true);
				mDrawerLayout.closeDrawers();
				return true;
			}
		});
	}
	// 添加Item
	public void addNavItem(Integer id,String name){
		// 先留空，以后再弄
	}
}
