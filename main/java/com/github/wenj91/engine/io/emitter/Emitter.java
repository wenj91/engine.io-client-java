package com.github.wenj91.engine.io.emitter;

import com.github.wenj91.engine.io.emitter.listener.Listener;

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

    /**
     * Emitter#on(event, fn)
     * @param event
     * @param listener
     * @return
     */
    public Emitter on(String event, Listener listener){
        ConcurrentLinkedQueue<Listener> listeners
                = this.callbacks.get(event);
        if(listeners == null){
            listeners = new ConcurrentLinkedQueue<>();
            ConcurrentLinkedQueue<Listener> temp
                    = this.callbacks.putIfAbsent(event, listeners);
            if(temp!=null){
                listeners = temp;
            }
        }
        listeners.add(listener);

        return this;
    }

    /**
     * Emitter#off(event, fn)
     * @param event
     * @param listener
     * @return
     */
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
        this.callbacks.remove(event);
        return this;
    }

    /**
     * off
     * @return
     */
    public Emitter off(){
        callbacks.clear();
        return this;
    }

    /**
     * Emitter#once(event, fn)
     * @param event
     * @param listener
     * @return
     */
    public Emitter once(String event, Listener listener){
        this.off(event, listener);
        this.on(event, listener);
        return this;
    }

    /**
     * Emitter#emit(event, ...)
     * @param event
     * @param args
     * @return
     */
    public Emitter emit(String event, Object...args){
        List<Listener> listeners = this.listeners(event);
        if(listeners!=null){
            for(Listener listener:listeners){
                listener.call(args);
            }
        }

        return this;
    }

    /**
     * Emitter#listeners(event)
     * @param event
     * @return
     */
    public List<Listener> listeners(String event){
        return this.callbacks.get(event)==null?
                null:new ArrayList<>(this.callbacks.get(event));
    }

    /**
     * Emitter#hasListeners(event)
     * @param event
     * @return
     */
    public boolean hasListeners(String event){
        if(this.callbacks.get(event)!=null &&
                !this.callbacks.get(event).isEmpty()){
            return true;
        }
        return false;
    }

}
