package com.mydao.nsop.client.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 根据需求自定义重试策略
 * @author zhuyanwei
 */
public class ClientServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceUnavailableRetryStrategy.class);

    /**
     * Maximum number of allowed retries if the server responds with a HTTP code
     * in our retry code list. Default value is 1.
     */
    private final int maxRetries;

    /**
     * Retry interval between subsequent requests, in milliseconds. Default
     * value is 1 second.
     */
    private final long retryInterval;

    private String result;

    public ClientServiceUnavailableRetryStrategy(final int maxRetries, final int retryInterval) {
        super();
        Args.positive(maxRetries, "Max retries");
        Args.positive(retryInterval, "Retry interval");
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
    }

    public ClientServiceUnavailableRetryStrategy() {
        this(3, 1000);
    }

    @Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {

        if (executionCount > this.maxRetries) {
            // Do not retry if over max retry count
            return false;
        }

        try {
            StatusLine responseCode = response.getStatusLine();
            // 判断返回状态是否为503服务不可达
            if(responseCode.getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE) {
                LOGGER.error("processHttpCall, responseCode is, {} ",responseCode.getStatusCode());
                return true;
            }

            // 判断返回状态是否为200
            if (responseCode.getStatusCode() != HttpStatus.SC_OK) {
                LOGGER.error("processHttpCall, responseCode is, {} ",responseCode.getStatusCode());
                return true;
            }

            String result = StringUtils.trim(EntityUtils.toString(response.getEntity(), "UTF-8"));
            if(StringUtils.isBlank(result)){
                return true;
            }
            this.result = result;
        } catch (IOException e) {
            LOGGER.error("重试异常，异常消息{}",e.getMessage());
            return true;
        }
        // otherwise do not retry
        return false;
    }

    @Override
    public long getRetryInterval() {
        return retryInterval;
    }

    public String getResult() {
        return result;
    }
}
