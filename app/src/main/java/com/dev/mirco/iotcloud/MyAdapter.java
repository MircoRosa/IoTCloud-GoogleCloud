package com.dev.mirco.iotcloud;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mirco on 14/06/16.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private ArrayList<AppObject> mDataset;
	private Context context;

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyAdapter(ArrayList<AppObject> myDataset, Context context) {
		mDataset = myDataset;
		this.context=context;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
		switch (viewType) {
			case 0: //Server
				return new ServerVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_server, parent, false),context);
			case 1: //Resource not observable
				return new NotObsResourceVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_resource_not_observable, parent, false),context);
			case 2: //Observable resource
				return new ObsResourceVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_resource, parent, false),context);
			default:
				return new ObsResourceVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_resource, parent, false),context);
		}
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		AppObject appObject = mDataset.get(position);
		if(appObject instanceof AppServer) {
			ServerVH serverVH = (ServerVH)holder;
			serverVH.appServer = (AppServer)appObject;

			serverVH.name.setText(serverVH.appServer.getName());
			String text = "IP Address: " + serverVH.appServer.getAddress() + "      Port: " + serverVH.appServer.getPort();
			serverVH.details.setText(text);
		}
		else if(appObject instanceof AppResource) {
			for(String chechType : ((AppResource) appObject).getTypes()) {   //TODO Change
				if(chechType.equals("set")) {
					NotObsResourceVH notObsResourceVH = (NotObsResourceVH)holder;
					notObsResourceVH.appResource = (AppResource)appObject;
					notObsResourceVH.name.setText(notObsResourceVH.appResource.getName());
					notObsResourceVH.name.setPadding(25 + (notObsResourceVH.appResource.getTreeDepth()-1) * 55, 5, 0, 0);
					StringBuilder builder = new StringBuilder();
					for(String type : (notObsResourceVH.appResource.getTypes()))
						builder.append(type).append(",");
					String text = "Types: " + builder.toString().substring(0, builder.toString().length() - 1);
					notObsResourceVH.details.setText(text);
					notObsResourceVH.details.setPadding(30 + (notObsResourceVH.appResource.getTreeDepth()-1) * 55, 0, 0, 0);
					switch (notObsResourceVH.appResource.getTreeDepth()) {
						case 1:
							notObsResourceVH.view.setBackgroundColor(Color.parseColor("#90A4AE"));
							break;
//						case 2:
//							notObsResourceVH.view.setBackgroundColor(Color.parseColor("#9CCC65"));
//							break;
						case 2:
							notObsResourceVH.view.setBackgroundColor(Color.parseColor("#B0BEC5"));
							break;
//						case 4:
//							notObsResourceVH.view.setBackgroundColor(Color.parseColor("#C5E1A5"));
//							break;
						case 3:
							notObsResourceVH.view.setBackgroundColor(Color.parseColor("#CFD8DC"));
							break;
						default:
							notObsResourceVH.view.setBackgroundColor(Color.parseColor("#ECEFF1"));
							break;
					}
					return;
				}
			}
			ObsResourceVH obsResourceVH = (ObsResourceVH)holder;
			obsResourceVH.appResource = (AppResource)appObject;
			obsResourceVH.name.setText(obsResourceVH.appResource.getName());
			obsResourceVH.name.setPadding(25 + (obsResourceVH.appResource.getTreeDepth()-1) * 55, 5, 0, 0);
			StringBuilder builder = new StringBuilder();
			for(String type : (obsResourceVH.appResource.getTypes()))
				builder.append(type).append(",");
			String text = "Types: " + builder.toString().substring(0, builder.toString().length() - 1);
			obsResourceVH.details.setText(text);
			obsResourceVH.details.setPadding(30 + (obsResourceVH.appResource.getTreeDepth()-1) * 55, 0, 0, 0);

			obsResourceVH.type.setImageResource(android.R.color.transparent);
			switch (((AppResource) appObject).getTypes().get(0)) {
				case "sensor":
					if(((AppResource) appObject).getName().contains("Temperature"))
						obsResourceVH.type.setImageResource(R.drawable.temperature);
					else if(((AppResource) appObject).getName().contains("Humidity"))
						obsResourceVH.type.setImageResource(R.drawable.humidity);
					else if(((AppResource) appObject).getName().contains("Light"))
						obsResourceVH.type.setImageResource(R.drawable.light);
					break;
				case "switch":
					break;
				case "status":
					if(((AppResource) appObject).getName().contains("Monitor"))
					obsResourceVH.type.setImageResource(R.drawable.monitor);
					break;
				default:
			}

			if(obsResourceVH.appResource.isObserved()) {
				obsResourceVH.observed.setColorFilter(0xffb71c1c);
				obsResourceVH.observed.setImageResource(R.drawable.observing);
			}
			else {
				obsResourceVH.observed.setImageResource(android.R.color.transparent);
			}
			obsResourceVH.loading.setVisibility(View.GONE);
			obsResourceVH.value.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)obsResourceVH.type.getLayoutParams();
			params.addRule(RelativeLayout.START_OF, R.id.value);
			obsResourceVH.type.setLayoutParams(params);
			if(!obsResourceVH.appResource.getValue().equals("-")) {
				String completeText="";
				switch (((AppResource) appObject).getTypes().get(0)) {
					case "sensor":
						if(((AppResource) appObject).getName().contains("Temperature"))
							completeText=obsResourceVH.appResource.getValue()+"° C";
						else if(((AppResource) appObject).getName().contains("Humidity"))
							completeText=obsResourceVH.appResource.getValue()+"%";
						else if(((AppResource) appObject).getName().contains("Light"))
							completeText=obsResourceVH.appResource.getValue()+"lx";
						break;
					case "switch":
						if (obsResourceVH.appResource.getValue().equals("true"))
							completeText = "ON";
						else
							completeText = "OFF";
						break;
					case "status":
						completeText=obsResourceVH.appResource.getValue();
						break;
					default:
				}
				obsResourceVH.value.setText(completeText);
			} else
				obsResourceVH.value.setText("");
			switch (obsResourceVH.appResource.getTreeDepth()) {
				case 1:
					obsResourceVH.view.setBackgroundColor(Color.parseColor("#90A4AE"));
					break;
//				case 2:
//					obsResourceVH.view.setBackgroundColor(Color.parseColor("#9CCC65"));
//					break;
				case 2:
					obsResourceVH.view.setBackgroundColor(Color.parseColor("#B0BEC5"));
					break;
//				case 4:
//					obsResourceVH.view.setBackgroundColor(Color.parseColor("#C5E1A5"));
//					break;
				case 3:
					obsResourceVH.view.setBackgroundColor(Color.parseColor("#CFD8DC"));
					break;
				default:
					obsResourceVH.view.setBackgroundColor(Color.parseColor("#ECEFF1"));
					break;
			}
		}
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	@Override
	public int getItemViewType(int position) {
		if(mDataset.get(position) instanceof AppServer)
			return 0;
		else if(mDataset.get(position) instanceof AppResource) {
			for(String type : ((AppResource) mDataset.get(position)).getTypes())    //TODO Change
				if(type.equals("set"))
					return 1;
			return 2;
		}
		return -1;
	}

	public void addElements(AppObject element, AppObject previous) {
		//If is null, it's a server, append
		if(previous==null) {
			mDataset.add(element);
			notifyItemInserted(mDataset.indexOf(element));
		} else {
			mDataset.add(mDataset.indexOf(previous)+1,element);
			notifyItemInserted(mDataset.indexOf(element));
		}

	}

	public void removeElements(AppObject element) {
		int position = mDataset.indexOf(element);
		mDataset.remove(element);
		notifyItemRemoved(position);
	}

	public void updateValue(AppObject element) {
		notifyItemChanged(mDataset.indexOf(element));
	}

	public void clearDataSet() {
		mDataset.clear();
		notifyDataSetChanged();
	}

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ObsResourceVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
		public Context context;
		public AppResource appResource;
		public View view;
		public TextView name,details,value;
		public ImageView observed, type;
		public ProgressBar loading;

		public ObsResourceVH(View v, Context context) {
			super(v);
			view = v;
			name=(TextView)v.findViewById(R.id.resource_name);
			details=(TextView)v.findViewById(R.id.resource_details);
			value=(TextView)v.findViewById(R.id.value);
			type=(ImageView)v.findViewById(R.id.type_ico);
			v.setOnClickListener(this);
			v.setOnLongClickListener(this);
			observed=(ImageView)v.findViewById(R.id.observing_state);
			loading=(ProgressBar)v.findViewById(R.id.loading);
			this.context=context;
		}

		@Override
		public void onClick(View v) {
			value.setVisibility(View.GONE);
			loading.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)type.getLayoutParams();
			params.addRule(RelativeLayout.START_OF, R.id.loading);
			type.setLayoutParams(params);
			appResource.requireValue();
		}

		@Override
		public boolean onLongClick(View v) {
			if (appResource.registerObs()){
				observed.setColorFilter(0xffb71c1c);
				observed.setImageResource(R.drawable.observing);
			}
			else if(appResource.unregisterObs()) {
				observed.setImageResource(android.R.color.transparent);
			}
			return true;
		}
	}

	public static class NotObsResourceVH extends RecyclerView.ViewHolder {
		public Context context;
		public View view;
		public AppResource appResource;
		public TextView name, details;

		public NotObsResourceVH(View v, Context context) {
			super(v);
			view=v;
			name=(TextView)v.findViewById(R.id.resource_name);
			details=(TextView)v.findViewById(R.id.resource_details);
			this.context=context;
		}
	}

	public static class ServerVH extends RecyclerView.ViewHolder {
		public Context context;
		public View view;
		public AppServer appServer;
		public TextView name, details;

		public ServerVH(View v, Context context) {
			super(v);
			view=v;
			name=(TextView)v.findViewById(R.id.server_name);
			details=(TextView)v.findViewById(R.id.server_details);
			this.context=context;
		}
	}

}
