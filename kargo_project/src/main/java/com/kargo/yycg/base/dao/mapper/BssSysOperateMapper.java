package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.po.BssSysOperate;
import com.kargo.yycg.base.pojo.po.BssSysOperateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BssSysOperateMapper {
    int countByExample(BssSysOperateExample example);

    int deleteByExample(BssSysOperateExample example);

    int deleteByPrimaryKey(String operateid);

    int insert(BssSysOperate record);

    int insertSelective(BssSysOperate record);

    List<BssSysOperate> selectByExample(BssSysOperateExample example);

    BssSysOperate selectByPrimaryKey(String operateid);

    int updateByExampleSelective(@Param("record") BssSysOperate record, @Param("example") BssSysOperateExample example);

    int updateByExample(@Param("record") BssSysOperate record, @Param("example") BssSysOperateExample example);

    int updateByPrimaryKeySelective(BssSysOperate record);

    int updateByPrimaryKey(BssSysOperate record);
}