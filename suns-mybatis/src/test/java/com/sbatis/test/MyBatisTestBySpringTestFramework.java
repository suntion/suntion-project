package com.sbatis.test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sbatis2.domain.User;
import com.sbatis2.service.UserServiceI;

/**
 * <p>
 * DO:
 * </p>
 * <p>
 * Company: suns
 * </p>
 * @author suns suntion@yeah.net
 * @since 2016年12月5日 下午5:17:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class MyBatisTestBySpringTestFramework {

    // 注入userService
    @Autowired
    private UserServiceI userService;
    String id = UUID.randomUUID().toString().replaceAll("-", "");
    
    public void testAddUser() {
        User user = new User();
        user.setUserId(id);
        user.setUserName("xdp_gacl_白虎神皇");
        user.setUserBirthday(new Date());
        user.setUserSalary(10000D);
        userService.addUser(user);
    }

    public void testGetUserById() {
        User user = userService.getUserById("0d8a2e53d2384df594654b3418fed9bf");
        userService.getUserById("0d8a2e53d2384df594654b3418fed9bf");
        System.out.println(user.getUserName());
    }

    @Test
    public void testlist(){
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
