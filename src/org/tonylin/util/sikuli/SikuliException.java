package org.tonylin.util.sikuli;

public class SikuliException extends Exception {
	private static final long serialVersionUID = 1L;

	public SikuliException(Throwable e){
		super(e);
	}
	
	public SikuliException(String msg){
		super(msg);
	}
}
