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
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private Gson gson = new Gson();

    @Async
    public void systemInitBlack(){
        PageVo pv = new PageVo(1,1500);
        boolean blackFlag = false;
        boolean whiteFlag = false;
        SqlSession sqlSession =null;
            String blackUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_black();
            LOGGER.info("全量黑名单：" + blackUri + "    开始时间 " + System.currentTimeMillis());
            if (trafficConfig.getBlackOn()) {
                //获取sqlSession
                sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                blackFlag = true;
                payBlackListMapper.deleteAll();
            } else {
                blackFlag = false;
            }
            while (blackFlag) {
                PayBlackList pbl = new PayBlackList();
                ResponseEntity<Map> blackEntity = oAuthRestTemplate.postForEntity(blackUri, pv, Map.class);
                Map<String, Object> map = blackEntity.getBody();
                Integer code = Integer.valueOf(Objects.toString(map.get("code"), "0"));
                if (code != 200) {
                    LOGGER.error("全量黑名单拉取失败！");
                    break;
                }
                Object data = map.get("data");
                if (data == null) {
                    LOGGER.warn("没有全量黑名单！");
                    break;
                }
                PayBlackListMapper blackMapper = sqlSession.getMapper(PayBlackListMapper.class);
                List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) data;
                try{
                    for (LinkedHashMap<String, Object> red : list) {
                        pbl.setVehclass(Objects.toString(red.get("vehclass"), ""));
                        pbl.setUptime(DateTime.parse(Objects.toString(red.get("uptime"), ""), DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss")).toDate());
                        pbl.setPlateno(red.get("plateno").toString());
                        pbl.setPlatecolor(red.get("platecolor").toString());
                        pbl.setBand(red.get("band").toString());
                        pbl.setVehclass(red.get("vehclass").toString());
                        pbl.setBodycolor(red.get("bodycolor").toString());
                        pbl.setSubBand(red.get("subBand").toString());
                        blackMapper.insertSelective(pbl);
                    }
                    sqlSession.commit();
                }catch (Exception e){
                    sqlSession.rollback();
                }
                if (list.size() < pv.getSize()) {
                    blackFlag = false;
                    if (null != sqlSession) {
                        sqlSession.close();
                    }
                } else {
                    pv.setCurrent(pv.getCurrent() + 1);
                }
            }
            LOGGER.info("全量黑名单：" + blackUri + "    结束时间 " + System.currentTimeMillis());
    }
    @Async
    public void systemInitWhite(){
        PageVo pv = new PageVo(1,1500);
        boolean whiteFlag = false;
        SqlSession sqlSession =null;
        try {
            String whiteUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
            LOGGER.info("全量白名单："+ whiteUri+"    开始时间 "+System.currentTimeMillis());
            if(trafficConfig.getWhiteOn()){
                //获取sqlSession
                sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                whiteFlag =true;
                payWhiteListMapper.deleteAll();
            }else {
                whiteFlag =false;
            }
            while (whiteFlag){
                PayWhiteList pwl = new PayWhiteList();
                ResponseEntity<Map> whiteEntity = oAuthRestTemplate.postForEntity(whiteUri,pv,Map.class);
                Map<String,Object> map = whiteEntity.getBody();
                Integer code = Integer.valueOf(Objects.toString(map.get("code"), "0"));
                if(code != 200) {
                    LOGGER.error("全量白名单拉取失败！");
                    break;
                }
                Object data = map.get("data");
                if(data == null) {
                    LOGGER.warn("没有全量白名单！");
                    break;
                }
                PayWhiteListMapper whiteMapper = sqlSession.getMapper(PayWhiteListMapper.class);
                List<LinkedHashMap<String,Object>> list = (List<LinkedHashMap<String,Object>>)data;
                for (LinkedHashMap<String,Object> rad : list) {
                    pwl.setUptime(DateTime.parse(Objects.toString(rad.get("uptime"),""), DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss")).toDate());
                    pwl.setPlateno(rad.get("plateno").toString());
                    pwl.setPlatecolor(rad.get("platecolor").toString());
                    pwl.setBand(rad.get("band").toString());
                    pwl.setVehclass(rad.get("vehclass").toString());
                    pwl.setBodycolor(rad.get("bodycolor").toString());
                    pwl.setSubBand(rad.get("subBand").toString());
                    whiteMapper.insertSelective(pwl);
                }
                sqlSession.commit();
                if(list.size()<pv.getSize()){
                    whiteFlag = false;
                    if (null != sqlSession) {
                        sqlSession.close();
                    }
                }else{
                    pv.setCurrent(pv.getCurrent()+1);
                }
            }
            LOGGER.info("全量白名单："+ whiteUri+"    结束时间 "+System.currentTimeMillis());
        } catch (Exception e) {
            LOGGER.error("白名单拉取失败！ErrorMsg:"+e.getMessage());
        }
    }
}
