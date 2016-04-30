package com.randegroup.roid;

import java.io.File;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditorFragment extends Fragment
{
	private View mView;
	private CodeEditor mCodeEditor;
	private File file;

	public EditorFragment(File file){
		this.file = file;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		MainActivity.changeIcon(MainActivity.FAB_SAVE);
		mView = inflater.inflate(R.layout.code_editor, null);
		mCodeEditor = (CodeEditor)mView.findViewById(R.id.codeeditorEditText1);
		
		return mView;
	}
	public static void save(File file){
		// 保存
		if(file != null){
			// 如果不是新建文件
		} else {
			// 如果是新建文件
		}
	}
}
