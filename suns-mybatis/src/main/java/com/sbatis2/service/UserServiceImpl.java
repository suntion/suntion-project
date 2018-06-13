/**
 * 
 */
package com.sbatis2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbatis2.domain.User;
import com.sbatis2.mapper.UserMapper;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月5日 下午5:16:00
*/
@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserMapper userMapper;
    
    
    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
    
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
    
    @Override
    public void updateByPrimaryKey(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
