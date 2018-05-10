package com.mydao.nsop.client.service;

import com.mydao.nsop.client.config.FTPConfig;
import com.mydao.nsop.client.util.FTPUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FileUploadService {

    @Async
    public boolean fileUpload(FTPConfig fTPConfig, String fileName) throws IOException {
        boolean flag = false;
        int index = 0;
        while (!flag){
            String filePath = FTPUtil.downloadFtpFile(fTPConfig,fileName);
            FileInputStream input = new FileInputStream(new File(filePath));
            flag = FTPUtil.uploadFile(fTPConfig,fileName,input);
            index ++;
            if (index == 5){
                break;
            }
        }
        return flag;
    }
}
