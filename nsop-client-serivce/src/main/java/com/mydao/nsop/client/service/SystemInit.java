package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayBlackListMapper;
import com.mydao.nsop.client.dao.PayWhiteListMapper;
import com.mydao.nsop.client.domain.entity.PayBlackList;
import com.mydao.nsop.client.domain.entity.PayWhiteList;
import com.mydao.nsop.client.util.HttpClientUtil;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SystemInit {

    private static final Logger log = LoggerFactory.getLogger(SystemInit.class);

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private InterFaceConfig interFaceConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OAuth2RestTemplate oAuthRestTemplate;

    @Autowired
    private PayBlackListMapper payBlackListMapper;

    @Autowired
    private PayWhiteListMapper payWhiteListMapper;

    private Gson gson = new Gson();

    @Async
    public void systemInit(){
        try {
            System.out.println("请求全量黑白名单");
            String blackUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
            String whiteUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_black();
            ResponseEntity<PayBlackList> whiteEntity = oAuthRestTemplate.postForEntity(blackUri,new Object(),PayBlackList.class);
            payBlackListMapper.insertSelective(whiteEntity.getBody());
            ResponseEntity<PayWhiteList> blackEntity = oAuthRestTemplate.postForEntity(whiteUri,new Object(),PayWhiteList.class);
            payWhiteListMapper.insertSelective(blackEntity.getBody());
        } catch (Exception e) {
            log.error("全量黑白名单拉取失败！");
        }
    }

    public void restTemplateTest(){
        //Get方式
        ResponseEntity<String> getEntity = oAuthRestTemplate.getForEntity("http://127.0.0.1:9090/pay/secretfree/contract/param", String.class);
        //Post方式，第二个参数为对象，第三个参数为返回类型
        ResponseEntity<Object> postEntity = oAuthRestTemplate.postForEntity("http://127.0.0.1:9090/pay/secretfree/contract/param",new Object(),Object.class);
        System.out.println(getEntity.getBody());
    }
}
