package com.cmss.storage;

public class ObjectAlreadyInitializedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4042733412900321479L;
	@SuppressWarnings("unused")
	private final String msg;
	public ObjectAlreadyInitializedException(String string) {
		// TODO Auto-generated constructor stub
		msg = string;
	}

}
