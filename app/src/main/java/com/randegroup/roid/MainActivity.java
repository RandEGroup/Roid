package com.randegroup.roid;

import java.io.File;
import java.util.Map;
import java.util.List;

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
import android.support.design.widget.FloatingActionButton;


public class MainActivity extends AppCompatActivity{
	// 控件
	private Toolbar toolbar;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavView;
	private ActionBarDrawerToggle mDrawerToggle;
	private FloatingActionButton mFab;
	// Id映射，通过String存取
	private Map<String,Integer> mIdMap;
	// 存入Fragment
	private Map<Integer,Fragment> mFragmentMap;
	// Menu
	private DrawerHelper mDrawerHelper;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupData();
		bindViews();
	}
	// 设置数据
	public void setupData(){
		mIdMap = new ArrayMap<String,Integer>();
		mFragmentMap = new ArrayMap<Integer,Fragment>();
		mDrawerHelper = new DrawerHelper();
	}
	// 绑定控件
	public void bindViews(){
		toolbar = (Toolbar)findViewById(R.id.main_toolbar);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawerlayout);
		mNavView = (NavigationView)findViewById(R.id.main_nav_view);
		setupToolbar();
		mFab = (FloatingActionButton)findViewById(R.id.main_fab);
		// 设置FAB的点击事件
		mFab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				
				mDrawerHelper.addFragment(null);
				// 留空
			}
		});
		mDrawerHelper.addHomepage();
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
				mDrawerHelper.showFragment(menuItem.getItemId());
				mDrawerLayout.closeDrawers();
				return true;
			}
		});
	}
	private class DrawerHelper{
		// id
		int menu_id = 1;
		int newfile_id = 1;
		// Fragment
		FragmentManager manager;
		FragmentTransaction transaction;
		
		// 构造
		DrawerHelper(){
			manager = getSupportFragmentManager();
			transaction = manager.beginTransaction();
		}
		// 添加Fragment
		void addFragment(File file){
			Fragment fragment = new EditorFragment();
			if(file != null){
				// 这里写读取内容
				addNavItem(file.getName(),file.getName(),fragment);
			} else {
				// 同上
				addNavItem("new" + new Integer(newfile_id).toString(),"new" + new Integer(newfile_id).toString(),fragment);
			}
			newfile_id ++;
			mDrawerLayout.openDrawer(mNavView);
		}
		// 添加Item
		void addNavItem(String id,String name,Fragment fragment){
			// 数据
			mIdMap.put(id,menu_id);
			mFragmentMap.put(menu_id,fragment);
			// Menu
			Menu menu = mNavView.getMenu();
			menu.add(0,menu_id,0,name).setIcon(R.drawable.ic_edit_black).setCheckable(true);
			menu_id++;
		}
		// 下面这个方法只能使用一次，在打开时添加主页
		void addHomepage(){
			// 数据
			mIdMap.put("homepage",0);
			mFragmentMap.put(0,new HomepageFragment());
			// Menu
			Menu menu = mNavView.getMenu();
			menu.add(0,0,0,getResources().getString(R.string.drawer_homepage)).setIcon(R.drawable.ic_home_black).setCheckable(true).setChecked(true);
			menu_id++;
			// 显示
			showFragment(0);
		}
		// 获取Id
		int getMenuId(String id){
			return mIdMap.get(id);
		}
		// 获取Fragment
		Fragment getFragment(Integer id){
			return mFragmentMap.get(id);
		}
		// 显示Fragment
		void showFragment(Fragment fragment){
			transaction = manager.beginTransaction();
			transaction.replace(R.id.main_container,fragment);
			transaction.commit();
		}
		void showFragment(int id){
			showFragment(getFragment(id));
		}
	}
}
