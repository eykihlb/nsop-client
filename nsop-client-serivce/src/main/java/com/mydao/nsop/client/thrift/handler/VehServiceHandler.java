package com.mydao.nsop.client.thrift.handler;

import com.mydao.nsop.client.dao.PayBlackListMapper;
import com.mydao.nsop.client.dao.PayEntryRecMapper;
import com.mydao.nsop.client.domain.entity.PayEntryRec;
import com.mydao.nsop.client.thrift.VehService;
import com.mydao.nsop.client.vo.EntryInfo;
import com.mydao.nsop.client.vo.VehInfo;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZYW
 * @date 2018/6/4
 */
@Service("vehServiceHandler")
public class VehServiceHandler implements VehService.Iface {

    @Autowired
    private PayBlackListMapper payBlackListMapper;

    @Autowired
    private PayEntryRecMapper payEntryRecMapper;

    @Override
    public boolean isInBlackList(String plateno) throws TException {
        return payBlackListMapper.selectByPrimaryKey(plateno) != null;
    }

    @Override
    public EntryInfo getEntryInfo(String plateno) throws TException {
        EntryInfo entryInfo = new EntryInfo();
        PayEntryRec payEntryRec = payEntryRecMapper.selectByPID(plateno);
        entryInfo.setEntryLaneNo(payEntryRec.getLaneno());
        entryInfo.setEntryNetNo(payEntryRec.getNetno());
        entryInfo.setEntryRecID(payEntryRec.getRecid());
        entryInfo.setEntrySiteNo(payEntryRec.getSiteno());
        entryInfo.setEntryTime(String.valueOf(payEntryRec.getEntrytime().getTime()));
        entryInfo.setVehClass(payEntryRec.getVehclass());
        return entryInfo;
    }

    @Override
    public VehInfo getByPlateInfo(String plateno) throws TException {
        return new VehInfo();
    }
}
