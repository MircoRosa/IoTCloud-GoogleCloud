package com.dev.mirco.iotcloud;

import com.dev.mirco.iot.backend.datastore.model.CloudScenario;
import com.dev.mirco.iot.backend.datastore.model.CloudServer;

import java.util.ArrayList;

/**
 * Created by mirco on 01/05/16.
 */
public class AppScenario extends AppObject {
	private MyAdapter listAdapter;
	private String name = null;
	private ArrayList<AppServer> servers = new ArrayList<>();

	public AppScenario() {/*Empty Constructor*/}

	public AppScenario(String name) {
		this.name = name;
	}

	public AppScenario(String name, ArrayList<AppServer> servers) {
		this.name = name;
		this.servers = servers;
	}

	public AppScenario(CloudScenario cloudScenario, MyAdapter adapter) {
		this.name = cloudScenario.getName();
		this.listAdapter=adapter;
		if(cloudScenario.getServers()!=null) {
			for(CloudServer cloudServer : cloudScenario.getServers()) {
				AppServer server = new AppServer(cloudServer,adapter);
				server.initializeResources(cloudServer.getResources());
				servers.add(server);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<AppServer> getServers() {
		return servers;
	}

	public void setServers(ArrayList<AppServer> servers) {
		this.servers = servers;
	}
}
