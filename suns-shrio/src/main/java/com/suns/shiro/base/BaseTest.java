/**
 * 
 */
package com.suns.shiro.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * TODO LoginLogoutTest
 * @author suns sontion@yeah.net
 * @since 2017年2月24日下午2:27:45
 */
public class BaseTest {
    public void login(String file, String username, String password) {  
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + file);
        
        SecurityManager securityManager = factory.getInstance();
        
        SecurityUtils.setSecurityManager(securityManager);
        
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
            throw e;
        }
    }
    
    
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
