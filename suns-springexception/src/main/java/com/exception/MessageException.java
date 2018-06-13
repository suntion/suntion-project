package com.exception;

/**
 * 错误消息
 * @author long
 */
public class MessageException extends Exception{
    private static final long serialVersionUID = 3590260860821215306L;

    private Object code;
    
    public MessageException(Object code, String message) {
        super(message);
        this.code = code;
    }
    
    public MessageException(Object code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

}
