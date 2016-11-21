package com.github.wenj91.engine.io.enums;

/**
 * Created by wenj91 on 2016-11-21.
 */
public enum EmitterEventType {
    onError("onError"),
    onOpen("onOpen"),
    onPacket("onPacket"),
    onClose("onClose"),
    responseHeaders("responseHeaders");
    String event;
    EmitterEventType(String event){
        this.event = event;
    }

    public String getEvent() {
        return event;
    }
}
