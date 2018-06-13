/**
 * 
 */
package d2.AbstractFactory;

/**
 * TODO MailSender
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午3:13:47
 */
public class MailSender implements Sender {  
    @Override  
    public void Send() {  
        System.out.println("this is mailsender!");  
    }

}
