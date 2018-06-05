package com.mydao.nsop.client.dao;

import com.mydao.nsop.client.domain.entity.PayWhiteList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayWhiteListMapper {
    int deleteByPrimaryKey(String plateno);

    int insertSelective(PayWhiteList record);

    PayWhiteList selectByPrimaryKey(String plateno);

    int updateByPrimaryKeySelective(PayWhiteList record);
}