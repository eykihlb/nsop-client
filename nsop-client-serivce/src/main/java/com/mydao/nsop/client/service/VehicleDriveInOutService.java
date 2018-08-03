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
import com.mydao.nsop.client.util.ThreadPoolFtp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/5/7
 */
@Component
public class VehicleDriveInOutService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveInOutService.class);
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
    @Scheduled(fixedRate = Constants.RETRY_TIMES)
    public void driveIn(){
        LOGGER.info("车辆驶入定时任务");
        final String entryUrl = trafficConfig.getUrl() + interFaceConfig.getEntry();
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
            rev.setFileId(payEntryRec.getRecid()+".jpg");
            ThreadPoolFtp.ftpThreadPool().execute(new FtpThread(fTPConfig,rev.getFileId()));
            //fileUploadService.fileUpload(fTPConfig,rev.getFileId());
            try {
                ResponseEntity<Object> getEntity = oAuthRestTemplate.postForEntity(entryUrl,rev,Object.class);
                Map<String,Object> map = gson.fromJson(getEntity.getBody().toString(),Map.class);
                if ("200".equals(map.get("code").toString().substring(0,map.get("code").toString().indexOf(".")))){
                    LOGGER.info(map.get("msg").toString());
                    payEntryRecMapper.updateById(payEntryRec.getRecid());
                } else {
                    LOGGER.error(map.get("msg").toString());
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * 驶出
     */
    @Scheduled(fixedRate = Constants.RETRY_TIMES)
    public void driveOut(){
        LOGGER.info("车辆驶出定时任务");
        final String exitUrl = trafficConfig.getUrl() + interFaceConfig.getExit();
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
            rev.setEntryRecId("000000000000000000000".equals(payExitRec.getEntryRecid())?"":payExitRec.getEntryRecid());
            rev.setPayFare("0.01");
            rev.setVehcolorId(payExitRec.getFarePlatecolor());
            rev.setFileId(payExitRec.getRecid()+".jpg");
            ThreadPoolFtp.ftpThreadPool().execute(new FtpThread(fTPConfig,rev.getFileId()));
            //fileUploadService.fileUpload(fTPConfig,rev.getFileId());
            try {
                ResponseEntity<Object> getEntity = oAuthRestTemplate.postForEntity(exitUrl,rev,Object.class);
                Map<String,Object> map = gson.fromJson(getEntity.getBody().toString(),Map.class);
                if ("200".equals(map.get("code").toString().substring(0,map.get("code").toString().indexOf(".")))){
                    LOGGER.info(map.get("msg").toString());
                    payExitRecMapper.updateById(payExitRec.getRecid());
                } else {
                    LOGGER.error(map.get("msg").toString());
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }
        }
    }
}
