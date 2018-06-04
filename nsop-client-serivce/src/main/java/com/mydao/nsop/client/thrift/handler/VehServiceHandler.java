package com.mydao.nsop.client.thrift.handler;

import com.mydao.nsop.client.thrift.VehService;
import com.mydao.nsop.client.vo.EntryInfo;
import com.mydao.nsop.client.vo.VehInfo;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * @author ZYW
 * @date 2018/6/4
 */
@Service("vehServiceHandler")
public class VehServiceHandler implements VehService.Iface {

    @Override
    public boolean isInBlackList(String plateno) throws TException {
        return false;
    }

    @Override
    public EntryInfo getEntryInfo(String plateno) throws TException {
        EntryInfo entryInfo = new EntryInfo();
        entryInfo.setEntryRecID("22222");
        return entryInfo;
    }

    @Override
    public VehInfo getByPlateInfo(String plateno) throws TException {
        return new VehInfo();
    }
}
