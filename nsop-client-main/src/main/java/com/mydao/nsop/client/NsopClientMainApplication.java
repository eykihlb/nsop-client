package com.mydao.nsop.client;

import com.mydao.nsop.client.thrift.server.ThriftServer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NsopClientMainApplication {

	private final static Logger LOGGER = Logger.getLogger(NsopClientMainApplication.class);
	/**
	 * 自定义异步线程池
	 * @return
	 */
	@Bean
	public AsyncTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("Client-Executor");
		executor.setMaxPoolSize(10);
		executor.setCorePoolSize(6);
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				// .....
			}
		});
		// 使用预定义的异常处理类
		return executor;
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(NsopClientMainApplication.class, args);
		ThriftServer thriftServer = run.getBean(ThriftServer.class);
		thriftServer.startServer();
		LOGGER.info("启动成功！");
	}
}
