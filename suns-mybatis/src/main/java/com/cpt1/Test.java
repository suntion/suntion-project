package com.cpt1;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import com.cpt1.entity.User;
import com.cpt1.mapper.UserMapper2;

/**
 * 
 */

/**
 * TODO Test
 * @author suns sontion@yeah.net
 * @since 2017年3月16日上午11:19:46
 */
public class Test {
    private SqlSessionFactory sqlSessionFactory;
    
    
    @Before
    public void before() throws IOException {
        String resource = "com/cpt1/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    
    
    
    @org.junit.Test
    public void test1() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
          User user = (User) session.selectOne("com.cpt1.mapper.UserMapper1.getUserById", 1);
          System.out.println(user.toString());
        } finally {
          session.close();
        }
    }
    
    
    @org.junit.Test
    public void test2() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper2 userMapper2 = session.getMapper(UserMapper2.class);
            User user =userMapper2.getUserById(1);
            System.out.println(user.toString());
        } finally {
          session.close();
        }
    }
    
    
    @org.junit.Test
    public void test3() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper2 userMapper2 = session.getMapper(UserMapper2.class);
            List<User> userList =userMapper2.getUserList();
            for (User user : userList) {
                System.out.println(user.toString());
            }
        } finally {
          session.close();
        }
    }
    
    

}
