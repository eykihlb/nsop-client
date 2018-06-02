package com.mydao.nsop.client.config;

import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.apache.http.client.config.RequestConfig.Builder;

@Configuration
public class HttpClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConfig.class);

    /**
     * 创建重试机制类
     * @return
     */
    @Bean
    public ClientServiceUnavailableRetryStrategy retryStrategy() {
        return new ClientServiceUnavailableRetryStrategy();
    }

    /**
     * 创建httpClient
     * @return
     */
    @Bean
    public CloseableHttpClient httpClient() {

        //创建连接池
        PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
        phccm.setMaxTotal(200);
        phccm.setDefaultMaxPerRoute(200);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置连接池
        httpClientBuilder.setConnectionManager(phccm);
        //设置重试机制(自定义重试策略)
        //httpClientBuilder.setServiceUnavailableRetryStrategy(retryStrategy());
        //设置重试机制（根据异常设置重试策略）
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3,true));
        //创建request配置类
        httpClientBuilder.setDefaultRequestConfig(requestConfig());
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        //设置默认header
        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));

        httpClientBuilder.setDefaultHeaders(headers);

        return httpClientBuilder.build();
    }

    /**
     * 创建request配置对象
     * @return
     */
    @Bean
    public RequestConfig requestConfig() {
        Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(5000);
        requestConfigBuilder.setConnectionRequestTimeout(1000);
        requestConfigBuilder.setSocketTimeout(5000);

        return requestConfigBuilder.build();
    }
}
