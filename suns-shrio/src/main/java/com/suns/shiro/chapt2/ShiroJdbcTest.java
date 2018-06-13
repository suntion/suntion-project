/**
 * 
 */
package com.suns.shiro.chapt2;

import org.junit.Test;

import com.suns.shiro.base.BaseTest;

import junit.framework.Assert;

/**
 * TODO LoginLogoutTest
 * 
 * @author suns sontion@yeah.net
 * @since 2017年2月24日下午2:27:45
 */
public class ShiroJdbcTest extends BaseTest {

    /**
     * 调用的是 默认支持的 org.apache.shiro.realm.jdbc.JdbcRealm 用了下面几句SQL
     * DEFAULT_AUTHENTICATION_QUERY = "select password from users where username = ?";
     * DEFAULT_SALTED_AUTHENTICATION_QUERY = "select password, password_salt from users where username = ?";
     * DEFAULT_USER_ROLES_QUERY = "select role_name from user_roles where username = ?";
     * DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";
     * 
     */
    @Test
    public void test() {
        login("chapt2-shiro-jdbc.ini", "sunshen", "123");

        Assert.assertEquals(true, getSubject().isAuthenticated());

        getSubject().logout();
    }
}
