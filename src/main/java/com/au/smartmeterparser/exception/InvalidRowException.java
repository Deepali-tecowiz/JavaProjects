package com.au.smartmeterparser.exception;

/**
 * Invalid ROW Exception
 * 
 * @author deepalipimparkar
 *
 */
public class InvalidRowException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRowException(String messsage) {
        super(messsage);
    }
}
