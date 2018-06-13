/**
 * 
 */
package com.suns.shiro.chapt3;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * TODO Realm
 * @author suns sontion@yeah.net
 * @since 2017年2月24日下午5:15:58
 */
public class Realm extends AuthorizingRealm{
    
    private PasswordService passwordService;

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("role1");
        authorizationInfo.addStringPermission("user2:*");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String) token.getPrincipal();
        String credentials = new String((char[])token.getCredentials());
        
        if (!"sunshen".equals(principal)) {
            throw new UnknownAccountException();
        }
        
        if (!"1".equals(credentials)) {
            throw new IncorrectCredentialsException();
        }
        
        return new SimpleAuthenticationInfo(principal, passwordService.encryptPassword(credentials), getName());
    }

}
