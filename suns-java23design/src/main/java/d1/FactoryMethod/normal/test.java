/**
 * 
 */
package d1.FactoryMethod.normal;

import d1.FactoryMethod.Sender;

/**
 * TODO test
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:14:48
 */
public class test {
    
    public static void main(String[] args) {
        SendFactory factory = new SendFactory();  
        Sender sender = factory.produce("sms1");  
        sender.Send();  
    }  
}
