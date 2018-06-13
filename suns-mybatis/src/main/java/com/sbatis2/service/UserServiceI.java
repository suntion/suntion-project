/**
 * 
 */
package com.sbatis2.service;

import java.util.List;

import com.sbatis2.domain.User;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月5日 下午5:14:51
*/
public interface UserServiceI {
    void addUser(User user);
    
    User getUserById(String userId);

    List<User> selectAll();
    
    void updateByPrimaryKey(User user);
}
