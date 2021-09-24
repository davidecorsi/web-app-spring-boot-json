package it.partec.webappspringbootjson.exception;

public class CommonException extends Exception {

	public CommonException() {
		super();
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}	
}
