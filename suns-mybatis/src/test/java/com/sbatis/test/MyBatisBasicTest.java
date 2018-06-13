/**
 * 
 */
package com.sbatis.test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.sbatis.model.Article;
import com.sbatis.model.User;
import com.sbatis.service.UserService;

/**
 * <p>
 * DO:
 * </p>
 * <p>
 * Company: suns
 * </p>
 * 
 * @author suns suntion@yeah.net
 * @since 2016年12月2日 下午3:21:54
 */
public class MyBatisBasicTest {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 
    
    @Before
    public void init() throws IOException{
        reader = Resources.getResourceAsReader("Configuration.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void tt() {
        test2();
    }

    
    public  void test2() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserService userDao = session.getMapper(UserService.class);
            List<User> userList = null;
            
            userList = userDao.selectUsers();
            for (User e :userList) {
                System.out.println(e.toString());
            }
            System.out.println("1");
            
            User u = userDao.getUserById(1);
            u.setName(RandomUtils.nextInt(1, 99)+"");
            userDao.updateUser(u);
            System.out.println(u.toString());
            System.out.println("2");
            
            userList = userDao.selectUsers();
            for (User e :userList) {
                System.out.println(e.toString());
            }
            System.out.println("3");
            
        } finally {
            session.commit();
            session.close();
        }
    }
    
    
    public  void test1() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = (User) session.selectOne("com.sbatis.model.UserMapper.getUserById", 1);
            System.out.println(user.toString());
        } finally {
            session.close();
        }
    }
    
    
    
    public  void test4() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user=new User("sss", 11, "aaaa");
            UserService userDao = session.getMapper(UserService.class);
            userDao.addUser(user);
            session.commit();
            
            System.out.println(user.toString());
        } finally {
            session.close();
        }
    }
    
    
    public  void test5() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserService userDao = session.getMapper(UserService.class);
            User user=userDao.getUserById(2);
            user.setAge(RandomUtils.nextInt(1, 1000));
            
            userDao.updateUser(user);
            session.commit();
            
            System.out.println(user.toString());
        } finally {
            session.close();
        }
    }
    
    public  void test6() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserService userDao = session.getMapper(UserService.class);
            userDao.deleteUser(1);
            session.commit();
        } finally {
            session.close();
        }
    }
    
    
    public  void test7() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserService userDao = session.getMapper(UserService.class);
            List<Article> articles  = userDao.getUserArticles(1);
            for(Article article:articles){
                System.out.println(article.getTitle()+":"+article.getContent()+
                        ":作者是:"+article.getUser().getName()+":地址:"+
                         article.getUser().getAddress());
            }
            session.commit();
        } finally {
            session.close();
        }
    }
}
