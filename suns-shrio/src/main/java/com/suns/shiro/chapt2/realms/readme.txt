shrio笔记

jar包  
    只需要shiro-core
    

了解shiro
    定义一个shiro.ini文件   sunshen=123
	
	Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + file);
    SecurityManager securityManager = factory.getInstance();  
    SecurityUtils.setSecurityManager(securityManager);

	继承 AuthorizingRealm 类  来处理认证和授权
	
	
	securityManager.authenticator.authenticationStrategy 来配置 多个Realms时判断策略：
	#所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息
	allSuccessfulStrategy=org.apache.shiro.authc.pam.AllSuccessfulStrategy

	#只要有一个Realm验证成功即可，返回所有Realm身份验证成功的认证信息；
	atLeastOneSuccessfulStrategy=org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy

	#只要有一个Realm验证成功即可，只返回第一个Realm身份验证成功的认证信息
	firstSuccessfulStrategy=org.apache.shiro.authc.pam.FirstSuccessfulStrategy
    
    
  