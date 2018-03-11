package com.dev.mirco.iotcloud.perf_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.mirco.iotcloud.GcmRegistrationAsyncTask;
import com.dev.mirco.iotcloud.R;
import com.dev.mirco.iotcloud.ResponseDescriptor;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivityTesting extends AppCompatActivity implements CallbackInterface{

	private RecyclerView mRecyclerView;
	public RecyclerView.Adapter mAdapter;
	private LinearLayoutManager mLayoutManager;
	private BroadcastReceiver dataBReceiver;
	private IntentFilter dataFilter;
	private long start;

	public MainActivityTesting activityTesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main_v2);

	    activityTesting=this;

	    Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
	    setSupportActionBar(myToolbar);
	    getSupportActionBar().setTitle("GET Test");

	    GcmRegistrationAsyncTask registrationAsyncTask = new GcmRegistrationAsyncTask(this);
	    registrationAsyncTask.execute();

	    mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

	    // use this setting to improve performance if you know that changes
	    // in content do not change the layout size of the RecyclerView
	    mRecyclerView.setHasFixedSize(true);

	    // use a linear layout manager
	    mLayoutManager = new LinearLayoutManager(this);
	    mLayoutManager.setReverseLayout(true);
	    mLayoutManager.setStackFromEnd(true);
	    mRecyclerView.setLayoutManager(mLayoutManager);

	    // specify an adapter (see also next example)
	    ArrayList<long[]> dataset = new ArrayList<>();
	    mAdapter = new TestAdapter(getApplicationContext(),dataset);
	    mRecyclerView.setAdapter(mAdapter);

	    /*
	    * Broadcast Receivers
	    */


	    start = 0;
	    dataBReceiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
			    ResponseDescriptor responseDescriptor = new Gson().fromJson(intent.getExtras().getString("value"),ResponseDescriptor.class);


			    ((TestAdapter)mAdapter).addValue(System.currentTimeMillis()-start);
			    mRecyclerView.scrollToPosition(mAdapter.getItemCount());
//			    TestGETAsyncTask testGETAsyncTask = new TestGETAsyncTask();
//			    testGETAsyncTask.callback=activityTesting;
//			    testGETAsyncTask.execute("Useless");
//			    Toast.makeText(MainActivityTesting.this, "Something was received!", Toast.LENGTH_SHORT).show();
			    final Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
				    @Override
				    public void run() {
					    TestGETAsyncTask testGETAsyncTask = new TestGETAsyncTask();
					    testGETAsyncTask.callback=activityTesting;
					    testGETAsyncTask.execute("Useless");				    }
			    }, 1000);

		    }

	    };
	    dataFilter = new IntentFilter("DATA");
	    registerReceiver(dataBReceiver,dataFilter);



	}

	@Override
	public void setStart(long start) {
		this.start=start;
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
////				Toast.makeText(MainActivityTesting.this, "Refresh action clicked!", Toast.LENGTH_SHORT).show();
//				TestGETAsyncTask testGETAsyncTask = new TestGETAsyncTask();
//				testGETAsyncTask.callback=activityTesting;
//				testGETAsyncTask.execute("Useless");
//				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}