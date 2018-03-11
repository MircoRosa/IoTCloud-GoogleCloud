package com.dev.mirco.iotcloud.cloud_com;

import android.os.AsyncTask;

import com.dev.mirco.iot.backend.datastore.Datastore;
import com.dev.mirco.iot.backend.datastore.model.CloudScenario;
import com.dev.mirco.iot.backend.datastore.model.CloudServer;
import com.dev.mirco.iotcloud.AppScenario;
import com.dev.mirco.iotcloud.AppServer;
import com.dev.mirco.iotcloud.MyAdapter;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by mirco on 13/06/16.
 */
public class GetServerAsyncTask extends AsyncTask<String, Void, CloudServer> {

	private static Datastore datastoreService = null;
	MyAdapter listAdapter = null;
	AppScenario scenario;

	public GetServerAsyncTask(MyAdapter adapter, AppScenario scenario) {
		this.listAdapter=adapter;
		this.scenario=scenario;
	}

	@Override
	protected CloudServer doInBackground(String... params) {
		if(datastoreService==null) {
			Datastore.Builder builder = new Datastore.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
					.setRootUrl("https://iot220999.appspot.com/_ah/api/");
			datastoreService = builder.build();
		}

		try {
			CloudServer server = datastoreService.datastoreEndpoint().getServer(params[0]).execute();
			return server;
		} catch (IOException e) { }

		return null;
	}

	@Override
	protected void onPostExecute(CloudServer param) {
		AppServer server = new AppServer(param,listAdapter);
		server.initializeResources(param.getResources());
		scenario.getServers().add(server);
	}
}
