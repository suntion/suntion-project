/**
 * 
 */
package d13.command;

/**
 * TODO CommandOff
 * @author suns sontion@yeah.net
 * @since 2017年9月11日上午9:50:56
 */
public class CommandOff implements Command{
    private Tv myTv;

    /**
     * @param myTv
     */
    public CommandOff(Tv myTv) {
        super();
        this.myTv = myTv;
    }

    @Override
    public void execute() {
        myTv.turnoff();
    }
}
