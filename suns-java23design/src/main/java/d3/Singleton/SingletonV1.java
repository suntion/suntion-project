/**
 * 
 */
package d3.Singleton;

/**
 * TODO SingletonV1
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:38:58
 */
public class SingletonV1 {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */  
    private static SingletonV1 instance = null;  
  
    /* 私有构造方法，防止被实例化 */  
    private SingletonV1() {
        System.out.println("初始化");
    }  
  
    /**
     * 静态工程方法，创建实例 
     * 方法上加 synchronized 锁住的是整个对象
     * 
     * 将synchronized关键字加在了内部，也就是说当调用的时候是不需要加锁的，只有在instance为null，并创建对象的时候才需要加锁，性能有一定的提升
     * 
     * 
     * @return
     */
    public static synchronized SingletonV1 getInstance() {  
        if (instance == null) {  
            instance = new SingletonV1();  
        }
        return instance;
    }  
  
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */  
    public Object readResolve() {  
        return instance;  
    }  
}
