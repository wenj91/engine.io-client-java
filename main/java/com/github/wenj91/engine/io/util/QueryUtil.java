package com.github.wenj91.engine.io.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by wenj91 on 16-11-19.
 */
public class QueryUtil {

    public static String map2Query(Map<String, Object> queryMap){
        StringBuffer sb = new StringBuffer();

        Set<String> keySet = queryMap.keySet();

        for(String key : keySet){
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(key).append("=").append(queryMap.get(key));
        }

        return sb.toString();
    }

    public static void main(String...args){
        Map<String, Object> query = new HashMap();
        query.put("key1", "keyss");
        query.put("key2", "test");

        System.out.println(map2Query(query));
    }
}
