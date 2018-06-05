package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayExitRec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayExitRecMapper {

    int insertSelective(PayExitRec record);

    List<PayExitRec> selectList();

    Integer updateById(String recId);
}