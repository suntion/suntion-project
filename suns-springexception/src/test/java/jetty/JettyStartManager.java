package jetty;

import java.net.InetAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 使用jetty嵌入方式启动web应用
 */
public class JettyStartManager {
    
    /**使用的端口号*/
    public static final int PORT = 7373;
    
    /** 使用的项目名称*/
    public static final String CONTEXT = "/ss";
    
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
                System.in.read();
                reloadContext(server);
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
        
        System.out.println("【项目已经启动,控制台输入任意键重启】Server running at： http://" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT + CONTEXT);
    }
}
