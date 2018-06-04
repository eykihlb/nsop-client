package com.mydao.nsop.client.thrift.server;

import com.mydao.nsop.client.thrift.VehService;
import com.mydao.nsop.client.thrift.handler.VehServiceHandler;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ZYW
 * @date 2018/6/4
 */
@Component
public class ThriftServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftServer.class);

    @Value("${nsop.traffic.thriftPort}")
    private int thriftServerPort;

    @Autowired
    private VehServiceHandler vehServiceHandler;

    public void startServer() {
        try {
            TProcessor tProcessor = new VehService.Processor<>(vehServiceHandler);
            TServerSocket tServerSocket = new TServerSocket(thriftServerPort);
            TThreadPoolServer.Args tpsArgs = new TThreadPoolServer.Args(tServerSocket);

            tpsArgs.processor(tProcessor);
            tpsArgs.transportFactory(new TFramedTransport.Factory());
            tpsArgs.protocolFactory(new TCompactProtocol.Factory());

            TServer server = new TThreadPoolServer(tpsArgs);
            LOGGER.info("start thrift server at: " + thriftServerPort);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
