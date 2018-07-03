package com.mydao.nsop.client.thrift.handler;

import com.mydao.nsop.client.dao.PayBlackListMapper;
import com.mydao.nsop.client.dao.PayIssuedRecMapper;
import com.mydao.nsop.client.dao.PayWhiteListMapper;
import com.mydao.nsop.client.domain.entity.PayIssuedRec;
import com.mydao.nsop.client.domain.entity.PayWhiteList;
import com.mydao.nsop.client.thrift.VehService;
import com.mydao.nsop.client.vo.EntryInfo;
import com.mydao.nsop.client.vo.VehInfo;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZYW
 * @date 2018/6/4
 */
@Service("vehServiceHandler")
public class VehServiceHandler implements VehService.Iface {

    @Autowired
    private PayBlackListMapper payBlackListMapper;

    @Autowired
    private PayWhiteListMapper payWhiteListMapper;
    @Autowired
    private PayIssuedRecMapper payIssuedRecMapper;

    @Override
    public boolean isInBlackList(String plateno) throws TException {
        System.out.println("---------------查询黑名单记录--------------" + plateno);
        return payBlackListMapper.selectByPrimaryKey(plateno) != null;
    }

    @Override
    public EntryInfo getEntryInfo(String plateno) throws TException {
        Map<String,Object> map = new HashMap<>();
        map.put("status","1");
        map.put("plateNo",plateno);
        PayIssuedRec payIssuedRec = payIssuedRecMapper.selectById(map);
        if(payIssuedRec == null) {
            return null;
        }
        System.out.println("---------------查询驶入记录--------------" + plateno);
        EntryInfo entryInfo = new EntryInfo();
        entryInfo.setEntryLaneNo(payIssuedRec.getLaneno());
        entryInfo.setEntryNetNo(payIssuedRec.getNetno());
        entryInfo.setEntryRecID(payIssuedRec.getRecid());
        entryInfo.setEntrySiteNo(payIssuedRec.getSiteno());
        entryInfo.setEntryTime(String.valueOf(payIssuedRec.getEntrytime().getTime()));
        entryInfo.setVehClass(payIssuedRec.getVehclass());
        return entryInfo;
    }

    @Override
    public VehInfo getByPlateInfo(String plateno) throws TException {

        PayWhiteList payWhiteList = payWhiteListMapper.selectByPrimaryKey(plateno);

        if(payWhiteList == null) {
            return null;
        }
        System.out.println("---------------查询白名单记录--------------" + plateno);
        VehInfo vehInfo = new VehInfo();
        vehInfo.setPlatecolor(Integer.parseInt(payWhiteList.getPlatecolor()));
        vehInfo.setWhiteFlag(1);
        vehInfo.setPlateno(plateno);
        vehInfo.setVehClass(payWhiteList.getVehclass());
        return vehInfo;
    }
}
