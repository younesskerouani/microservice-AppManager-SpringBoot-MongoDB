package com.ixpath.appmanager.exception;

public class Exception extends RuntimeException {

	protected static final long serialVersionUID = 1L;
	protected String codeError;
	protected java.lang.Exception exception;

	public Exception(java.lang.Exception exception) {
		super(exception.getMessage());
		this.exception = exception;
		this.initCause(exception.getCause());
	}

	public String getExceptionMessage() {
		return this.exception.getMessage();
	}

	public Exception(String desc) {
		super(desc);
	}

	public String getCodeError() {
		return codeError;
	}
	
	public void setCodeError(String codeError) {
		this.codeError = codeError;
	}
	
	public java.lang.Exception getException() {
		return exception;
	}
}
