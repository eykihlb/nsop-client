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
        String filePath = FTPUtil.downloadFtpFile(fTPConfig,fileName);
        FileInputStream input = new FileInputStream(new File(filePath));
        return FTPUtil.uploadFile(fTPConfig,fileName,input);
    }
}
