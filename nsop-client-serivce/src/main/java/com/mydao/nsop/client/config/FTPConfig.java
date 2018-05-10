package com.mydao.nsop.client.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "nsop.ftpServer")
public class FTPConfig {

    private Map<String,Object> download;
    private Map<String,Object> upload;

    public Map<String, Object> getDownload() {
        return download;
    }

    public void setDownload(Map<String, Object> download) {
        this.download = download;
    }

    public Map<String, Object> getUpload() {
        return upload;
    }

    public void setUpload(Map<String, Object> upload) {
        this.upload = upload;
    }
}
