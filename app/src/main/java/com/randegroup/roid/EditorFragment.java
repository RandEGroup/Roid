package com.randegroup.roid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditorFragment extends Fragment
{
	private View mView;
	private CodeEditor mCodeEditor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		mView = inflater.inflate(R.layout.code_editor, null);
		mCodeEditor = (CodeEditor)mView.findViewById(R.id.codeeditorEditText1);
		
		return mView;
	}
	
}
