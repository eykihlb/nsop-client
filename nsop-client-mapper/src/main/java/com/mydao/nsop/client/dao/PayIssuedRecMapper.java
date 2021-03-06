package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayIssuedRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface PayIssuedRecMapper {

    int insertSelective(PayIssuedRec record);

    Integer deleteByPlateNo(String plateNo);

    PayIssuedRec selectById(Map<String,Object> map);

    int updateByPlateNo(Map<String,Object> map);

    int selectCountByPlateNoAndStatus(@Param("plateNo") String plateNo,@Param("status") String status);
}