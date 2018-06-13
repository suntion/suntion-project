package com.sbatis2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sbatis2.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    @Select("select * from user where dr='1'")
    List<User> selectAll();
}