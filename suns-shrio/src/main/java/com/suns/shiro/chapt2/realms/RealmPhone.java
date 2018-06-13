/**
 * 
 */
package com.suns.shiro.chapt2.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * TODO MyRealm1
 * 
 * @author suns sontion@yeah.net
 * @since 2017年2月24日下午2:55:57
 */
public class RealmPhone extends AuthorizingRealm{

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
        
        if (!"13629711009".equals(principal)) {
            throw new UnknownAccountException();
        }
        
        if (!"1".equals(credentials)) {
            throw new IncorrectCredentialsException();
        }
        
        return new SimpleAuthenticationInfo(principal + "phone", credentials, getName());
    }

}
