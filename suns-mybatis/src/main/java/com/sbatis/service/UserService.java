/**
 * 
 */
package com.sbatis.service;

import java.util.List;

import com.sbatis.model.Article;
import com.sbatis.model.User;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月2日 下午3:54:25
*/
public interface UserService {
    User getUserById(long id);
    
    List<User> selectUsers();
    
    void addUser(User user);
    
    void updateUser(User user);
    
    void deleteUser(long id);
    
    List<Article> getUserArticles(long id);
}
