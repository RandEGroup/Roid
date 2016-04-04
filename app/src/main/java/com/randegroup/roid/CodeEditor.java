package com.randegroup.roid;

import android.widget.AutoCompleteTextView;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.*;
import android.widget.*;

public class CodeEditor extends EditText
{
	public CodeEditor(Context context, AttributeSet attrs)
	{
		super(context,attrs);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter)
	{
		// TODO: Implement this method
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		invalidate();
	}
	
	
	
	
	
}
