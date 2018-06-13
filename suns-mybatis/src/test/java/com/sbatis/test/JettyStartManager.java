package com.sbatis.test;

import java.awt.Desktop;
import java.net.InetAddress;
import java.net.URI;


import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Server;

/**
 * 使用jetty嵌入方式启动web应用
 * @author ss
 *
 */
public class JettyStartManager {
    /**日志*/
    private static Logger logger = LoggerFactory.getLogger(JettyStartManager.class);
    
    /**使用的端口号*/
    public static final int PORT = 7272;
    
    /** 使用的项目名称*/
    public static final String CONTEXT = "/mybatis";
    
    /**
     * 启动主方法
     * @param args 主参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        try {
            
            WebAppContext context = new WebAppContext();
            context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
            context.setResourceBase("./src/main/webapp");
            context.setContextPath(CONTEXT);
            context.setParentLoaderPriority(true);
            
            Server server = new Server(PORT);
            server.setHandler(context);
            reloadContext(server);
            server.start();
            
            // 等待用户输入命令.
            while (true) {
                char c = (char)System.in.read();
                switch (c) {
                    case 'o':
                        Desktop.getDesktop().browse(new URI("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT + CONTEXT));
                        break;
                    case 'r':
                        reloadContext(server);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * 快速重新启动application，重载classes
     * @param server 
     * @throws Exception 
     */
    public static void reloadContext(Server server) throws Exception {
        WebAppContext context = (WebAppContext)server.getHandler();
        context.stop();
        WebAppClassLoader classLoader = new WebAppClassLoader(context);
        context.setClassLoader(classLoader);
        context.start();
        
        logger.info("【控制台‘r’重新启动】Server running at： http://" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT + CONTEXT);
        logger.info("【控制台‘o’浏览器打开系统】");
    }
}
