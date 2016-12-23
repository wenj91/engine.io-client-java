package com.github.wenj91.engine.io.transports;

import com.github.wenj91.engine.io.emitter.Emitter;
import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.enums.EmitterEventType;
import com.github.wenj91.engine.io.parser.Parser;
import com.github.wenj91.engine.io.thread.EventThread;
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


    public Transport(Option option){
        this.option = option;
    }

    public Transport onError(String msg, String desc){
        Map<String, Object> error = new HashMap<>();
        error.put("type", "TransportError");
        error.put("desc", desc);
        emit(EmitterEventType.onError.getEvent(), msg, error);
        return this;
    }

    public Transport open(){
        EventThread.execTask(new Runnable() {
            @Override
            public void run() {
                doOpen();
            }
        });
        return this;
    }

    public void send(Packet...packets){
        EventThread.execTask(new Runnable() {
            @Override
            public void run() {
                sendPacket(packets);
            }
        });
    }

    public void onMsg(String data){
        this.onPacket(Parser.encode2Packet(data));
    }

    protected void onOpen(){
        this.emit(EmitterEventType.onOpen.getEvent());
    }

    protected void onPacket(Packet packet){
        this.emit(EmitterEventType.onPacket.getEvent(), packet);
    }

    protected void onClose(){
        this.emit(EmitterEventType.onClose.getEvent());
    }

    public Transport close(){
        EventThread.execTask(new Runnable() {
            @Override
            public void run() {
                doClose();
                onClose();
            }
        });

        return this;
    }

    public abstract String uri();

    protected abstract void sendPacket(Packet...packets);

    protected abstract void doOpen();

    protected abstract void doClose();

    protected Option getOption() {
        return option;
    }

    protected void setOption(Option option) {
        this.option = option;
    }
}
