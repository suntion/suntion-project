/**
 * 
 */
package d5.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * TODO Prototype
 * @author suns sontion@yeah.net
 * @since 2017年3月15日下午5:14:59
 */
public class Prototype implements Cloneable, Serializable {  
    private static final long serialVersionUID = 1L;  
    private String string;  
  
    private Serializable obj;
    
    
    /* 浅复制 */
    public Object clone() throws CloneNotSupportedException {  
        Prototype proto = (Prototype) super.clone();  
        return proto;  
    }
    
    
    /* 深复制 */  
    public Object deepClone() throws IOException, ClassNotFoundException {  
  
        /* 写入当前对象的二进制流 */  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ObjectOutputStream oos = new ObjectOutputStream(bos);  
        oos.writeObject(this);  
  
        /* 读出二进制流产生的新对象 */  
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());  
        ObjectInputStream ois = new ObjectInputStream(bis);  
        return ois.readObject();  
    }


    /**
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * @param string the string to set
     */
    public void setString(String string) {
        this.string = string;
    }


    /**
     * @return the obj
     */
    public Serializable getObj() {
        return obj;
    }
    
    /**
     * @param obj the obj to set
     */
    public void setObj(Serializable obj) {
        this.obj = obj;
    }  

}
