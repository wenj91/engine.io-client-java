package com.github.wenj91.engine.io.encoding;

/**
 * Created by wenj91 on 2016-11-17.\
 * Encoding payloads should not be used for WebSocket,
 * as the protocol already has a lightweight framing mechanism.
 */
public class Payload {
    private Integer length;
    private Packet packet;

    public Payload(Integer length, Packet packet) {
        this.length = length;
        this.packet = packet;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
