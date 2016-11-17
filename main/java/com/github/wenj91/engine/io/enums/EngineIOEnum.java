package com.github.wenj91.engine.io.enums;

/**
 * Created by wenj91 on 2016-11-17.
 */
public enum EngineIOEnum {
    OPEN("0"), CLOSE("1"), PING("2"), PONG("3"), MESSAGE("4");

    private String value;

    EngineIOEnum(String value){
        this.value = value;
    }

    public String getValue() {
       return value;
    }
}
