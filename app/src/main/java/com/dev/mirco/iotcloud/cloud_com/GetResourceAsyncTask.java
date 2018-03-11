package com.dev.mirco.iotcloud.cloud_com;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dev.mirco.iot.backend.datastore.Datastore;
import com.dev.mirco.iot.backend.datastore.model.CloudResource;
import com.dev.mirco.iotcloud.AppResource;
import com.dev.mirco.iotcloud.AppScenario;
import com.dev.mirco.iotcloud.AppServer;
import com.dev.mirco.iotcloud.MyAdapter;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mirco on 13/06/16.
 */
public class GetResourceAsyncTask extends AsyncTask<String, Void, CloudResource> {

	private static Datastore datastoreService = null;
	MyAdapter listAdapter = null;
	AppScenario scenario;
	Context context;
	String[] splitUri;

	public GetResourceAsyncTask(MyAdapter adapter, AppScenario scenario, Context context) {
		this.listAdapter=adapter;
		this.scenario=scenario;
		this.context=context;
	}

	@Override
	protected CloudResource doInBackground(String... params) {
		if(datastoreService==null) {
			Datastore.Builder builder = new Datastore.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
					.setRootUrl("https://iot220999.appspot.com/_ah/api/");
			datastoreService = builder.build();
		}
		splitUri = Arrays.copyOfRange(params[0].split("/"),0,params[0].split("/").length-1);   //Excludes the resource

		try {
			return datastoreService.datastoreEndpoint().getResource(params[0]).execute();
		} catch (IOException e) { }

		return null;
	}

	@Override
	protected void onPostExecute(CloudResource cloudResource) {
		for(AppServer server : scenario.getServers()) {
			if(server.getName().equals(splitUri[1])) {
				if(splitUri.length==2) {    //1st level
					AppResource appResource = new AppResource(cloudResource, null, server, listAdapter);
					if(cloudResource.getResChildren()!=null)
						appResource.initializeChildren(new ArrayList<>(cloudResource.getResChildren()));
					server.getResources().add(appResource);
					break;
				} else {    //Deeper level
					for(AppResource res : server.getResources()) {
						if(res.getName().equals(splitUri[2])) {
							if(splitUri.length==3) {
								AppResource appResource = new AppResource(cloudResource, res, res.getServer(), listAdapter);
								if(cloudResource.getResChildren()!=null)
									appResource.initializeChildren(new ArrayList<>(cloudResource.getResChildren()));
								res.getResChildren().add(appResource);
							}
							else
								addDeeperResource(res, splitUri,cloudResource);
						}
					}
				}
			}
		}
	}

	private void addDeeperResource(AppResource resource, String[] splitUri, CloudResource cloudResource) {
		for(AppResource res : resource.getResChildren()) {
			if(res.getName().equals(splitUri[res.getTreeDepth()+1])) {
				if(splitUri.length==res.getTreeDepth()+2) {
					AppResource appResource = new AppResource(cloudResource, res, res.getServer(), listAdapter);
					if(cloudResource.getResChildren()!=null)
						appResource.initializeChildren(new ArrayList<>(cloudResource.getResChildren()));
					res.getResChildren().add(appResource);
				}
				else
					addDeeperResource(res, splitUri,cloudResource);
			}
		}
	}
}
