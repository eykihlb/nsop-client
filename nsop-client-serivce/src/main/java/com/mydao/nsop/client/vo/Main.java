package com.mydao.nsop.client.vo;

import com.mydao.nsop.client.thrift.VehService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author ZYW
 * @date 2018/6/4
 */
public class Main {
    public static void main(String[] args) throws TException {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 3333));
        TProtocol protocol = new TCompactProtocol(transport);
        transport.open();

        VehService.Client client = new VehService.Client(protocol);
        EntryInfo entryInfo = client.getEntryInfo("1111");
        System.out.println(entryInfo);
    }
}
