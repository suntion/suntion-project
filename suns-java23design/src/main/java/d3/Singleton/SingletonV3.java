/**
 * 
 */
package d3.Singleton;

/**
 * 
 * 因为我们只需要在创建类的时候进行同步，所以只要将创建和getInstance()分开，单独为创建加synchronized关键字，也是可以的：
 * TODO SingletonV3
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午4:50:52
 */
public class SingletonV3 {
    private static SingletonV3 instance = null;  
    
    private SingletonV3() {
        System.out.println("初始化");
    }  
  
    private static synchronized void init() {  
        if (instance == null) {  
            instance = new SingletonV3();  
        }  
    }  
  
    public static SingletonV3 getInstance() {  
        if (instance == null) {  
            init();  
        }  
        return instance;  
    }  
}
