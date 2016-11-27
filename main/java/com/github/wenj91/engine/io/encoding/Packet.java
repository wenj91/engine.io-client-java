package com.github.wenj91.engine.io.encoding;

/**
 * Created by wenj91 on 2016-11-17.
 */
public class Packet {
    private String typeId;
    private String data;

    public Packet(String typeId, String data) {
        this.typeId = typeId;
        this.data = data;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
