/**
 * 
 */
package com.sbatis.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* <p>
* DO: 
* </p>
* <p>Company: suns </p> 
* @author suns suntion@yeah.net
* @since 2016年12月2日 下午3:19:10
*/
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;  
    private String name;  
    private int age;  
    private String address;
    
    public User() {  
    }  
  
    public User(int id, String address) {  
        this.id = id;  
        this.address = address;  
    }  
  
    public User(String name, int age, String address) {  
        this.name = name;  
        this.age = age;  
        this.address = address;  
    }
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
