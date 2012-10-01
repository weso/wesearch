package org.weso.utils;

public class NotImplementedException extends UnsupportedOperationException {
	public NotImplementedException(String msg){ super("Unimplemented: " + msg);}
}
