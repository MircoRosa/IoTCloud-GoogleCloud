package com.dev.mirco.iotcloud;

import com.dev.mirco.iot.backend.datastore.model.CloudResource;
import com.dev.mirco.iot.backend.datastore.model.CloudServer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by mirco on 01/05/16.
 */
public class AppServer extends AppObject {
	private String name = null, address = null;
	private int port = 0;
	private MyAdapter listAdapter;
	private ArrayList<AppResource> resources = new ArrayList<>();

	public AppServer() {/*Empty Constructor*/}

	public AppServer(String name) {
		this.name = name;
	}

	public AppServer(String name, String address, int port, ArrayList<AppResource> resources) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.resources = resources;
	}

	public AppServer(CloudServer cloudServer, MyAdapter adapter) {
		this.name = cloudServer.getName();
		this.address = cloudServer.getAddress();
		this.port = cloudServer.getPort().intValue();
		this.listAdapter = adapter;
		listAdapter.addElements(this,null);
	}

	public void addResources(List<CloudResource> cloudResources) {
		for(CloudResource cloudResource : cloudResources) {
			AppResource resource = new AppResource(cloudResource,null,this,listAdapter);
			resource.addChildren(cloudResource.getResChildren());
			resources.add(resource);
		}
	}

	public void initializeResources(List<CloudResource> cloudResources) {
		while(!cloudResources.isEmpty()) {
			ArrayList<CloudResource> resources = new ArrayList<>();
			resources.add(cloudResources.get(0));
			cloudResources.remove(0);
			int parentIndex = 0;
			for(int i=0; i<cloudResources.size(); i++) {
				if(cloudResources.get(i).getUri().substring(1).split("/")[0].equals(resources.get(0).getUri().substring(1).split("/")[0])) {
					if(cloudResources.get(i).getUri().substring(1).split("/").length==3)
						parentIndex=i;
					resources.add(cloudResources.get(i));
					cloudResources.remove(i);
					i--;
				}
			}
			AppResource appResource = new AppResource(resources.get(parentIndex),null,this,listAdapter);
			resources.remove(parentIndex);
			appResource.initializeChildren(resources);
			this.resources.add(appResource);
		}
	}

	public void removeServer() {
		for(AppResource resource : resources)
			resource.removeResource();
		resources.clear();
		listAdapter.removeElements(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ArrayList<AppResource> getResources() {
		return resources;
	}

	public void setResources(ArrayList<AppResource> resources) {
		this.resources = resources;
	}

	public MyAdapter getListAdapter() {
		return listAdapter;
	}

	public void setListAdapter(MyAdapter listAdapter) {
		this.listAdapter = listAdapter;
	}
}
