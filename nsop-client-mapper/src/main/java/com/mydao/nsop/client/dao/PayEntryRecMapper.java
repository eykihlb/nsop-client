package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayEntryRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PayEntryRecMapper {

    int insertSelective(PayEntryRec record);

    PayEntryRec selectByPID(String pid);

    List<PayEntryRec> selectList();

    List<PayEntryRec> selectListByExceptFlag();

    Integer updateById(String recId);

    void updateObjById(@Param("flag") String flag, @Param("recId")String recId);
}