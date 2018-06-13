/**
 * 
 */
package com.suns.shiro.chapt3;

import org.junit.Test;

import com.suns.shiro.base.BaseTest;

/**
 * TODO LoginLogoutTest
 * @author suns sontion@yeah.net
 * @since 2017年2月24日下午2:27:45
 */
public class ShiroRoleTest extends BaseTest{
    
    @Test
    public void testHasRole() {  
        login("chapt3-shiro.ini", "sunshen", "1");
     
        System.out.println(getSubject().hasRole("role1"));
        
        System.out.println(getSubject().hasRole("role2"));
    }
    
    
    @Test
    public void testCheckRole() {
        try {
            login("chapt3-shiro.ini", "sunshen", "1");
            
            getSubject().checkRole("role3");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
    @Test
    public void testIsPermitted() {
        try {
            login("chapt3-shiro.ini", "sunshen", "1");
            
            System.out.println(getSubject().isPermitted("user:create"));
            System.out.println(getSubject().isPermitted("user:update"));
            System.out.println(getSubject().isPermitted("user:view"));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
}
