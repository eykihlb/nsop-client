package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayEntryRec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayEntryRecMapper {

    int insertSelective(PayEntryRec record);

    PayEntryRec selectByPID(String pid);

    List<PayEntryRec> selectList();

    Integer updateById(String recId);
}