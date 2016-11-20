package com.github.wenj91.engine.io.encoding;

import com.github.wenj91.engine.io.enums.PacketType;

/**
 * Created by wenj91 on 2016-11-17.
 */
public class Packet {
    private String typeId;
    private String data;

    public Packet(PacketType typeId, String data) {
        this.typeId = typeId.getValue();
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
