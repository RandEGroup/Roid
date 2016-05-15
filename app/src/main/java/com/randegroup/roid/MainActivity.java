package com.randegroup.roid;

import java.io.File;
import java.util.Map;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import me.yugy.github.reveallayout.RevealLayout;


public class MainActivity extends AppCompatActivity{
	// 控件
	private Toolbar toolbar;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavView;
	private ActionBarDrawerToggle mDrawerToggle;
	private static FloatingActionButton mFab;
	private FloatingActionButton mCheckImage;
	// Id映射，通过String存取
	private Map<String,Integer> mIdMap;
	// 存入Fragment
	private Map<Integer,Fragment> mFragmentMap;
	// Menu
	private DrawerHelper mDrawerHelper;
	// FAB图标
	final static int FAB_ADD = R.drawable.ic_add_white;
	final static int FAB_SAVE = R.drawable.ic_save_white;
	static int FAB_IC_NOW;
	// 动画支持
	boolean nowFab = true;
	// 添加Fragment动画
	View mFragmentAddView;
	RevealLayout mReveal;
	// 最后添加
	int latest_add = 1;
	
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
		mFab.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View view, MotionEvent event){
				// TODO: Implement this method
				switch(event.getAction()){
					case MotionEvent.ACTION_UP:
						switch(FAB_IC_NOW){
							case FAB_ADD:
								animWhenAdd((int)event.getX(),(int)event.getY());
								mDrawerHelper.addFragment(null);
								break;
							case FAB_SAVE:
								// 保存，参数是文件
								EditorFragment.save(null);
								// 延时留出时间保存
								Handler handler = new Handler();
								Runnable runnable = new Runnable(){
									@Override
									public void run(){
										animWhenSave();
									}
								};
								handler.postDelayed(runnable,1500);
								break;
						}
						break;
				}
				return true;
			}
		});
		mCheckImage = (FloatingActionButton)findViewById(R.id.main_check_image);
		mFragmentAddView = (View)findViewById(R.id.main_add_anim);
		mReveal = (RevealLayout)findViewById(R.id.reveal_layout);
		mReveal.hide();
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
	// 改变图标
	public static void changeIcon(int resId){
		mFab.setImageResource(resId);
		FAB_IC_NOW = resId;
	}
	// 动画
	public void animWhenSave(){
		final Animation checkInAnim = AnimationUtils.loadAnimation(this,R.anim.image_in);
		final Animation checkOutAnim = AnimationUtils.loadAnimation(this,R.anim.image_out);
		checkInAnim.setAnimationListener(new Animation.AnimationListener(){
			@Override
			public void onAnimationStart(Animation p1)
			{
				// TODO: Implement this method
				Animation outAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.image_out);
				mFab.startAnimation(outAnim);
				mFab.setVisibility(View.GONE);
				Handler handler = new Handler();
				Runnable runnable = new Runnable(){
					@Override
					public void run(){
						mCheckImage.startAnimation(checkOutAnim);
						mCheckImage.setVisibility(View.GONE);
						Animation inAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.image_in);
						mFab.setVisibility(View.VISIBLE);
						mFab.startAnimation(inAnim);
					}
				};
				handler.postDelayed(runnable,3000);
			}

			@Override
			public void onAnimationEnd(Animation p1)
			{
				// TODO: Implement this method
			}

			@Override
			public void onAnimationRepeat(Animation p1)
			{
				// TODO: Implement this method
			}
		});
		mCheckImage.setVisibility(View.VISIBLE);
		mCheckImage.startAnimation(checkInAnim);
	}
	public void animWhenAdd(final int x,final int y){
		mReveal.show(x,y,300);
		Handler handler = new Handler();
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				mDrawerHelper.showFragment(mIdMap.get("new" + new Integer(latest_add).toString()));
				mReveal.hide(x,y,300);
			}
		};
		handler.postDelayed(runnable,1000);
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
			Fragment fragment = new EditorFragment(file);
			if(file != null){
				// 这里写读取内容
				addNavItem(file.getName(),file.getName(),fragment);
			} else {
				// 同上
				addNavItem("new" + new Integer(newfile_id).toString(),"new" + new Integer(newfile_id).toString(),fragment);
				latest_add = newfile_id;
			}
			newfile_id ++;
			//mDrawerLayout.openDrawer(mNavView);
		}
		// 添加Item
		void addNavItem(String id,String name,Fragment fragment){
			// 数据
			mIdMap.put(id,menu_id);
			mFragmentMap.put(menu_id,fragment);
			// Menu
			Menu menu = mNavView.getMenu();
			menu.add(0,menu_id,0,name).setIcon(R.drawable.ic_edit_black).setCheckable(true).setChecked(true);
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
