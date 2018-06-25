package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class FileUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);

    @Async
    public void fileUpload(FTPConfig fTPConfig, String fileName) {
        try {
            boolean flag = false;
            Integer index = 0;
            while (!flag){
                try{
                    String filePath = FTPUtil.downloadFtpFile(fTPConfig,fileName);
                    FileInputStream input = new FileInputStream(new File(filePath));
                    flag = FTPUtil.uploadFile(fTPConfig,fileName,input);
                }catch (Exception e){
                    index ++;
                    if (index.equals(Constants.FILEUPLOAD_RETRY)){
                        break;
                    }
                    continue;
                }
                index ++;
                if (index.equals(Constants.FILEUPLOAD_RETRY)){
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("FTP上传异常：" + e.getMessage(),e);
        }
    }
}
