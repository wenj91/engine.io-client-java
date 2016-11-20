package com.github.wenj91.engine.io.emitter;

import com.github.wenj91.engine.io.emitter.Listener.Listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by wenj91 on 16-11-19.
 * this code is copy from
 */
public class Emitter {

    public ConcurrentMap<String, ConcurrentLinkedQueue<Listener>>
            callbacks = new ConcurrentHashMap<>();

    //Emitter#on(event, fn)
    public Emitter on(String event, Listener listener){
        ConcurrentLinkedQueue<Listener> listeners = this.callbacks.get(event);
        if(listeners == null){
            listeners = new ConcurrentLinkedQueue<>();
            ConcurrentLinkedQueue<Listener> temp = this.callbacks.putIfAbsent(event, listeners);
            if(temp!=null){
                listeners = temp;
            }
        }
        listeners.add(listener);

        return this;
    }

    //Emitter#off(event, fn)
    public Emitter off(String event, Listener listener){
        ConcurrentLinkedQueue<Listener> listeners
                = this.callbacks.get(event);
        if(listeners != null){
            Iterator<Listener> it = listeners.iterator();
            while(it.hasNext()){
                Listener temp = it.next();
                if(temp.equals(listener)) {
                    listeners.remove(listener);
                }
            }
        }

        return this;
    }

    public Emitter off(String event){
        ConcurrentLinkedQueue<Listener> listeners = this.callbacks.get(event);
        if(listeners != null){
            listeners.clear();
        }

        return this;
    }

    //off
    public Emitter off(){
        callbacks.clear();
        return this;
    }

    //Emitter#once(event, fn)
    public Emitter once(String event, Listener listener){
        this.off(event, listener);
        this.on(event, listener);
        return this;
    }

    //Emitter#emit(event, ...)
    public Emitter emit(String event, Object...args){
        List<Listener> listeners = this.listeners(event);
        if(listeners!=null){
            for(Listener listener:listeners){
                listener.call(args);
            }
        }

        return this;
    }

    //Emitter#listeners(event)
    public List<Listener> listeners(String event){
        return this.callbacks.get(event)==null?
                null:new ArrayList<>(this.callbacks.get(event));
    }

    //Emitter#hasListeners(event)
    public boolean hasListeners(String event){
        if(this.callbacks.get(event)!=null &&
                !this.callbacks.get(event).isEmpty()){
            return true;
        }
        return false;
    }

    public static void main(String...args){
        Emitter emitter = new Emitter();
        emitter.on("event", new Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("event");
            }
        });
        emitter.on("key", new Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("key1");
            }
        });

        emitter.on("key", new Listener() {
            @Override
            public void call(Object... args) {

            }
        });

        Listener listener = new Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("key11");
            }
        };

        emitter.on("key", listener);

        emitter.off("key", listener);

        emitter.off();

        emitter.once("once", listener);

        emitter.once("once", listener);

        System.out.println();
    }

}
