package com.github.wenj91.engine.io.transports.transport;

import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;

/**
 * Created by wenj91 on 16-11-19.
 */
public class Polling extends Transport{
    public Polling(Option option) {
        super(option);
    }

    @Override
    public String uri() {
        return null;
    }

    @Override
    public void sendPacket(Packet... packets) {

    }

    @Override
    public void doOpen() {

    }

    @Override
    public void doClose() {

    }
}
