package com.dev.mirco.iot.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
//import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

/**
 * Created by mirco on 30/10/15.
 */

@Api(name = "datastore", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.iot.mirco.dev.com", ownerName = "backend.iot.mirco.dev.com", packagePath = ""))
public class DatastoreEndpoint {
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private MessagingEndpoint endpoint = new MessagingEndpoint();

    //FETCHER METHODS
	//TODO logging (see MessagingEndpoint example)

	public void handlePUT(@Named("uri") String uri, @Named("json") String json) throws IOException {
		String[] splitUri = uri.split("/");
		String objectName="";
		Transaction transaction = datastore.beginTransaction();
		try {
			if(uri.equals(" ")) //It's a Scenario
				addScenario(new Gson().fromJson(json, CloudScenario.class));
			else {
				switch (splitUri.length) {
					case 1:     //It's a Server
						CloudServer fetcherServer = new Gson().fromJson(json, CloudServer.class);
						objectName=fetcherServer.getName();
						addServer(fetcherServer,findScenario(splitUri[0]));
						break;
					case 2:     //It's a Resource on 1st level
						CloudResource fetcherResource = new Gson().fromJson(json, CloudResource.class);
						objectName=fetcherResource.getName();
						addResources(fetcherResource, findServer(splitUri[1], findScenario(splitUri[0])));
						break;
					default:    //It's a Resource on deeper level
						CloudResource fetcherResourceDeeper = new Gson().fromJson(json, CloudResource.class);
						objectName=fetcherResourceDeeper.getName();
						addResources(fetcherResourceDeeper, findResource(Arrays.copyOfRange(splitUri,2,splitUri.length),findServer(splitUri[1],findScenario(splitUri[0]))));
						break;
				}
			}
			transaction.commit();
		} finally {
			if(transaction.isActive())
				transaction.rollback();
			else {
				endpoint.sendPUT(uri+"/"+objectName);
			}

		}
	}

	public void handleDELETE(@Named("uri") String uri) throws IOException{
		String[] splitUri = uri.split("/");
		Transaction transaction = datastore.beginTransaction();
		try {
			switch (splitUri.length) {
				case 1:     //It's a Scenario
					deleteDescendants(findScenario(splitUri[0]));
					break;
				case 2:     //It's a Server
					deleteWithDescendants(findServer(splitUri[1], findScenario(splitUri[0])));
					break;
				default:    //It's a Resource
					deleteWithDescendants(findResource(Arrays.copyOfRange(splitUri,2,splitUri.length),findServer(splitUri[1], findScenario(splitUri[0]))));
					break;
			}
			transaction.commit();
		} finally {
			if(transaction.isActive())
				transaction.rollback();
			else {
				endpoint.sendDELETE(uri);
			}
		}
	}

	private void addScenario(CloudScenario fetcherScenario) {
		Entity scenario = new Entity("Scenario",fetcherScenario.getName());
		datastore.put(scenario);
		for(CloudServer fetcherServer : fetcherScenario.getServers()) {
			addServer(fetcherServer,scenario);
		}
	}

	private void addServer(CloudServer fetcherServer, Entity scenario) {
		Entity server = new Entity("Server", fetcherServer.getName(), scenario.getKey());
		server.setProperty("Address",fetcherServer.getAddress());
		server.setProperty("Port",fetcherServer.getPort());
		datastore.put(server);
		for(CloudResource fetcherResource : fetcherServer.getResources())
			addResources(fetcherResource,server);
	}

	private void addResources(CloudResource fetcherResource, Entity parent) {
		Entity resource = new Entity("Resource",fetcherResource.getName(),parent.getKey());
		resource.setProperty("URI",fetcherResource.getURI());
		StringBuilder builder = new StringBuilder();
		for(String type : fetcherResource.getTypes())
			builder.append(type).append(" ");
		resource.setProperty("Types",builder.toString()/*.trim()*/);
		datastore.put(resource);
		for(CloudResource child : fetcherResource.getResChildren())
			addResources(child,resource);
	}

	private Entity findScenario(String scenarioName) {
		Query scenarioQuery = new Query("Scenario")
				.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, KeyFactory.createKey("Scenario", scenarioName)));
		PreparedQuery pq = datastore.prepare(scenarioQuery);
		return pq.asSingleEntity();
	}

	private Entity findServer(String serverName, Entity scenario) {
		Query serverQuery = new Query("Server")
				.setAncestor(scenario.getKey())
				.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, KeyFactory.createKey(scenario.getKey(), "Server", serverName)));
		PreparedQuery pq = datastore.prepare(serverQuery);
		return pq.asSingleEntity();
	}

	private Entity findResource(String[] resourcePath, Entity parent) {
		Query resourceQuery = new Query("Resource")
				.setAncestor(parent.getKey())
				.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, KeyFactory.createKey(parent.getKey(), "Resource", resourcePath[0])));
		PreparedQuery pq = datastore.prepare(resourceQuery);
		if(resourcePath.length==1)
			return pq.asSingleEntity();
		else
			return findResource(Arrays.copyOfRange(resourcePath,1,resourcePath.length),pq.asSingleEntity());
	}

	private List<Entity> getAllDescendants(Entity parent) {
		Query descendantsQuery = new Query()
				.setAncestor(parent.getKey())
				.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,FilterOperator.GREATER_THAN,parent.getKey()));
		PreparedQuery pq = datastore.prepare(descendantsQuery);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}

	private List<Entity> getServerResources(Entity server) {
		Query serverQuery = new Query("Resource")
				.setAncestor(server.getKey());
		PreparedQuery pq = datastore.prepare(serverQuery);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}

	private void deleteWithDescendants(Entity parent) {
		if (parent == null)
			return;
		for (Entity descendant : getAllDescendants(parent))
			datastore.delete(descendant.getKey());
//		datastore.delete(getAllDescendants(parent));    //For batch: list of keys instead of entities (here or in proper method)
		datastore.delete(parent.getKey());
	}

	private void deleteDescendants(Entity parent) {
		if (parent == null)
			return;
		for (Entity descendant : getAllDescendants(parent))
			datastore.delete(descendant.getKey());
	}

	private List<Entity> getAllServers(Entity scenario) {
		Query serversQuery = new Query("Server")
				.setAncestor(scenario.getKey());
		PreparedQuery pq = datastore.prepare(serversQuery);
		return  pq.asList(FetchOptions.Builder.withDefaults());
	}


    //ANDROID CLIENT METHODS
    public CloudScenario getScenario(@Named("scenario") String scenario) throws IOException{
	    CloudScenario updatedScenario = new CloudScenario(scenario);
	    for(Entity server : getAllServers(findScenario(scenario))) {
		    CloudServer updatedServer = new CloudServer(server.getKey().getName());
		    updatedServer.setAddress(server.getProperty("Address").toString());
		    updatedServer.setPort(Integer.valueOf(server.getProperty("Port").toString()));
		    ArrayList<CloudResource> resources = new ArrayList<>();
		    for(Entity resource : getServerResources(server))
			    resources.add(new CloudResource(resource.getKey().getName(),(String)resource.getProperty("URI"),new ArrayList<>(Arrays.asList(((String)resource.getProperty("Types")).split("\\s")))));
		    updatedServer.setResources(resources);
		    updatedScenario.getServers().add(updatedServer);
	    }
	    endpoint.sendNotification(scenario+" retrieved from Cloud");

        return updatedScenario;
    }

	public CloudServer getServer(@Named("uri") String uri) {
		String[] splitUri = uri.split("/");
		Entity server = findServer(splitUri[1], findScenario(splitUri[0]));
		CloudServer updatedServer = new CloudServer(server.getKey().getName());
		updatedServer.setAddress(server.getProperty("Address").toString());
		updatedServer.setPort(Integer.valueOf(server.getProperty("Port").toString()));
		ArrayList<CloudResource> resources = new ArrayList<>();
		for(Entity resource : getServerResources(server))
			resources.add(new CloudResource(resource.getKey().getName(),(String)resource.getProperty("URI"),new ArrayList<>(Arrays.asList(((String)resource.getProperty("Types")).split("\\s")))));
		updatedServer.setResources(resources);
		return updatedServer;
	}

	public CloudResource getResource(@Named("uri") String uri) {
		String[] splitUri = uri.split("/");
		Entity resource = findResource(Arrays.copyOfRange(splitUri, 2, splitUri.length), findServer(splitUri[1], findScenario(splitUri[0])));
		CloudResource updatedResource = new CloudResource(resource.getKey().getName(),(String)resource.getProperty("URI"),new ArrayList<>(Arrays.asList(((String)resource.getProperty("Types")).split("\\s"))));
		for(Entity child : getAllDescendants(resource)) {
			updatedResource.getResChildren().add(new CloudResource(child.getKey().getName(),(String)child.getProperty("URI"),new ArrayList<>(Arrays.asList(((String)child.getProperty("Types")).split("\\s")))));
		}
		return updatedResource;
	}
}
