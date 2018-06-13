/**
 * 
 */
package d1.FactoryMethod.staticmulitmethod;

import d1.FactoryMethod.MailSender;
import d1.FactoryMethod.Sender;
import d1.FactoryMethod.SmsSender;

/**
 * TODO SendFactory
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:20:58
 */
public class SendFactory {
    public static Sender produceMail(){  
        return new MailSender();  
    }  
      
    public static Sender produceSms(){  
        return new SmsSender();  
    }
}
