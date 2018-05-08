package com.mydao.nsop.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@ConfigurationProperties(prefix = "nsop.traffic")
public class TrafficConfig {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
