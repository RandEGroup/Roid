package com.randegroup.roid;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;

public class MainActivity extends AppCompatActivity{
	// 控件
	private Toolbar toolbar;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavView;
	private ActionBarDrawerToggle mDrawerToggle;
	// Id映射，通过String存取
	private Map<String,Integer> mIdMap;
	// 存入Fragment
	private List<Fragment> mFragmentList;
	// Menu
	private MenuHelper mMenuHelper;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupData();
		bindViews();
	}
	// 设置数据
	public void setupData(){
		mIdMap = new ArrayMap<String,Integer>();
		mFragmentList = new ArrayList<Fragment>();
		mMenuHelper = new MenuHelper();
	}
	// 绑定控件
	public void bindViews(){
		toolbar = (Toolbar)findViewById(R.id.main_toolbar);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawerlayout);
		mNavView = (NavigationView)findViewById(R.id.main_nav_view);
		setupToolbar();
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
	private class MenuHelper{
		// id
		int menu_id = 1;
		// Fragment
		FragmentManager manager;
		FragmentTransaction transaction;
		
		// 构造
		MenuHelper(){
			manager = getSupportFragmentManager();
			transaction = manager.beginTransaction();
		}
		// 添加Fragment
		void addFragment(File file){
			// 留空，什么时候能够使用编辑功能就加上
		}
		// 添加Item
		void addNavItem(String id,String name,Fragment fragment,boolean needToOpen){
			// 数据
			mIdMap.put(id,menu_id);
			mFragmentList.add(fragment);
			// Menu
			Menu menu = mNavView.getMenu();
			menu.add(0,menu_id,0,name).setIcon(R.drawable.ic_edit_black).setCheckable(true);
			// 启动Fragment
			if(needToOpen){
				transaction = manager.beginTransaction();
				transaction.replace(R.id.main_container,fragment);
				transaction.commit();
			}
			menu_id++;
		}
		// 获取Id
		int getMenuId(String id){
			return mIdMap.get(id);
		}
	}
}
