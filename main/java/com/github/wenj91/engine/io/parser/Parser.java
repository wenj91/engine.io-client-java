package com.github.wenj91.engine.io.parser;

import com.alibaba.fastjson.JSON;
import com.github.wenj91.engine.io.encoding.HandshakeData;
import com.github.wenj91.engine.io.encoding.Packet;

/**
 * Created by wenj91 on 16-11-19.
 */
public class Parser {
    public static Packet encode2Packet(String data){
        Packet packet = new Packet(String.valueOf(data.charAt(0)), data.substring(1));
        return packet;
    }

    public static String decode2String(Packet packet){
        return packet.getTypeId() + packet.getData();
    }

    public static HandshakeData encode2HandshakeData(String json){
        return JSON.toJavaObject((JSON) JSON.parse(json), HandshakeData.class);
    }

    public static void main(String...args){
        HandshakeData hd = encode2HandshakeData("{\"sid\":\"FQgdlDaCPlaqgwRNAAAD\",\"upgrades\":[],\"pingInterval\":25000,\"pingTimeout\":60000}");
        System.out.println();
    }
}
