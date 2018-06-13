/**
 * 
 */
package d13.command;

import java.awt.MultipleGradientPaint.CycleMethod;

import org.junit.Test;

/**
 * Java之命令模式（Command Pattern）
 * 
 * 1.概念
 * 
 * 将来自客户端的请求传入一个对象，从而使你可用不同的请求对客户进行参数化。用于“行为请求者”与“行为实现者”解耦，可实现二者之间的松耦合，以便适应变化。
 * 分离变化与不变的因素。
 * 
 * 在面向对象的程序设计中，一个对象调用另一个对象，一般情况下的调用过程是：创建目标对象实例；设置调用参数；调用目标对象的方法。
 * 
 * 但在有些情况下有必要使用一个专门的类对这种调用过程加以封装，我们把这种专门的类称作command类。
 * 
 * Command模式可应用于 a）整个调用过程比较繁杂，或者存在多处这种调用。这时，使用Command类对该调用加以封装，便于功能的再利用。
 * b）调用前后需要对调用参数进行某些处理。 c）调用前后需要进行某些额外处理，比如日志，缓存，记录历史操作等。
 * 
 * Command模式有如下效果： a）将调用操作的对象和知道如何实现该操作的对象解耦。 b）Command是头等对象。他们可以像其他对象一样被操作和扩展。
 * c）你可将多个命令装配成一个符合命令。 d）增加新的Command很容易，因为这无需改变现有的类。
 * 
 * 
 * @author suns sontion@yeah.net
 * @since 2017年9月11日上午9:44:49
 */
public class test {

    @Test
    public void name() {
        Tv tv = new Tv();

        CommandOn on = new CommandOn(tv);
        CommandOff off = new CommandOff(tv);

        Control ct = new Control(on, off);
        
        ct.turnOn();
        ct.turnOff();
        

    }

}
