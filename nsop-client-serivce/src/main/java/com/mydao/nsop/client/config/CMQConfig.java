package com.mydao.nsop.client.config;

import com.qcloud.cmq.Account;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@Configuration
@ConfigurationProperties(prefix = "nsop.cmq")
public class CMQConfig {

    private String secretId;
    private String secretKey;
    private String endpointTopic;
    private String endpointQueue;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndpointTopic() {
        return endpointTopic;
    }

    public void setEndpointTopic(String endpointTopic) {
        this.endpointTopic = endpointTopic;
    }

    public String getEndpointQueue() {
        return endpointQueue;
    }

    public void setEndpointQueue(String endpointQueue) {
        this.endpointQueue = endpointQueue;
    }

    public Account accountTopic() {
        return new Account(endpointTopic,secretId, secretKey);
    }

    public Account accountQueue() {
        return new Account(endpointQueue,secretId, secretKey);
    }




}
