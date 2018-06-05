package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayEntryRec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayEntryRecMapper {

    int insertSelective(PayEntryRec record);

    PayEntryRec selectByPID(String pid);
}