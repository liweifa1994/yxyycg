package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.po.Userjd;
import com.kargo.yycg.base.pojo.po.UserjdExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserjdMapper {
    int countByExample(UserjdExample example);

    int deleteByExample(UserjdExample example);

    int deleteByPrimaryKey(String id);

    int insert(Userjd record);

    int insertSelective(Userjd record);

    List<Userjd> selectByExample(UserjdExample example);

    Userjd selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Userjd record, @Param("example") UserjdExample example);

    int updateByExample(@Param("record") Userjd record, @Param("example") UserjdExample example);

    int updateByPrimaryKeySelective(Userjd record);

    int updateByPrimaryKey(Userjd record);
}