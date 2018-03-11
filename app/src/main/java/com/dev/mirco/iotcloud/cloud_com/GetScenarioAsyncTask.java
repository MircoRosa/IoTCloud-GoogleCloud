package com.dev.mirco.iotcloud.cloud_com;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dev.mirco.iot.backend.datastore.Datastore;
import com.dev.mirco.iot.backend.datastore.model.CloudResource;
import com.dev.mirco.iot.backend.datastore.model.CloudScenario;
import com.dev.mirco.iot.backend.datastore.model.CloudServer;
import com.dev.mirco.iotcloud.AppScenario;
import com.dev.mirco.iotcloud.MainActivity;
import com.dev.mirco.iotcloud.MyAdapter;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mirco on 13/06/16.
 */
public class GetScenarioAsyncTask extends AsyncTask<Void, Void, CloudScenario> {

	private static Datastore datastoreService = null;
	private MainActivity activity;

	public GetScenarioAsyncTask(MainActivity activity) {
		this.activity=activity;
	}

	@Override
	protected CloudScenario doInBackground(Void... params) {
		if(datastoreService==null) {
			Datastore.Builder builder = new Datastore.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
					.setRootUrl("https://iot220999.appspot.com/_ah/api/");
			datastoreService = builder.build();
		}

		try {
			CloudScenario scenario = datastoreService.datastoreEndpoint().getScenario("TestScenario").execute();
			return scenario;
		} catch (IOException e) { }

		return null;
	}

	@Override
	protected void onPostExecute(CloudScenario cloudScenario) {
		activity.scenario = new AppScenario(cloudScenario,(MyAdapter)activity.mAdapter);
		activity.initialDialog.dismiss();
		Toast.makeText(activity, "Tap for GET, long press to Observe", Toast.LENGTH_LONG).show();
	}
}
