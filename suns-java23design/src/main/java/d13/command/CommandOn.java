/**
 * 
 */
package d13.command;

/**
 * TODO CommandOn
 * 
 * @author suns sontion@yeah.net
 * @since 2017年9月11日上午9:50:08
 */
public class CommandOn implements Command {

    private Tv myTv;

    /**
     * @param myTv
     */
    public CommandOn(Tv myTv) {
        super();
        this.myTv = myTv;
    }

    @Override
    public void execute() {
        myTv.turnon();
    }

}
