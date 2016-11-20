package com.github.wenj91.engine.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wenj91.engine.io.encoding.Packet;
import com.github.wenj91.engine.io.enums.PacketType;
import okhttp3.*;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenj91 on 2016-11-16.
 */
public class EngineIOClient implements WebSocketListener{

    private String url;
    private WebSocket webSocket;

    private Map<String, Object> query = new HashMap<>();

    public EngineIOClient(String url){
        this.url = url;
    }

    public void build(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.uri()).build();

        WebSocketCall wsCall = WebSocketCall.create(client, request);
        wsCall.enqueue(this);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        this.webSocket = webSocket;
    }

    @Override
    public void onFailure(IOException e, Response response) {

    }

    @Override
    public void onMessage(ResponseBody responseBody) throws IOException {
        //在此监听服务端返回消息
        //消息分发
        if(responseBody.contentType() == WebSocket.TEXT){
            String res = responseBody.string();
            char type = res.charAt(0);
            switch (type){
                case '0':
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Get The Open Message, Client Ping Interval!");
                            JSONObject jo = JSON.parseObject(res.substring(1));
                            Packet packet = new Packet(PacketType.PING, "ping");

                        }
                    }).start();
                    break;
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
                case '4':
                    break;
                case '5':
                    break;
                case '6':
                    break;
                default:
                    break;
            }
        }else if(responseBody.contentType() == WebSocket.BINARY){

        }else{

        }
    }

    @Override
    public void onPong(Buffer buffer) {

    }

    @Override
    public void onClose(int i, String s) {
        //处理断开连接事件
    }

    public String uri(){
        Map<String, Object> query = this.query;
        StringBuffer sb = new StringBuffer(url);
        sb.append("/engine.io/?transport=websocket")
                .append("&t=").append(new Date().getTime());

        return sb.toString();
    }

    public static void main(String...args){
        EngineIOClient client = new EngineIOClient("ws://127.0.0.1:3000");
        client.build();
    }
}
