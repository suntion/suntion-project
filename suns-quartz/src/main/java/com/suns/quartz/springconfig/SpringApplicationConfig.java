/**
 * 
 */
package com.suns.quartz.springconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * TODO SpringApplicationConfig
 * @author suns sontion@yeah.net
 * @since 2017年9月11日上午11:11:28
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = { "com.suns" })
@PropertySource(value = { "classpath:application.properties" })
public class SpringApplicationConfig {
    
}
