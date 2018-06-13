/**
 * 
 */
package d6.adapter;

import org.junit.Test;

/**
 * 适配器模式将某个类的接口转换成客户端期望的另一个接口表示，目的是消除由于接口不匹配所造成的类的兼容性问题。主要分为三类：类的适配器模式、对象的适配器模式、
 * 接口的适配器模式
 * 
 * 讲了这么多，总结一下三种适配器模式的应用场景：
 * 类的适配器模式：当希望将一个类转换成满足另一个新接口的类时，可以使用类的适配器模式，创建一个新类，继承原有的类，实现新的接口即可。
 * 对象的适配器模式：当希望将一个对象转换成满足另一个新接口的对象时，可以创建一个Wrapper类，持有原类的一个实例，在Wrapper类的方法中，
 * 调用实例的方法就行。
 * 接口的适配器模式：当不希望实现一个接口中所有的方法时，可以创建一个抽象类Wrapper，实现所有方法，我们写别的类的时候，继承抽象类即可。
 * 
 * 
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午5:20:21
 */
public class test {

    /**
     * 类的适配器模式
     */
    @Test
    public void test1() {
        Targetable target = new Adapter();
        target.method1();
        target.method2();
    }

    /**
     * 对象的适配器模式
     */
    @Test
    public void test2() {
        Source source = new Source();
        Targetable target = new Wrapper(source);
        target.method1();
        target.method2();
    }

}
