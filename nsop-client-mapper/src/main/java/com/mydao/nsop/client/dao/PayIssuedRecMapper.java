package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayIssuedRec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayIssuedRecMapper {

    int insertSelective(PayIssuedRec record);

    Integer deleteByPlateNo(String plateNo);

    PayIssuedRec selectById(String plateNo);
}