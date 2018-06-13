/**
 * 
 */
package d8.proxy;

/**
 * TODO Proxy
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午5:34:53
 */
public class Proxy implements Sourceable {  
    
    private Source source;  
    public Proxy(){  
        super();  
        this.source = new Source();  
    }  
    
    @Override  
    public void method() {  
        source.method();  
    }  
} 