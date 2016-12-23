package com.github.wenj91.engine.io.transports.transport;

import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.parser.Parser;
import com.github.wenj91.engine.io.thread.EventThread;
import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;
import com.github.wenj91.engine.io.util.QueryUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;
import okio.ByteString;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wenj91 on 16-11-19.
 */
public class WebSocket extends Transport{

    private okhttp3.WebSocket webSocket;

    public WebSocket(Option option) {
        super(option);
    }

    @Override
    protected void sendPacket(Packet...packets) {
        for(Packet packet : packets){
            webSocket.send(Parser.decode2String(packet));
        }
    }

    @Override
    protected void doOpen() {
        System.out.println("Do Open!!!");

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        String url = this.uri();
        Request request = new Request.Builder()
                .url(this.uri())
                .build();

        WebSocket self = this;

        OkHttpClient client = clientBuilder.build();
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                self.webSocket = webSocket;

                System.out.println("On Open!!!");

                try {
                    System.out.println(response.body()==null?
                            "NULL":response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<String, List<String>> headers = response.headers().toMultimap();
                EventThread.execTask(new Runnable() {
                    @Override
                    public void run() {
                        self.onOpen();
                    }
                });
            }

            @Override
            public void onMessage(okhttp3.WebSocket webSocket, String text) {
                onMsg(text);
            }

            @Override
            public void onMessage(okhttp3.WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(okhttp3.WebSocket webSocket, int code, String reason) {
                self.close();
            }

            @Override
            public void onFailure(okhttp3.WebSocket webSocket, Throwable t, Response response) {
                self.onError("1000", "webSocket connect failure!");
            }
        });
    }

    @Override
    protected void doClose() {
        webSocket.close(1000, "");

        if(webSocket!=null){
            webSocket.cancel();
        }
    }

    @Override
    public String uri() {
        StringBuffer sb = new StringBuffer();
        sb.append("ws://").append(this.getOption().getHost());
        if(this.getOption().getPort()!= null){
            sb.append(":").append(this.getOption().getPort());
        }
        sb.append(this.getOption().getPath());
        if(this.getOption().getQuery().size()>0){
            sb.append("?").append(QueryUtil.map2Query(
                    this.getOption().getQuery()));
        }

        return sb.toString();
    }
}
