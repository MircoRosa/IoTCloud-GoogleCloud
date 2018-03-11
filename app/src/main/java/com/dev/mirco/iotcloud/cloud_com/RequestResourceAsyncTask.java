package com.dev.mirco.iotcloud.cloud_com;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dev.mirco.iot.backend.channel.Channel;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by mirco on 29/04/15.
 */
public class RequestResourceAsyncTask extends AsyncTask<String, Void, String> {
//    private Context context;
    private static Channel channelService = null;

    public RequestResourceAsyncTask(/*Context context*/) {
//        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
//        String server = params[0], resource=params[1], reqType=params[2];

//        String URI = createGetURI(server, resource, reqType);
        //String URI = new Gson().toJson(new RequestDescriptor(server,resource));
        String URI = "RoomMonitor1/SensorSet1/TemperatureSensor1?observing=false";

        if(channelService ==null) {
            Channel.Builder builder = new Channel.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://iot220999.appspot.com/_ah/api/");
            channelService =builder.build();
        }

        try {
            channelService.channelEndpoint().sendOnChannel(params[0]).execute();
        } catch (IOException e) { }

        return URI; //TODO Change
    }

    @Override
    protected void onPostExecute(String URI) {
//        Toast.makeText(context, URI, Toast.LENGTH_SHORT).show();
    }

}
