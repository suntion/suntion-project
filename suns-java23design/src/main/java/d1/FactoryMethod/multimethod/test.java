/**
 * 
 */
package d1.FactoryMethod.multimethod;

import d1.FactoryMethod.Sender;

/**
 * TODO test
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:18:53
 */
public class test {
    public static void main(String[] args) {  
        
        SendFactory factory = new SendFactory();
        Sender sender = factory.produceMail();  
        sender.Send();  
    }  
}
