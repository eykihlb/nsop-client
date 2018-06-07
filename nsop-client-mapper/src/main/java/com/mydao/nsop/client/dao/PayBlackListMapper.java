package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayBlackList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayBlackListMapper {
    int deleteByPrimaryKey(String plateno);

    int insertSelective(PayBlackList record);

    PayBlackList selectByPrimaryKey(String plateno);

    int updateByPrimaryKeySelective(PayBlackList record);

    int deleteAll();
}