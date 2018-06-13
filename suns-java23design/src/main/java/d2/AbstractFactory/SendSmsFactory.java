/**
 * 
 */
package d2.AbstractFactory;

/**
 * TODO SendSmsFactory
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:30:57
 */
public class SendSmsFactory implements Provider{  
  
    @Override  
    public Sender produce() {  
        return new SmsSender();  
    }

}
