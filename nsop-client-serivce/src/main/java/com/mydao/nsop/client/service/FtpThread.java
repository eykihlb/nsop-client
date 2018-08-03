package com.mydao.nsop.client.service;

import com.mydao.nsop.client.common.Constants;
import com.mydao.nsop.client.config.FTPConfig;
import com.qcloud.cmq.Account;
import com.qcloud.cmq.Message;
import com.qcloud.cmq.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

public class FtpThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpThread.class);
    private FTPConfig fTPConfig;
    private String filedId;
    public FtpThread(FTPConfig fTPConfig,String filedId){
        this.fTPConfig = fTPConfig;
        this.filedId = filedId;
    }
    @Override
    public void run() {
        LOGGER.info("ftp线程调用-------");
        FileUploadService service = new FileUploadService();
        service.fileUpload(fTPConfig,filedId);
    }
}
