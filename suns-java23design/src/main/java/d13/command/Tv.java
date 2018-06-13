/**
 * 
 */
package d13.command;

/**
 * TODO Tv
 * 
 * @author suns sontion@yeah.net
 * @since 2017年9月11日上午9:47:28
 */
public class Tv {
    public int currentChannel = 0;

    public void turnon() {
        System.out.println("turnon");
    }

    public void turnoff() {
        System.out.println("turnoff");
    }

    public void changeChannel() {
        System.out.println("changeChannel" + currentChannel);
    }
}
