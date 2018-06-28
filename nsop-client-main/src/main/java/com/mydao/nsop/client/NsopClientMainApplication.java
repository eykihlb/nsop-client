package com.mydao.nsop.client;

import com.mydao.nsop.client.thrift.server.ThriftServer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NsopClientMainApplication {

	private final static Logger LOGGER = Logger.getLogger(NsopClientMainApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(NsopClientMainApplication.class, args);
		ThriftServer thriftServer = run.getBean(ThriftServer.class);
		thriftServer.startServer();
		LOGGER.info("启动成功！");
	}

}
