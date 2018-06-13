/**
 * 
 */
package com.sbatis2.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sbatis2.domain.User;
import com.sbatis2.service.UserServiceI;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月12日 下午2:52:12
*/
@Controller
@RequestMapping(value = "/test")
public class Test1 {
    @Autowired
    private UserServiceI userService;
    
    String id = UUID.randomUUID().toString().replaceAll("-", "");
    
    
    @RequestMapping(value = "t1", method=RequestMethod.GET)
    public  void test1(){
        List<User> userList = null;
        
        userList = userService.selectAll();
        for (User e :userList) {
            System.out.println(e.toString());
        }
        userList.clear();
        
        User u = userService.getUserById("0d8a2e53d2384df594654b3418fed9bf");
        u.setUserName(RandomUtils.nextInt(1000, 9999)+"");
        userService.updateByPrimaryKey(u);
        System.out.println(u.toString());
        
        userList = userService.selectAll();
        for (User e :userList) {
            System.out.println(e.toString());
        }
    }
}
