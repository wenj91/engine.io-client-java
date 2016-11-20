package com.github.wenj91.engine.io.transports.transport;

import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;

/**
 * Created by wenj91 on 16-11-19.
 */
public class Flashsocket extends Transport{
    public Flashsocket(Option option) {
        super(option);
    }

    @Override
    public String uri() {
        return null;
    }
}
