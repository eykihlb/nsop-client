package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayIssuedRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayIssuedRecMapper {

    int insertSelective(PayIssuedRec record);

    Integer deleteByPlateNo(String plateNo);

    PayIssuedRec selectById(String plateNo);

    int selectCountByPlateNo(String plateNo);

    int updateByStatus(@Param("status") int status,@Param("plateNo") String plateNo);
}