package com.dev.mirco.iot.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

/**
 * Created by mirco on 27/04/15.
 */

@Api(name = "channel", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.iot.mirco.dev.com", ownerName = "backend.iot.mirco.dev.com", packagePath = ""))
public class ChannelEndpoint {

    //TODO JS FOLDER IN BACKEND!
    //@ApiMethod(name="createChannel") TODO Why this not works in JS?
    public MyToken createChannel() {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        String channelKey = "TestChannel";
        String channelToken = channelService.createChannel(channelKey);
        MyToken token = new MyToken();
        token.setMyToken(channelToken);
        return token;
    }

    public void sendOnChannel(@Named("payload") String payload) {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        String channelKey = "TestChannel";
        channelService.sendMessage(new ChannelMessage(channelKey,payload));
    }

}
