package com.github.wenj91.engine.io;

import com.alibaba.fastjson.JSON;
import com.github.wenj91.engine.io.emitter.Emitter;
import com.github.wenj91.engine.io.emitter.listener.Listener;
import com.github.wenj91.engine.io.encoding.HandshakeData;
import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.enums.EmitterEventType;
import com.github.wenj91.engine.io.enums.PacketType;
import com.github.wenj91.engine.io.parser.Parser;
import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;
import com.github.wenj91.engine.io.transports.transport.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wenj91 on 2016-11-16.
 */
public class EngineIO extends Emitter{

    private String url;

    private Transport transport;

    private Map<String, Object> query = new HashMap<>();

    public EngineIO(String url){
        this.url = url;
    }

    public void open() throws URISyntaxException {
        Option option = new Option();
        URI uri = new URI(url);
        option.setHost(uri.getHost());
        option.setPort(uri.getPort());
        option.setPath("/engine.io/");
        option.getQuery().put("transport", "websocket");
        transport = new WebSocket(option);

        EngineIO self = this;

        transport.on(EmitterEventType.onPacket.getEvent(), new Listener() {
            @Override
            public void call(Object... args) {
                onMessage((Packet)args[0]);
            }
        });

        transport.open();
    }

    //onMessage
    public EngineIO onMessage(Packet packet){
//        transport.
        System.out.println("Message:" + Parser.decode2String(packet));

        if(PacketType.OPEN.getValue().equals(packet.getTypeId())) {

            emit("OnOpen", packet);

            HandshakeData handshakeData = Parser.encode2HandshakeData(packet.getData());

            ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
            schedule.	scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    ping();
                }
            }, 0, handshakeData.getPingInterval(), TimeUnit.MILLISECONDS);
        }else if(PacketType.MESSAGE.getValue().equals(packet.getTypeId())){

        }else if(PacketType.CLOSE.getValue().equals(packet.getTypeId())){

        }

        return this;
    }

    //close
    public void close(){

    }

    //send
    public void sendMessage(String message){
        Packet packet  = new Packet(PacketType.MESSAGE.getValue(), message);
        transport.send(packet);
    }

    //ping
    private void ping(){
        Packet packet = new Packet(PacketType.PING.getValue(), "ping");
        transport.send(packet);
        emit("ping", packet);
    }


    public static void main(String...args) throws URISyntaxException {
        EngineIO client = new EngineIO("ws://127.0.0.1:3000");
        client.open();
        client.on("OnOpen", new Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(JSON.toJSONString(args));

                client.sendMessage("1fds你好帅色调！");
            }
        });


    }
}
