package com.github.wenj91.engine.io;

import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;
import com.github.wenj91.engine.io.transports.transport.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenj91 on 2016-11-16.
 */
public class EngineIOClient{

    private String url;

    private Transport transport;

    private Map<String, Object> query = new HashMap<>();

    public EngineIOClient(String url){
        this.url = url;
    }

    public void build() throws URISyntaxException {
        Option option = new Option();
        URI uri = new URI(url);
        option.setHost(uri.getHost());
        option.setPort(uri.getPort());
        option.setPath("/engine.io/");
        option.getQuery().put("transport", "websocket");
        transport = new WebSocket(option);
        transport.open();
    }


    public static void main(String...args) throws URISyntaxException {
        EngineIOClient client = new EngineIOClient("ws://127.0.0.1:3000");
        client.build();

    }
}
