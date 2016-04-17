package com.randegroup.roid;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;

public class HomepageFragment extends Fragment
{
	RecyclerView recycler;
	RecyclerAdapter adapter;
	FileHelper mFileHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View homepageView = inflater.inflate(R.layout.homepage,null);
		setupData();
		recycler = (RecyclerView)homepageView.findViewById(R.id.homepage_recycler);
		recycler.setAdapter(adapter);
		recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		recycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
		recycler.setItemAnimator(new DefaultItemAnimator());
		return homepageView;
	}
	
	public void setupData(){
		mFileHelper = new FileHelper();
		adapter = new RecyclerAdapter(getActivity(),mFileHelper.getDataset());
	}
	
	class FileHelper{
		// 在这个列表里列出所有的文件，map有三个值，type表示文件类型，在Adapter里面，file就是文件，name是文件名
		List<Map<String,Object>> mFileDataset;
		public FileHelper(){
			mFileDataset = new ArrayList<Map<String,Object>>();
			// 测试
			for(int i = 0;i < 20;i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("type",RecyclerAdapter.TYPE_FILE);
				map.put("file",null);
				map.put("name","test " + i);
				mFileDataset.add(map);
			}
		}
		List<Map<String,Object>> getDataset(){
			return mFileDataset;
		}
		void put(String type,File file,String name){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type",type);
			map.put("file",file);
			map.put("name",name);
			mFileDataset.add(map);
			adapter.notifyDataSetChanged();
		}
	}
}
