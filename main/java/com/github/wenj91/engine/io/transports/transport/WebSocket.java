package com.github.wenj91.engine.io.transports.transport;

import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;
import com.github.wenj91.engine.io.util.QueryUtil;

/**
 * Created by wenj91 on 16-11-19.
 */
public class WebSocket extends Transport{

    private okhttp3.ws.WebSocket webSocket;

    public WebSocket(Option option) {
        super(option);
    }

    @Override
    public String uri() {
        StringBuffer sb = new StringBuffer();
        sb.append("ws://").append(this.getOption().getHost());
        if(this.getOption().getPort()!= null){
            sb.append(":").append(this.getOption().getPort());
        }
        sb.append(this.getOption().getPath());
        if(this.getOption().getQuery().size()>0){
            sb.append("?").append(QueryUtil.map2Query(this.getOption().getQuery()));
        }

        return sb.toString();
    }

    public static void main(String...args){
        Option option = new Option();
        option.setHost("127.0.0.1");
        option.setPort(3000);
        option.setPath("/engine.io");
        option.getQuery().put("transport", "websocket");
        WebSocket ws = new WebSocket(option);
        System.out.println(ws.uri());
    }
}
