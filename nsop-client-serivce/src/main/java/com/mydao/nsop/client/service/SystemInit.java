package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.util.HttpClientUtil;
import com.rabbitmq.client.Channel;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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

    private Gson gson = new Gson();

    @Async
    public void systemInit(){
        Message blackMsg = null, whiteMsg = null;
        try {
            System.out.println("请求全量黑白名单");
            List<NameValuePair> list = new ArrayList<>();
            //list.add(new BasicNameValuePair(Constants.INTERFACE_PARAM, new String("init")));
            String blackUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
            String whiteUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_black();
            String blackResult = HttpClientUtil.sendHttpPostCall(blackUri,list);
            String whiteResult = HttpClientUtil.sendHttpPostCall(whiteUri,list);
            Map<String,Object> blackMap = gson.fromJson(blackResult,Map.class);
            Map<String,Object> whiteMap = gson.fromJson(whiteResult,Map.class);
            String black = gson.toJson(blackMap.get("data"));
            String white = gson.toJson(whiteMap.get("data"));
            MessageProperties mp = new MessageProperties();
            blackMsg = new Message(black.getBytes(),mp);
            whiteMsg = new Message(white.getBytes(),mp);
        } catch (Exception e) {
            log.error("全量黑白名单拉取失败！");
        }
        try {
            rabbitTemplate.send(Constants.FULL_BLACK_LIST,blackMsg);
            rabbitTemplate.send(Constants.FULL_WHITE_LIST,whiteMsg);
        }catch (Exception e){
            log.error("全量黑白名单初始化失败！");
        }
    }

    public void aaa(){
        ResponseEntity<String> entity = oAuthRestTemplate.getForEntity("http://127.0.0.1:9090/pay/secretfree/contract/param", String.class);
        System.out.println(entity.getBody());
    }
}
