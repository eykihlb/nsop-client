package com.mydao.nsop.client.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.nio.charset.StandardCharsets;

/**
 * @author ZYW
 * @date 2018/5/31
 */
@Configuration
@EnableOAuth2Client
@ConfigurationProperties(prefix = "security.oauth2.client")
public class OAuth2RestTemplateConfig {

    private String clientId;
    private String clientSecret;
    private String accessTokenUri;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    @Bean
    public OAuth2RestTemplate oAuthRestTemplate(CloseableHttpClient httpClient) {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setId("1");
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setAccessTokenUri(accessTokenUri);

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //替换默认http客户的（默认是SimpleClientHttpRequestFactory使用JDK的java.net包创建客户的）
        //替换为使用Apache Client的客户的并增加重试机制
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // 连接超时
        requestFactory.setConnectTimeout(5000);
        // 数据读取超时时间，即SocketTimeout
        requestFactory.setReadTimeout(5000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        requestFactory.setConnectionRequestTimeout(200);

        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}
