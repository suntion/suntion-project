/**
 * 
 */
package d1.FactoryMethod.normal;

import d1.FactoryMethod.MailSender;
import d1.FactoryMethod.Sender;
import d1.FactoryMethod.SmsSender;

/**
 * TODO SendFactory
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:14:26
 */
public class SendFactory {
    public Sender produce(String type) {  
        if ("mail".equals(type)) {  
            return new MailSender();  
        } else if ("sms".equals(type)) {  
            return new SmsSender();  
        } else {  
            System.out.println("请输入正确的类型!");  
            return null;  
        }  
    }
}
