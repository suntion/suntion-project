/**
 * 
 */
package d7.decorator;

/**
 * TODO Decorator
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午5:29:48
 */
public class Decorator implements Sourceable {  
    
    private Sourceable source;  
      
    public Decorator(Sourceable source){  
        super();  
        this.source = source;  
    }  
    @Override  
    public void method() {  
        System.out.println("before decorator!");  
        source.method();  
        System.out.println("after decorator!");  
    }  
} 
