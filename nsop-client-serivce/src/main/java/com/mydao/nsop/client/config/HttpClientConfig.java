package com.mydao.nsop.client.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.http.client.config.RequestConfig.Builder;

@Configuration
public class HttpClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConfig.class);

    @Bean(destroyMethod = "shutdown")
    public PoolingHttpClientConnectionManager httpClientConnectionManager() {

        PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
        phccm.setMaxTotal(200);
        phccm.setDefaultMaxPerRoute(200);
        return phccm;
    }

    @Bean
    public ClientServiceUnavailableRetryStrategy retryStrategy() {
        return new ClientServiceUnavailableRetryStrategy();
    }

    @Bean
    public CloseableHttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager());

        httpClientBuilder.setServiceUnavailableRetryStrategy(retryStrategy());
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig requestConfig() {
        Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(5000);
        requestConfigBuilder.setConnectionRequestTimeout(1000);
        requestConfigBuilder.setSocketTimeout(5000);

        return requestConfigBuilder.build();
    }

    @Bean(destroyMethod = "shutdown")
    public IdleConnectionEvictor idleConnectionEvictor() {
        return new IdleConnectionEvictor(httpClientConnectionManager(), 60000);
    }
}
