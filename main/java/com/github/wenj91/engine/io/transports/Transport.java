package com.github.wenj91.engine.io.transports;

import com.github.wenj91.engine.io.emitter.Emitter;
import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.parser.Parser;
import com.github.wenj91.engine.io.transports.base.Option;

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

    public Transport open(){
        if(executor == null){
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //open
                //send ping interval
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
            }
        });
    }

    public void onData(String data){
        this.onPacket(Parser.encode2Packet(data));
    }

    void onPacket(Packet packet){
        this.emit("packet", packet);
    }

    public Transport close(){
        if(executor == null){
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //close
            }
        });

        return this;
    }

    public abstract String uri();

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
