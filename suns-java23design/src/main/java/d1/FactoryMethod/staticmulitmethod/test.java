/**
 * 
 */
package d1.FactoryMethod.staticmulitmethod;

import d1.FactoryMethod.Sender;

/**
 * 将多个工厂方法模式里的方法置为静态的，不需要创建实例，直接调用即可。
 * TODO test
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:21:13
 */
public class test {
    public static void main(String[] args) {      
        Sender sender = SendFactory.produceMail();  
        sender.Send();  
    }
}
