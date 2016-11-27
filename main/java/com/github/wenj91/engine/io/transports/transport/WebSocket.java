package com.github.wenj91.engine.io.transports.transport;

import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.parser.Parser;
import com.github.wenj91.engine.io.transports.Transport;
import com.github.wenj91.engine.io.transports.base.Option;
import com.github.wenj91.engine.io.util.QueryUtil;
import okhttp3.*;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static okhttp3.ws.WebSocket.TEXT;

/**
 * Created by wenj91 on 16-11-19.
 * practice: copy from https://github.com/socketio/engine.io-client-java
 */
public class WebSocket extends Transport implements WebSocketListener{

    private okhttp3.ws.WebSocket webSocket;
    WebSocketCall call;

    Executor executor;

    public WebSocket(Option option) {
        super(option);
    }

    @Override
    protected void sendPacket(Packet...packets) {
        try {
            for(Packet packet : packets){
                webSocket.sendMessage(RequestBody.create(TEXT,
                        Parser.decode2String(packet)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOpen() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .writeTimeout(0, TimeUnit.MILLISECONDS);//see  https://github.com/socketio/engine.io-client-java/issues/32

        String url = this.uri();
        Request request = new Request.Builder()
                .url(this.uri())
                .build();

        OkHttpClient client = clientBuilder.build();

        call = WebSocketCall.create(client, request);
        call.enqueue(this);
        System.out.println("Do Open!!!");
    }

    @Override
    protected void doClose() {
        try {
            webSocket.close(1000, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(call!=null){
            call.cancel();
        }
    }

    @Override
    public void onOpen(okhttp3.ws.WebSocket webSocket, Response response) {
        this.webSocket = webSocket;

        System.out.println("On Open!!!");

        try {
            System.out.println(response.body()==null?
                    "NULL":response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<String>> headers = response.headers().toMultimap();
        if(executor==null){
            executor = Executors.newSingleThreadExecutor();
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                onOpen();
            }
        });

    }

    @Override
    public void onFailure(IOException e, Response response) {
        onError("1000", "webSocket connect failure!");
    }

    @Override
    public void onMessage(ResponseBody message) throws IOException {

        if(message.contentType() == TEXT){
            onMsg(message.string());
        }
        message.close();//@see https://github.com/square/okhttp/issues/2303
    }

    @Override
    public void onPong(Buffer payload) {

    }

    @Override
    public void onClose(int code, String reason) {
        System.out.println("On Close!!!");
        close();
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
