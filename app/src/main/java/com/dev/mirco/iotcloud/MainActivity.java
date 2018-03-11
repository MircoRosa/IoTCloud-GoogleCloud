package com.dev.mirco.iotcloud;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.mirco.iotcloud.cloud_com.GetResourceAsyncTask;
import com.dev.mirco.iotcloud.cloud_com.GetScenarioAsyncTask;
import com.dev.mirco.iotcloud.cloud_com.GetServerAsyncTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	public RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private BroadcastReceiver notificationBReceiver, putBReceiver, deleteBReceiver, dataBReceiver;
	private IntentFilter notificationFilter, putFilter, deleteFilter, dataFilter;
	public ProgressDialog initialDialog;

	public AppScenario scenario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main_v2);

	    Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
	    setSupportActionBar(myToolbar);
	    getSupportActionBar().setTitle("TestScenario");
	    getSupportActionBar().hide();

	    GcmRegistrationAsyncTask registrationAsyncTask = new GcmRegistrationAsyncTask(this);
	    registrationAsyncTask.execute();

	    mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

	    // use this setting to improve performance if you know that changes
	    // in content do not change the layout size of the RecyclerView
	    mRecyclerView.setHasFixedSize(true);

	    // use a linear layout manager
	    mLayoutManager = new LinearLayoutManager(this);
	    mRecyclerView.setLayoutManager(mLayoutManager);

	    // specify an adapter (see also next example)
	    mAdapter = new MyAdapter(new ArrayList<AppObject>(), getApplicationContext());
	    mRecyclerView.setAdapter(mAdapter);


	    initialDialog = new ProgressDialog(MainActivity.this);
	    initialDialog.setMessage("Retrieving scenario from Datastore");
	    initialDialog.show();

	    GetScenarioAsyncTask asyncTask = new GetScenarioAsyncTask(this);
	    asyncTask.execute();

	    /*
	    * Broadcast Receivers
	    */
	    notificationBReceiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
//			    Toast.makeText(MainActivity.this, intent.getExtras().getString("text"), Toast.LENGTH_SHORT).show();
		    }
	    };
	    notificationFilter = new IntentFilter("NOTIFICATION");
	    registerReceiver(notificationBReceiver, notificationFilter);


	    putBReceiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
			    String[] splitUri = intent.getExtras().getString("uri").split("/");
			    if(!intent.getExtras().getString("uri").equals(" ")) { //It's a Scenario
				    switch (splitUri.length) {
					    case 2:     //It's a Server
						    GetServerAsyncTask asyncTask1 = new GetServerAsyncTask((MyAdapter)mAdapter,scenario);
							asyncTask1.execute(intent.getExtras().getString("uri"));
						    break;
					    default:    //It's a Resource
						    GetResourceAsyncTask asyncTask2 = new GetResourceAsyncTask((MyAdapter)mAdapter,scenario,context);
						    asyncTask2.execute(intent.getExtras().getString("uri"));
						    break;
				    }
			    }

		    }
	    };
	    putFilter = new IntentFilter("PUT");
	    registerReceiver(putBReceiver,putFilter);


	    deleteBReceiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
			    String[] splitUri = intent.getExtras().getString("uri").split("/");
			    if(splitUri[0].equals("TestScenario") && splitUri.length==1) {    //It's a scenario
				    ((MyAdapter)mAdapter).clearDataSet();
				    scenario.getServers().clear();
				    return;
			    }
			    switch (splitUri.length) {
				    case 2:     //It's a server
					    Iterator<AppServer> iterator = scenario.getServers().iterator();
					    while(iterator.hasNext()) {
						    AppServer server = iterator.next();
						    if(server.getName().equals(splitUri[1])) {
							    server.removeServer();
							    iterator.remove();
							    break;
						    }
					    }
					    break;
				    default:    //It's a resource
					    for(AppServer server : scenario.getServers()) {
						    if (server.getName().equals(splitUri[1])) {
							    Iterator<AppResource> resIterator = server.getResources().iterator();
							    while (resIterator.hasNext()) {
								    AppResource resource = resIterator.next();
								    if (splitUri[2].equals(resource.getName())) {
									    if(splitUri.length==3) {    //1st level
										    resource.removeResource();
										    resIterator.remove();
										    break;
									    } else {  //Deeper level
										    recursiveRemove(resource, splitUri);
									    }
								    }
							    }
						    }
					    }
					    break;
			    }
		    }

		    private void recursiveRemove(AppResource resource, String[] splitUri) {
			    Iterator<AppResource> resIterator = resource.getResChildren().iterator();
			    while(resIterator.hasNext()) {
				    AppResource res = resIterator.next();
				    if(res.getName().equals(splitUri[res.getTreeDepth()+1])) {
					    if(splitUri.length==res.getTreeDepth()+2) {
						    res.removeResource();
						    resIterator.remove();
						    break;
					    } else
						    recursiveRemove(res,splitUri);
				    }
			    }

		    }
	    };
	    deleteFilter = new IntentFilter("DELETE");
	    registerReceiver(deleteBReceiver,deleteFilter);


	    dataBReceiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
			    ResponseDescriptor responseDescriptor = new Gson().fromJson(intent.getExtras().getString("value"),ResponseDescriptor.class);
//			    String test = "Server: "+responseDescriptor.getServer()
//					    +"\nResource: "+responseDescriptor.getResource()
//					    +"\nValue: "+responseDescriptor.getValue()
//					    +"\nToken: "+(responseDescriptor.getToken()==null?" ":new String(responseDescriptor.getToken()));
//			    Toast.makeText(MainActivity.this, test, Toast.LENGTH_LONG).show();

			    String[] splitUri = (responseDescriptor.getServer()+responseDescriptor.getResource()).split("/");

			    for(AppServer server : scenario.getServers()) {
				    if (server.getName().equals(splitUri[0])) {
					    Iterator<AppResource> resIterator = server.getResources().iterator();
					    while (resIterator.hasNext()) {
						    AppResource resource = resIterator.next();
						    if (splitUri[1].equals(resource.getName())) {
							    if(splitUri.length==2) {    //1st level
								    resource.setValue(responseDescriptor.getValue());
								    resource.checkToken(responseDescriptor.getToken());
								    break;
							    } else {  //Deeper level
								    recursiveSearch(resource, splitUri,responseDescriptor);
							    }
						    }
					    }
				    }
			    }

		    }

		    private void recursiveSearch(AppResource resource, String[] splitUri, ResponseDescriptor descriptor) {
			    Iterator<AppResource> resIterator = resource.getResChildren().iterator();
			    while(resIterator.hasNext()) {
				    AppResource res = resIterator.next();
				    if(res.getName().equals(splitUri[res.getTreeDepth()])) {
					    if(splitUri.length==res.getTreeDepth()+1) {
						    res.setValue(descriptor.getValue());
						    res.checkToken(descriptor.getToken());
						    break;
					    } else
						    recursiveSearch(res, splitUri,descriptor);
				    }
			    }

		    }
	    };
	    dataFilter = new IntentFilter("DATA");
	    registerReceiver(dataBReceiver,dataFilter);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolbar_menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
//			case R.id.refresh:
//				Toast.makeText(MainActivity.this, "Refresh action clicked!", Toast.LENGTH_SHORT).show();
//				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}