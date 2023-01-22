package com.ixpath.appmanager.exception;

public class AppNotFound extends Exception{

	private static final long serialVersionUID = 695689536966180348L;

	public AppNotFound() {
		super("App not found");
		this.setCodeError("404");
	}
}
