/**
 * 
 */
package com.suns.shiro.chapt2;

import org.apache.shiro.subject.PrincipalCollection;
import org.junit.Test;

import com.suns.shiro.base.BaseTest;

/**
 * TODO LoginLogoutTest
 * @author suns sontion@yeah.net
 * @since 2017年2月24日下午2:27:45
 */
public class ShiroTest extends BaseTest{
    
    @Test
    public void test() {  
        login("chapt2-shiro.ini", "13629711009", "1");
     
        System.out.println(getSubject().isAuthenticated());
        
        PrincipalCollection principalCollection = getSubject().getPrincipals();
        System.out.println(principalCollection.asList().size());
    } 
}
