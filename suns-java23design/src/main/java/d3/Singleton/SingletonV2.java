package d3.Singleton;

/**
 * TODO SingletonV1 
 * 单例模式使用内部类来维护单例的实现，JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
 * 这样当我们第一次调用getInstance的时候，JVM能够帮我们保证instance只被创建一次，并且会保证把赋值给instance的内存初始化完毕，
 * 这样我们就不用担心上面的问题。同时该方法也只会在第一次调用的时候使用互斥机制，这样就解决了低性能问题。这样我们暂时总结一个完美的单例模式
 * 
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:38:58
 */
public class SingletonV2 {

    private SingletonV2() {
        System.out.println("初始化");
    }

    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory {
        private static final SingletonV2 INSTANCE = new SingletonV2();
    }


    /* 创建实例  */
    public static SingletonV2 getInstance() {
        return SingletonFactory.INSTANCE;
    }
}
