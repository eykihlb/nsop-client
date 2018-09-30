package com.mydao.nsop.client.service;

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
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;

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
    //@Autowired
    //private SqlSessionTemplate sqlSessionTemplate;
    @Async
    public void systemInitBlack(CountDownLatch countDownLatch){
        PageVo pv = new PageVo(1,1000);
        boolean blackFlag = false;
        boolean flag;
        //SqlSession sqlSession =null;
        try {
            String blackUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_black();
            LOGGER.info("全量黑名单：" + blackUri + "    开始时间 " + System.currentTimeMillis());
            if (trafficConfig.getBlackOn()) {
                //获取sqlSession
                //sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                blackFlag = true;
                payBlackListMapper.deleteAll();
            } else {
                blackFlag = false;
            }
            while (blackFlag) {
                flag=true;
                LOGGER.info("请求黑名单数据第："+pv.getCurrent()+"页");
                PayBlackList pbl = new PayBlackList();
                Object data = new Object();
                List<LinkedHashMap<String,Object>> list = new ArrayList<>();
                try {
                    ResponseEntity<Map> blackEntity = oAuthRestTemplate.postForEntity(blackUri, pv, Map.class);
                    Map<String, Object> map = blackEntity.getBody();
                    Integer code = Integer.valueOf(Objects.toString(map.get("code"), "0"));
                    if (code != 200) {
                        LOGGER.error("全量黑名单请求失败，继续请求。。。。");
                        flag = false;
                       // break;
                    }
                    data = map.get("data");
                    if (data == null) {
                        LOGGER.warn("没有全量黑名单！");
                        break;
                    }
                    //PayBlackListMapper blackMapper = sqlSession.getMapper(PayBlackListMapper.class);
                    list = (List<LinkedHashMap<String, Object>>) data;
                    for (LinkedHashMap<String, Object> red : list) {
                        pbl.setVehclass(Objects.toString(red.get("vehclass"), ""));
                        pbl.setUptime(DateTime.parse(Objects.toString(red.get("uptime"), ""), DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss")).toDate());
                        pbl.setPlateno(Objects.toString(red.get("plateno"), ""));
                        pbl.setPlatecolor(Objects.toString(red.get("platecolor")));
                        pbl.setBand(Objects.toString(red.get("band"), ""));
                        pbl.setVehclass(Objects.toString(red.get("vehclass"), ""));
                        pbl.setBodycolor(Objects.toString(red.get("bodycolor"), ""));
                        pbl.setSubBand(Objects.toString(red.get("subBand"), ""));
                        payBlackListMapper.insertSelective(pbl);
                    }
                    //sqlSession.commit();
                } catch (Exception e) {
                    flag = false;
                    LOGGER.error("黑名单拉取失败！ErrorMsg:" + e.getMessage());
                }
                if (list.size() < pv.getSize()&&flag) {
                    blackFlag = false;
                } else {
                    if (flag)
                        pv.setCurrent(pv.getCurrent() + 1);
                }
            }
            LOGGER.info("全量黑名单：" + blackUri + "    结束时间 " + System.currentTimeMillis());
        }catch (Exception e){
            LOGGER.error("黑名单拉取失败！ErrorMsg:" + e.getMessage());
        }finally {
            countDownLatch.countDown();
        }

    }
    @Async
    public void systemInitWhite(CountDownLatch countDownLatch){
        PageVo pv = new PageVo(1,1000);
        boolean whiteFlag = false;
        boolean flag ;
        //SqlSession sqlSession =null;
        try {
            String whiteUri = trafficConfig.getUrl() + interFaceConfig.getFull_quantity_white();
            LOGGER.info("全量白名单："+ whiteUri+"    开始时间 "+System.currentTimeMillis());
            if(trafficConfig.getWhiteOn()){
                //获取sqlSession
               // sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                whiteFlag =true;
                payWhiteListMapper.deleteAll();
            }else {
                whiteFlag =false;
            }
            while (whiteFlag){
                flag=true;
                LOGGER.info("请求白名单数据第："+pv.getCurrent()+"页");
                PayWhiteList pwl = new PayWhiteList();
                Object data = new Object();
                List<LinkedHashMap<String,Object>> list = new ArrayList<>();
                try {
                    ResponseEntity<Map> whiteEntity = oAuthRestTemplate.postForEntity(whiteUri,pv,Map.class);
                    Map<String,Object> map = whiteEntity.getBody();
                    Integer code = Integer.valueOf(Objects.toString(map.get("code"), "0"));
                    if(code != 200) {
                        LOGGER.error("全量白名单拉取失败！");
                        flag = false;
                    }
                    data = map.get("data");
                    if(data == null) {
                        LOGGER.warn("没有全量白名单！");
                        break;
                    }
                    //PayWhiteListMapper whiteMapper = sqlSession.getMapper(PayWhiteListMapper.class);
                         list = (List<LinkedHashMap<String,Object>>)data;
                    for (LinkedHashMap<String,Object> rad : list) {
                        pwl.setUptime(DateTime.parse(Objects.toString(rad.get("uptime"),""), DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss")).toDate());
                        pwl.setPlateno(Objects.toString(rad.get("plateno"),""));
                        pwl.setPlatecolor(Objects.toString(rad.get("platecolor"),""));
                        pwl.setBand(Objects.toString(rad.get("band"),""));
                        pwl.setVehclass(Objects.toString(rad.get("vehclass"),""));
                        pwl.setBodycolor(Objects.toString(rad.get("bodycolor"),""));
                        pwl.setSubBand(Objects.toString(rad.get("subBand"),""));
                        payWhiteListMapper.insertSelective(pwl);
                    }
                    //sqlSession.commit();
                }catch (Exception e){
                    LOGGER.error("白名单拉取失败！ErrorMsg:"+e.getMessage());
                    flag = false;
                }
                if(list.size()<pv.getSize()&&flag){
                    whiteFlag = false;
                }else{
                    if(flag)
                        pv.setCurrent(pv.getCurrent()+1);
                }
            }
            LOGGER.info("全量白名单："+ whiteUri+"    结束时间 "+System.currentTimeMillis());
        } catch (Exception e) {
            LOGGER.error("白名单拉取失败！ErrorMsg:"+e.getMessage());
        }finally {
            countDownLatch.countDown();
          }
        }
}
