/**
 * 
 */
package d1.FactoryMethod.multimethod;

import d1.FactoryMethod.MailSender;
import d1.FactoryMethod.Sender;
import d1.FactoryMethod.SmsSender;

/**
 * TODO SendFactory
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:18:36
 */
public class SendFactory {
    public Sender produceMail(){  
        return new MailSender();  
    }  
      
    public Sender produceSms(){  
        return new SmsSender();  
    }
}
