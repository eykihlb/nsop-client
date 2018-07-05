package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayBlackListMapper;
import com.mydao.nsop.client.dao.PayWhiteListMapper;
import com.mydao.nsop.client.domain.entity.PayBlackList;
import com.mydao.nsop.client.domain.entity.PayWhiteList;
import com.mydao.nsop.client.domain.vo.PageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class SystemInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemInit.class);

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private InterFaceConfig interFaceConfig;

    @Autowired
    private OAuth2RestTemplate oAuthRestTemplate;

    @Autowired
    private PayBlackListMapper payBlackListMapper;

    @Autowired
    private PayWhiteListMapper payWhiteListMapper;

    private Gson gson = new Gson();

    @Async
    public void systemInit(){
        PageVo pv = new PageVo(1,100);
        boolean blackFlag = true;
        boolean whiteFlag = true;
        try {
            String whiteUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
            System.out.println("全量白名单："+ whiteUri);
            String blackUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_black();
            System.out.println("全量黑名单："+ whiteUri);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
            while (blackFlag){
                payBlackListMapper.deleteAll();
                PayBlackList pbl = new PayBlackList();
                ResponseEntity<Object> blackEntity = oAuthRestTemplate.postForEntity(blackUri,pv,Object.class);
                Map<String,Object> map = gson.fromJson(blackEntity.getBody().toString(),Map.class);
                List<LinkedTreeMap<String,Object>> list = (List<LinkedTreeMap<String,Object>>)map.get("data");
                for (LinkedTreeMap<String,Object> red : list) {
                    pbl.setVehclass(new BigDecimal(red.get("vehicleId").toString()).toPlainString());
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(new BigDecimal(red.get("createTime").toString()).toPlainString()))));
                    pbl.setUptime(new Date(sd.replace("-","/")));
                    pbl.setPlateno(red.get("plateno").toString());
                    pbl.setPlatecolor(red.get("platecolor").toString());
                    pbl.setBand(red.get("band").toString());
                    pbl.setVehclass(red.get("vehclass").toString());
                    pbl.setBodycolor(red.get("bodycolor").toString());
                    pbl.setSubBand(red.get("subBand").toString());
                    try{
                        payBlackListMapper.insertSelective(pbl);
                    }catch (Exception e){
                        LOGGER.error("黑名单车牌：" + pbl.getPlateno() + "拉取失败！" + e.getMessage());
                        continue;
                    }
                }
                if (list.size()<pv.getSize()){
                    blackFlag = false;
                }else{
                    pv.setCurrent(pv.getCurrent()+1);
                }
            }
            while (whiteFlag){
                payWhiteListMapper.deleteAll();
                PayWhiteList pwl = new PayWhiteList();
                ResponseEntity<Object> whiteEntity = oAuthRestTemplate.postForEntity(whiteUri,pv,Object.class);
                Map<String,Object> map = gson.fromJson(whiteEntity.getBody().toString(),Map.class);
                List<LinkedTreeMap<String,Object>> list = (List<LinkedTreeMap<String,Object>>)map.get("data");
                for (LinkedTreeMap<String,Object> rad : list) {
                    String sd = sdf.format(new Date(Long.parseLong(String.valueOf(new BigDecimal(rad.get("createTime").toString()).toPlainString()))));
                    pwl.setUptime(new Date(sd.replace("-","/")));
                    pwl.setPlateno(rad.get("plateno").toString());
                    pwl.setPlatecolor(rad.get("platecolor").toString());
                    pwl.setBand(rad.get("band").toString());
                    pwl.setVehclass(rad.get("vehclass").toString());
                    pwl.setBodycolor(rad.get("bodycolor").toString());
                    pwl.setSubBand(rad.get("subBand").toString());
                    try{
                        payWhiteListMapper.insertSelective(pwl);
                    }catch (Exception e){
                        LOGGER.error("白名单车牌：" + pwl.getPlateno()+"拉取失败！" + e.getMessage());
                        continue;
                    }

                }
                if(list.size()<pv.getSize()){
                    whiteFlag = false;
                }else{
                    pv.setCurrent(pv.getCurrent()+1);
                }
            }
        } catch (Exception e) {
            LOGGER.error("全量黑白名单拉取失败！ErrorMsg:"+e.getMessage());
        }
    }
}
