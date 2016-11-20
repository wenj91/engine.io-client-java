package com.github.wenj91.engine.io.transports.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenj91 on 16-11-19.
 */
public class Option {
    private String host;
    private Integer port;
    private String path;
    private Map<String, Object> query = new HashMap<>();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }
}
