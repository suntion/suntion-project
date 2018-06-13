/**
 * 
 */
package d1.FactoryMethod;

/**
 * TODO SmsSender
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:13:55
 */
public class SmsSender implements Sender {  
  
    @Override  
    public void Send() {  
        System.out.println("this is sms sender!");  
    }

}
