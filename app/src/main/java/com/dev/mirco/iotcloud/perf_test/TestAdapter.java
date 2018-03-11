package com.dev.mirco.iotcloud.perf_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.mirco.iotcloud.R;

import java.util.ArrayList;

/**
 * Created by mirco on 14/06/16.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
	private Context context;

	private ArrayList<long[]> mDataset;

	// Provide a suitable constructor (depends on the kind of dataset)
	public TestAdapter(Context context, ArrayList<long[]> initialArray) {
		this.context=context;
		mDataset = initialArray;
	}

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public TextView number, latency;
		public View view;
		public ViewHolder(View v) {
			super(v);
			view = v;
			number = (TextView)v.findViewById(R.id.number);
			latency = (TextView)v.findViewById((R.id.latency));
		}
	}


	// Create new views (invoked by the layout manager)
	@Override
	public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.get_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		holder.number.setText(String.valueOf(mDataset.get(position)[0]));
		holder.latency.setText(String.valueOf(mDataset.get(position)[1]));
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	public void addValue(long latency) {
		long[] array = new long[2];
		array[0] = (long)mDataset.size();
		array[1] = latency;
		mDataset.add(array);
		notifyItemInserted(mDataset.size());
	}

}
