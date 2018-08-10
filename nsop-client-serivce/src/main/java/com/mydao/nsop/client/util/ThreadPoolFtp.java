package com.mydao.nsop.client.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFtp {
    private final static Log logger = LogFactory.getLog(ThreadPoolFtp.class);
    private  static ThreadPoolExecutor ftpPool = null;
    public static synchronized ThreadPoolExecutor ftpThreadPool(){
        if(ftpPool==null){
            logger.debug("线程池启动》》》》》》》");
            ftpPool = new ThreadPoolExecutor(2, 5, 200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(500));
        }
        return ftpPool;
    }
}
