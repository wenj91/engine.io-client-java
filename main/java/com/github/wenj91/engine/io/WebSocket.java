package com.github.wenj91.engine.io;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

import java.io.IOException;

/**
 * Created by wenj91 on 2016-11-16.
 */
public class WebSocket implements WebSocketListener{

    private String url;

    public WebSocket(String url){
        this.url = url;
    }

    public void run(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();

        WebSocketCall wsCall = WebSocketCall.create(client, request);
        wsCall.enqueue(this);
    }

    @Override
    public void onOpen(okhttp3.ws.WebSocket webSocket, Response response) {

    }

    @Override
    public void onFailure(IOException e, Response response) {

    }

    @Override
    public void onMessage(ResponseBody responseBody) throws IOException {

    }

    @Override
    public void onPong(Buffer buffer) {

    }

    @Override
    public void onClose(int i, String s) {

    }
}
