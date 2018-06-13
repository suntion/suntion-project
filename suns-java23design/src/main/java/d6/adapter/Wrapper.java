/**
 * 
 */
package d6.adapter;

/**
 * TODO Wrapper
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午5:25:07
 */
public class Wrapper implements Targetable {  
  
    private Source source;  
      
    public Wrapper(Source source){  
        super();  
        this.source = source;  
    }
    
    @Override  
    public void method2() {  
        System.out.println("this is the targetable method!");  
    }  
  
    @Override  
    public void method1() {  
        source.method1();  
    } 
}
