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
import com.mydao.nsop.client.domain.vo.RoadAbnormalDataAddVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Component
public class VehicleDriveInOutExceptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDriveInOutExceptionService.class);
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
     * 异常处理
     */
    @Scheduled(fixedRate = Constants.EXCEPTION_CHECK_TIMES)
    public void driveExceptCheckInOut(){

        LOGGER.info("车辆驶入异常处理定时任务");
        final String entryUrl = trafficConfig.getUrl() + interFaceConfig.getExceptInOut();
        List<PayEntryRec> perList = payEntryRecMapper.selectListByExceptFlag();
        RoadAbnormalDataAddVo recVo =  new RoadAbnormalDataAddVo();
        //RoadEntryVo rev = new RoadEntryVo();
        String  recId = "";
        for (PayEntryRec payEntryRec : perList) {
            recId = payEntryRec.getRecid();
            recVo.setRecCode(recId);
            recVo.setCamerano(payEntryRec.getCamerano());
            //recVo.setEntryRecid(payEntryRec.getRecid());
            recVo.setLaneno(payEntryRec.getLaneno());
            recVo.setEntno(payEntryRec.getNetno());
            recVo.setSiteno(payEntryRec.getSiteno());
            recVo.setLprPlateno(payEntryRec.getLprPlateno());
            recVo.setFarePlateno(payEntryRec.getFarePlateno());
            recVo.setFarePlatecolor(payEntryRec.getFarePlatecolor());
            recVo.setEntryTime(payEntryRec.getEntrytime());
            recVo.setVehclass(payEntryRec.getVehclass());
            recVo.setType(1);
           // recVo.set(payEntryRec.getRecid()+".jpg");
            //上传图片到FTP
            //ThreadPoolFtp.ftpThreadPool().execute(new FtpThread(fTPConfig,rev.getFileId()));
            //fileUploadService.fileUpload(fTPConfig,rev.getFileId());
            try {
                ResponseEntity<Object> getEntity = oAuthRestTemplate.postForEntity(entryUrl,recVo,Object.class);
                Map<String,Object> map = gson.fromJson(getEntity.getBody().toString(),Map.class);
                if ("200".equals(map.get("code").toString().substring(0,map.get("code").toString().indexOf(".")))){
                    LOGGER.info(map.get("msg").toString());
                    payEntryRecMapper.updateObjById("3",recId);
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }
        }

        LOGGER.info("车辆驶出异常处理定时任务");
        final String exitUrl = trafficConfig.getUrl() + interFaceConfig.getExceptInOut();
        List<PayExitRec> payList = payExitRecMapper.selectListByExcepFlag();
        RoadAbnormalDataAddVo revExit =  new RoadAbnormalDataAddVo();
        //RoadExitVo revExit = new RoadExitVo();
        for (PayExitRec payExitRec : payList) {
            recId = payExitRec.getRecid();
            revExit.setRecCode(payExitRec.getRecid());
            revExit.setCamerano(payExitRec.getCamerano());
            revExit.setEntryRecid(payExitRec.getEntryRecid());
            revExit.setLaneno(payExitRec.getLaneno());
            revExit.setEntno(payExitRec.getNetno());
            revExit.setSiteno(payExitRec.getSiteno());
            revExit.setLprPlateno(payExitRec.getLprPlateno());
            revExit.setLprPlatecolor(payExitRec.getLprPlatecolor());
            revExit.setFarePlateno(payExitRec.getFarePlateno());
            revExit.setFarePlatecolor(payExitRec.getFarePlatecolor());
            revExit.setExitTime(payExitRec.getExittime());
            revExit.setVehclass(payExitRec.getVehclass());
            revExit.setEntryTime(payExitRec.getEntrytime());
            //revExit.setEntryRecId("000000000000000000000".equals(payExitRec.getEntryRecid())?"":payExitRec.getEntryRecid());
            //设置收费金额
            revExit.setFareTotal(format(payExitRec.getFaretotal()));
            //revExit.setPayFare("0.01");
            revExit.setFarePlateno(payExitRec.getFarePlatecolor());
            revExit.setType(2);
            //revExit.setFileId(payExitRec.getRecid()+".jpg");
            //上传图片到FTP
            //ThreadPoolFtp.ftpThreadPool().execute(new FtpThread(fTPConfig,rev.getFileId()));
            //fileUploadService.fileUpload(fTPConfig,rev.getFileId());
            try {
                ResponseEntity<Object> getEntity = oAuthRestTemplate.postForEntity(exitUrl,revExit,Object.class);
                Map<String,Object> map = gson.fromJson(getEntity.getBody().toString(),Map.class);
                if ("200".equals(map.get("code").toString().substring(0,map.get("code").toString().indexOf(".")))){
                    LOGGER.info(map.get("msg").toString());
                    payExitRecMapper.updateObjById("4",recId);
                }
            }catch (Exception e){

                LOGGER.error(e.getMessage());
            }
        }
    }

    private String format(BigDecimal money) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        return df.format(money);
    }
}
