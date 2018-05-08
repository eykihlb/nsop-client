package com.mydao.nsop.client.util;

import com.mydao.nsop.client.config.ClientServiceUnavailableRetryStrategy;
import com.mydao.nsop.client.exception.CallRemoteAPIException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * @author ZYW
 */
@Component
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private static CloseableHttpClient httpClient;

    private static ClientServiceUnavailableRetryStrategy retryStrategy;

    @Autowired
    public void setHttpClient(CloseableHttpClient httpClient){
        HttpClientUtil.httpClient = httpClient;
    }

    @Autowired
    public void setRetryStrategy(ClientServiceUnavailableRetryStrategy retryStrategy){
        HttpClientUtil.retryStrategy = retryStrategy;
    }

    private static RequestConfig requestConfig;

    @Autowired
    public void setRequestConfig(RequestConfig requestConfig){
        HttpClientUtil.requestConfig = requestConfig;
    }

    /**
     * 只处理http返回值为utf8文本类型的调用
     * @param builder
     * @return
     * @throws CallRemoteAPIException
     */
    public static String sendHttpGetCall(URIBuilder builder) {
        try {
            URI uri = builder.build();
            HttpGet httpget = new HttpGet(uri);

            return requestRemote(httpget);
        } catch (Exception e) {
            e.printStackTrace();
            String message = StringUtils.isBlank(e.getMessage()) ? e.getCause().getMessage() : e.getMessage();
            LOGGER.error("processHttpCall failed:{}, {} ", builder, message);
            throw new CallRemoteAPIException(message,e);
        }
    }

    /**
     * 处理http Post请求
     * @param url post url
     * @param nvps post参数
     * @return
     */
    public static String sendHttpPostCall(String url, List<NameValuePair> nvps ) {
        URIBuilder builder = null;
        try {
            builder = new URIBuilder( url );
            for(NameValuePair param : nvps ){
                builder.setParameter( param.getName(), param.getValue() );
            }

            HttpPost httpPost = new HttpPost(builder.build());

            return requestRemote(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
            String message = StringUtils.isBlank(e.getMessage()) ? e.getCause().getMessage() : e.getMessage();
            LOGGER.error("processHttpCall failed:{}, {} ", builder, message);
            throw new CallRemoteAPIException(message,e);
        }
    }

    private static String requestRemote(HttpRequestBase httpMethod) throws IOException {
        CloseableHttpResponse response = null;

        try {
            httpMethod.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpMethod);

            StatusLine responseCode = response.getStatusLine();
            // 判断返回状态是否为200
            if (responseCode.getStatusCode() != HttpStatus.SC_OK) {
                LOGGER.error("processHttpCall failed: uri:{}, responseCode is, {} ", httpMethod.getURI(), responseCode.getStatusCode());
                throw new CallRemoteAPIException("responseCode:" + responseCode);
            }
            // 获取服务端返回的数据,并返回
            String result = retryStrategy.getResult();

            if(StringUtils.isBlank(result)) {
                result = StringUtils.trim(EntityUtils.toString(response.getEntity(), "UTF-8"));
            }

            if(StringUtils.isBlank(result)){
                LOGGER.error("processHttpCall: no response, {} ", httpMethod.getURI());
                return "";
            }

            return result;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            String message = e.getMessage() == null ? e.getCause().getMessage() : e.getMessage();
            LOGGER.error("error message:{} ", message);
            throw new CallRemoteAPIException(message);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
