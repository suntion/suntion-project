package com.suns.quartz;

import java.net.InetAddress;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO TomcatBootStrap
 * 
 * @author suns sontion@yeah.net
 * @since 2017年7月18日下午3:28:45
 */
public class TomcatMgrStrap {
    private static Logger logger = LoggerFactory.getLogger(TomcatMgrStrap.class);
    
    /** 使用的端口号 */
    public static final int    PORT    = 80;

    /** 使用的项目名称 */
    public static final String CONTEXT = "/quartz";
    
    public static void main(String[] args) throws Exception {
        try {
            long begin = System.currentTimeMillis();
            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir("target");
            //协议版本  
            Connector connector = new Connector("HTTP/1.1");  
            connector.setPort(PORT); 
            connector.setURIEncoding("UTF-8");  
            connector.setUseBodyEncodingForURI(true);  
            
            tomcat.setConnector(connector);  
            tomcat.getService().addConnector(connector);  
            
            Class<TomcatMgrStrap> clazz = TomcatMgrStrap.class;
            String file = clazz.getClassLoader().getResource(".").getFile();
            String webappPath = file.substring(0, file.indexOf("target")) + "src/main/webapp";
            Context ctx = tomcat.addWebapp(CONTEXT, webappPath);

            StandardJarScanner scanner = (StandardJarScanner) ctx.getJarScanner();  
            //是否扫描全目录  
            scanner.setScanAllDirectories(true);  
            //是否扫描classpath  
            scanner.setScanClassPath(true);
            
            tomcat.start();
            long end = System.currentTimeMillis();
//          //Desktop.getDesktop().browse(new URI("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT + CONTEXT));
            logger.info(">>>>>>>>>>>>>>>>>>>> application start success. " + "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT + CONTEXT);
            logger.info(">>>>>>>>>>>>>>>>>>>> application startd in: {} ms", end - begin);
            tomcat.getServer().await();
        } catch (Exception e) {
            logger.error((new StringBuilder()).append("tomcat filed: ").append(e.getMessage()).toString(), e);
        }
    }
    
}
