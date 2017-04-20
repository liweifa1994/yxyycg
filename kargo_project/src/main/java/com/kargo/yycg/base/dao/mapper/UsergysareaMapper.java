package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.po.Usergysarea;
import com.kargo.yycg.base.pojo.po.UsergysareaExample;
import com.kargo.yycg.base.pojo.po.UsergysareaKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsergysareaMapper {
    int countByExample(UsergysareaExample example);

    int deleteByExample(UsergysareaExample example);

    int deleteByPrimaryKey(UsergysareaKey key);

    int insert(Usergysarea record);

    int insertSelective(Usergysarea record);

    List<Usergysarea> selectByExample(UsergysareaExample example);

    Usergysarea selectByPrimaryKey(UsergysareaKey key);

    int updateByExampleSelective(@Param("record") Usergysarea record, @Param("example") UsergysareaExample example);

    int updateByExample(@Param("record") Usergysarea record, @Param("example") UsergysareaExample example);

    int updateByPrimaryKeySelective(Usergysarea record);

    int updateByPrimaryKey(Usergysarea record);
}