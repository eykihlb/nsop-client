package com.mydao.nsop.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Configuration
@ConfigurationProperties(prefix = "nsop.traffic")
public class TrafficConfig {

    private String url;

    private String clientNum;

    private String qqDns;

    public String getQqDns() {
        return qqDns;
    }

    public void setQqDns(String qqDns) {
        this.qqDns = qqDns;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }
}
