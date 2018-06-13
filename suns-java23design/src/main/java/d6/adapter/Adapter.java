/**
 * 
 */
package d6.adapter;

/**
 * TODO Adapter
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午5:22:51
 */
public class Adapter extends Source implements Targetable{

    @Override
    public void method2() {
        System.out.println("this is the targetable method!");
    }
    
}
