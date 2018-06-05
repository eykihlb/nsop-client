package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayExitRec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayExitRecMapper {
    int insert(PayExitRec record);

    int insertSelective(PayExitRec record);
}