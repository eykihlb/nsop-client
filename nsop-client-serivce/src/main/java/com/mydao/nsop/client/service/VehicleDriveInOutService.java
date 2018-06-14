package com.mydao.nsop.client.service;

import com.google.gson.Gson;
import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.config.InterFaceConfig;
import com.mydao.nsop.client.config.TrafficConfig;
import com.mydao.nsop.client.dao.PayEntryRecMapper;
import com.mydao.nsop.client.dao.PayExitRecMapper;
import com.mydao.nsop.client.domain.entity.PayEntryRec;
import com.mydao.nsop.client.domain.entity.PayExitRec;
import com.mydao.nsop.client.domain.vo.RoadEntryVo;
import com.mydao.nsop.client.domain.vo.RoadExitVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class VehicleDriveInOutService {

    private static final Logger log = LoggerFactory.getLogger(VehicleDriveInOutService.class);
    @Autowired
    private FTPConfig fTPConfig;

    @Autowired
    private InterFaceConfig interFaceConfig;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private TrafficConfig trafficConfig;

    @Autowired
    private OAuth2RestTemplate oAuthRestTemplate;

    @Autowired
    private PayEntryRecMapper payEntryRecMapper;

    @Autowired
    private PayExitRecMapper payExitRecMapper;

    private Gson gson = new Gson();

    /**
     * 驶入
     */
    @Async
    public void driveIn(){
        Timer timer = new Timer(true);
        String url = trafficConfig.getUrl() + interFaceConfig.getEntry();
        System.out.println("驶入："+url);
        timer.schedule(
            new java.util.TimerTask() { public void run() {
                List<PayEntryRec> perList = payEntryRecMapper.selectList();
                RoadEntryVo rev = new RoadEntryVo();
                for (PayEntryRec payEntryRec : perList) {
                    rev.setCameraNo(payEntryRec.getCamerano());
                    rev.setEntryRecId(payEntryRec.getRecid());
                    rev.setTolllaneNo(payEntryRec.getLaneno());
                    rev.setTollnetNo(payEntryRec.getNetno());
                    rev.setTollsiteNo(payEntryRec.getSiteno());
                    rev.setLprPlateNo(payEntryRec.getLprPlateno());
                    rev.setFarePlateNo(payEntryRec.getFarePlateno());
                    rev.setVehcolorId(payEntryRec.getFarePlatecolor());
                    rev.setPassTime(payEntryRec.getEntrytime());
                    rev.setVehclassId(payEntryRec.getVehclass());
                    try {
                        ResponseEntity<Object> getEntity = oAuthRestTemplate.postForEntity(url,rev,Object.class);
                        Map<String,Object> map = gson.fromJson(getEntity.getBody().toString(),Map.class);
                        //List<LinkedTreeMap<String,Object>> list = (List<LinkedTreeMap<String,Object>>)map.get("data");
                        if ("200".equals(map.get("code").toString().substring(0,map.get("code").toString().indexOf(".")))){
                            log.info(map.get("msg").toString());
                            payEntryRecMapper.updateById(payEntryRec.getRecid());
                        } else {
                            log.error(map.get("msg").toString());
                        }
                    }catch (Exception e){
                        log.error(e.getMessage());
                        continue;
                    }
                }
            }},0,Constants.RETRY_TIMES
        );
    }

    /**
     * 驶出
     */
    @Async
    public void driveOut(){
        Timer timer = new Timer(true);
        String url = trafficConfig.getUrl() + interFaceConfig.getExit();
        System.out.println("驶出："+url);
        timer.schedule(
                new java.util.TimerTask() { public void run() {
                    List<PayExitRec> perList = payExitRecMapper.selectList();
                    RoadExitVo rev = new RoadExitVo();
                    for (PayExitRec payExitRec : perList) {
                        rev.setCameraNo(payExitRec.getCamerano());
                        rev.setExitRecId(payExitRec.getRecid());
                        rev.setTolllaneNo(payExitRec.getLaneno());
                        rev.setTollnetNo(payExitRec.getNetno());
                        rev.setTollsiteNo(payExitRec.getSiteno());
                        rev.setLprPlateNo(payExitRec.getLprPlateno());
                        rev.setFarePlateNo(payExitRec.getFarePlateno());
                        rev.setPassTime(payExitRec.getExittime());
                        rev.setVehclassId(payExitRec.getVehclass());
                        rev.setEntryRecId(payExitRec.getEntryRecid());
                        rev.setPayFare(payExitRec.getFaretotal().toString());
                        rev.setVehcolorId(payExitRec.getFarePlatecolor());
                        try {
                            ResponseEntity<Object> getEntity = oAuthRestTemplate.postForEntity(url,rev,Object.class);
                            Map<String,Object> map = gson.fromJson(getEntity.getBody().toString(),Map.class);
                            if ("200".equals(map.get("code").toString().substring(0,map.get("code").toString().indexOf(".")))){
                                log.info(map.get("msg").toString());
                                payExitRecMapper.updateById(payExitRec.getRecid());
                            } else {
                                log.error(map.get("msg").toString());
                            }
                        }catch (Exception e){
                            log.error(e.getMessage());
                            continue;
                        }
                    }
                }},0,Constants.RETRY_TIMES
        );
    }

    /**
     * 获取文件名
     */
    private String getFileName(String msg){
        String fileName = "";
        Map<String,Object> map = gson.fromJson(msg,Map.class);
        //fileName = map.get("key").toString();
        //fileName += map.get("key").toString();
        return fileName;
    }


}
