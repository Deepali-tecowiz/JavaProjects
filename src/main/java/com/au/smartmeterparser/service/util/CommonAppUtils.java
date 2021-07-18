package com.au.smartmeterparser.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * This utility class holds the common methods which can be used in generic way
 * 
 * @author deepalipimparkar
 *
 */

public class CommonAppUtils {

	/**
	 * Checks if object is null or not
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNull(Object value) {
		return (value != null ? false : true);
	}

	/**
	 * Check string is null or empty
	 * @param value
	 * @return
	 */
	public static boolean isStringNullOrEmpty(String value)  {
		if (value == null) {
			return true;
		} else if (value.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if collection is null or empty
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNullOrEmpty(Collection<?> value) {
		return ((value != null && !value.isEmpty()) ? false : true);
	}

	/**
	 * Converts string to local date
	 * 
	 * @param localDate
	 * @return LocalDate
	 */
	public static LocalDate getLocalDate(String localDate) {
		return LocalDate.parse(localDate, DateTimeFormatter.BASIC_ISO_DATE);
	}

	/**
	 * Check if value length matches the given length
	 * 
	 * @param value
	 * @param requiredLength
	 * @return
	 */
	public static boolean isValueLengthCorrect(String value, Integer requiredLength) {
		if (isNull(value)) {
			return false;
		}
		if (value.length() == requiredLength) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	public static boolean isNumber(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

}
