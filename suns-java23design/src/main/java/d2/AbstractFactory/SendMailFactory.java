/**
 * 
 */
package d2.AbstractFactory;

/**
 * TODO SendMailFactory
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:30:40
 */
public class SendMailFactory implements Provider {  
    
  @Override  
  public Sender produce(){  
      return new MailSender();  
  }

}
