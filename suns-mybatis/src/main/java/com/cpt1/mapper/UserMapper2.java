/**
 * 
 */
package com.cpt1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.cpt1.entity.User;

/**
 * TODO UserMapper2
 * @author suns sontion@yeah.net
 * @since 2017年3月16日上午11:42:34
 */
public interface UserMapper2 {
   User getUserById(int id);
   
   @Select("select * from user")
   List<User> getUserList();
}
