package com.mydao.nsop.client.config;

import com.qcloud.cmq.Account;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author ZYW
 * @date 2018/5/8
 */
@ConfigurationProperties(prefix = "nsop.cmq")
public class CMQConfig {

    private String secretId;
    private String secretKey;
    private String endpoint;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Bean
    public Account account() {
        return new Account(endpoint,secretId, secretKey);
    }
}
