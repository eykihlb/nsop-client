package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayExitRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PayExitRecMapper {

    int insertSelective(PayExitRec record);

    List<PayExitRec> selectList();

    List<PayExitRec> selectListByExcepFlag();

    Integer updateById(String recId);

    void updateObjById(@Param("flag") String flag, @Param("recId") String recId);
}