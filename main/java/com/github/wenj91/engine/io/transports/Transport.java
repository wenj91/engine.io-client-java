package com.github.wenj91.engine.io.transports;

import com.github.wenj91.engine.io.emitter.Emitter;
import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.parser.Parser;
import com.github.wenj91.engine.io.transports.base.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wenj91 on 16-11-19.
 */
public abstract class Transport extends Emitter{

    private Option option;

    Executor executor;

    public Transport(Option option){
        this.option = option;
    }

    public Transport onError(String msg, String desc){
        Map<String, Object> error = new HashMap<>();
        error.put("type", "TransportError");
        error.put("desc", desc);
        emit("onError", error);
        return this;
    }

    public Transport open(){
        if(executor == null){
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //do sth
                doOpen();
            }
        });
        return this;
    }

    public void send(Packet...packets){
        if(executor == null){
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //send
                //send the packet
                sendPacket(packets);
            }
        });
    }

    protected void onMessage(String data){
        this.onPacket(Parser.encode2Packet(data));
    }

    protected void onOpen(){
        this.emit("open");
    }

    protected void onPacket(Packet packet){
        this.emit("packet", packet);
    }

    protected void onClose(){
        this.emit("close");
    }

    public Transport close(){
        if(executor == null){
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //close
                //do sth
                doClose();
                onClose();
            }
        });

        return this;
    }

    public abstract String uri();

    public abstract void sendPacket(Packet...packets);

    public abstract void doOpen();

    public abstract void doClose();

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
