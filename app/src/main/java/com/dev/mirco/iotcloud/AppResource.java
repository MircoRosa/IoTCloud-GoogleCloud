package com.dev.mirco.iotcloud;

import com.dev.mirco.iot.backend.datastore.model.CloudResource;
import com.dev.mirco.iotcloud.cloud_com.RequestResourceAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mirco on 01/05/16.
 */
public class AppResource extends AppObject {
	private String name = null;
	private String uri = null;
	private ArrayList<String> types = null;
	private int treeDepth;
	private String value = "-";
	private AppServer server;
	private AppResource parent;
	private MyAdapter listAdapter;
	private boolean observed = false;
	private byte[] token = new byte[8];
	private ArrayList<AppResource> resChildren = new ArrayList<>();

	public AppResource() {/*Empty Constructor*/}

	public AppResource(String name, String uri, ArrayList<String> types) {
		this.name = name;
		this.uri = uri;
		this.types = types;
	}

	public AppResource(CloudResource cloudResource, AppResource parent, AppServer server, MyAdapter adapter) {
		this.name=cloudResource.getName();
		this.uri=cloudResource.getUri();
		this.types=new ArrayList<>(cloudResource.getTypes());
		this.parent=parent;
		this.server=server;
		this.treeDepth = uri.substring(1).split("/").length;
		this.listAdapter=adapter;
		listAdapter.addElements(this,parent);
	}

	public void removeChildren() {
		Iterator<AppResource> iterator = resChildren.iterator();
		while(iterator.hasNext()) {
			AppResource resource = iterator.next();
			resource.removeChildren();
			// TODO resource.clearObservations();
//			if(requiresCloudUpdate)	getServer().getLauncher().removeFromCloud(resource.getCloudURI());
			listAdapter.removeElements(resource);
			iterator.remove();
		}
	}

	public void addChildren(List<CloudResource> cloudResources) {
		for(CloudResource cloudResource : cloudResources) {
			AppResource resource = new AppResource(cloudResource,this,server,listAdapter);
			resource.addChildren(cloudResource.getResChildren());
			resChildren.add(resource);
		}

	}

	public void initializeChildren(ArrayList<CloudResource> cloudResources) {
		while(!cloudResources.isEmpty()) {
			ArrayList<CloudResource> resources = new ArrayList<>();
			resources.add(cloudResources.get(0));
			cloudResources.remove(0);
			int parentIndex = 0;
			for(int i=0; i<cloudResources.size(); i++) {
				if(cloudResources.get(i).getUri().substring(1).split("/")[treeDepth].equals(resources.get(0).getUri().substring(1).split("/")[treeDepth])) {
					if(cloudResources.get(i).getUri().substring(1).split("/").length==3)
						parentIndex=i;
					resources.add(cloudResources.get(i));
					cloudResources.remove(i);
					i--;
				}
			}
			AppResource appResource = new AppResource(resources.get(parentIndex),this,getServer(),listAdapter);
			resources.remove(parentIndex);
			appResource.initializeChildren(resources);
			this.resChildren.add(appResource);
		}
	}

	public void removeResource() {
		for(AppResource resource : resChildren)
			resource.removeResource();
		resChildren.clear();
		listAdapter.removeElements(this);
	}

	public void requireValue() {
		RequestResourceAsyncTask asyncTask = new RequestResourceAsyncTask();
		asyncTask.execute(server.getName() + uri);
	}

	public boolean registerObs() {
		if(Arrays.equals(this.token,new byte[8]) && !observed) {
			RequestResourceAsyncTask asyncTask = new RequestResourceAsyncTask();
			asyncTask.execute(server.getName() + uri + "?observing=true");
			setObserved(true);
			return true;
		} else
			return false;
	}

	public boolean unregisterObs() {
		if(!Arrays.equals(this.token,new byte[8]) && observed) {
			RequestResourceAsyncTask asyncTask = new RequestResourceAsyncTask();
			asyncTask.execute(server.getName() + uri + "?observing=true&token=" + new String(token));
			setObserved(false);
			token = new byte[8];
			return true;
		} else
			return false;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value=value;
		listAdapter.updateValue(this);
	}

	public void checkToken(byte[] token) {
		if(!Arrays.equals(token,new byte[8]) && Arrays.equals(this.token,new byte[8]) && observed) {
			setToken(token);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public AppServer getServer() {
		return server;
	}

	public void setServer(AppServer server) {
		this.server = server;
	}

	public AppResource getParent() {
		return parent;
	}

	public void setParent(AppResource parent) {
		this.parent = parent;
	}

	public ArrayList<String> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	public ArrayList<AppResource> getResChildren() {
		return resChildren;
	}

	public void setResChildren(ArrayList<AppResource> resChildren) {
		this.resChildren = resChildren;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public void setTreeDepth(int treeDepth) {
		this.treeDepth = treeDepth;
	}

	public boolean isObserved() {
		return observed;
	}

	public void setObserved(boolean observed) {
		this.observed = observed;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}
}
