package com.randegroup.roid;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;
import android.support.v7.widget.RecyclerView;

public class RecyclerAdapter extends SwipeAdapter{
	ArrayList<HashMap<String,Object>> mDataset;
	Context context;
	// TYPE
	static final int TYPE_FILE = 1;
	static final int TYPE_FOLDER = 2;
	static final int TYPE_IMAGE = 3;
	
	public RecyclerAdapter(Context context,List<Map<String,Object>> list){
		this.context = context;
		this.mDataset = (ArrayList<HashMap<String, Object>>)list;
	}

	@Override
	public RecyclerAdapter.RecyclerHolder onCreateSwipeViewHolder(ViewGroup p1, int type)
	{
		// TODO: Implement this method
		return new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.homepage_item,p1,true));
	}

	@Override
	public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		// TODO: Implement this method
		RecyclerHolder mHolder = (RecyclerHolder)holder;
		int image = 0;
		switch(getItemViewType(position)){
			case TYPE_FILE:
				image = R.drawable.ic_edit_white;
				break;
			case TYPE_FOLDER:
				image = R.drawable.ic_folder_white;
				break;
			case TYPE_IMAGE:
				image = R.drawable.ic_image_white;
				break;
		}
		mHolder.image.setImageResource(image);
		mHolder.name.setText(mDataset.get(position).get("name").toString());
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return mDataset.size();
	}
	
	@Override
	public int getItemViewType(int Position){
		return mDataset.get(Position).get("type");
	}
	
	@Override
	public SwipeConfiguration onCreateSwipeConfiguration(Context context,int position){
		return new SwipeConfiguration.Builder(context)
				.setDrawableResource(R.drawable.ic_delete_black)
				.setLeftUndoable(true)
				.setLeftUndoDescription(R.string.item_deleted)
				.setLeftBackgroundColor(0xFFE0E0E0)
				.setRightUndoable(true)
				.setRightUndoDescription(R.string.item_deleted)
				.setRightBackgroundColor(0xFFE0E0E0)
				.setDescriptionTextColorResource(android.R.color.white)
				.setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.RESTRICTED_SWIPE)
				.setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.RESTRICTED_SWIPE)
				.build();
	}

	@Override
	public void onSwipe(int position,int direction){
		mDataset.remove(position);
		notifyItemRemoved(position);
	}

	class RecyclerHolder extends RecyclerView.ViewHolder{
		ImageView image;
		TextView name;
		public RecyclerHolder(View view){
			super(view);
			image = (ImageView)view.findViewById(R.id.homepage_item_type);
			name = (TextView)view.findViewById(R.id.homepage_item_name);
		}
	}
}
