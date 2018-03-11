package com.dev.mirco.iotcloud;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mirco on 22/04/15.
 */
public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                handleMessage(extras);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void handleMessage(final Bundle extras) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
	            Intent intent = null;
	            switch (IoTMessageType.values()[Integer.valueOf(extras.getString("type"))]) {
		            case PUT:
			            intent = new Intent("PUT");
			            intent.putExtra("uri", extras.getString("uri"));
			            intent.putExtra("payload", extras.getString("payload"));
			            break;
		            case DELETE:
			            intent = new Intent("DELETE");
			            intent.putExtra("uri", extras.getString("uri"));
			            break;
		            case NOTIFICATION:
			            intent = new Intent("NOTIFICATION");
			            intent.putExtra("text", extras.getString("text"));
			            break;
		            case DATA:
			            intent = new Intent("DATA");
			            intent.putExtra("value",extras.getString("value"));
			            break;
		            default:
			            break;
	            }
	            getApplicationContext().sendBroadcast(intent);
            }
        });
    }
}
