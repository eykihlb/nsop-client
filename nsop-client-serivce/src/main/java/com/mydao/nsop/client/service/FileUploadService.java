package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.util.FTPUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;

@Component
public class FileUploadService {

    @Async
    public void fileUpload(FTPConfig fTPConfig, String fileName) {
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
    }
}
