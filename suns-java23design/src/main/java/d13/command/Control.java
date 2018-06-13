/**
 * 
 */
package d13.command;

/**
 * TODO Control
 * 
 * @author suns sontion@yeah.net
 * @since 2017年9月11日上午9:52:58
 */
public class Control {
    private Command onCommand, offCommand;

    public Control(Command on, Command off) {
        onCommand = on;
        offCommand = off;
    }

    public void turnOn() {
        onCommand.execute();
    }

    public void turnOff() {
        offCommand.execute();
    }
}
