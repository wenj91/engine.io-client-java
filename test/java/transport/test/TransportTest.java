package transport.test;

import com.github.wenj91.engine.io.transports.base.Option;
import com.github.wenj91.engine.io.transports.transport.WebSocket;
import org.junit.Test;

/**
 * Created by wenj91 on 2016-11-21.
 */
public class TransportTest {

    @Test
    public void webSocketTest(){
        Option option = new Option();
        option.setHost("127.0.0.1");
        option.setPort(3000);
        option.setPath("/engine.io");
        option.getQuery().put("transport", "websocket");
        WebSocket ws = new WebSocket(option);
        System.out.println(ws.uri());
    }
}
