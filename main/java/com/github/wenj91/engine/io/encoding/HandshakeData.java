package com.github.wenj91.engine.io.encoding;

/**
 * Created by wenj91 on 16-11-28.
 */
public class HandshakeData {
    private String sid;
    private String upgrades;
    private Integer pingInterval;
    private Integer pingTimeout;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(String upgrades) {
        this.upgrades = upgrades;
    }

    public Integer getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(Integer pingInterval) {
        this.pingInterval = pingInterval;
    }

    public Integer getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(Integer pingTimeout) {
        this.pingTimeout = pingTimeout;
    }
}
