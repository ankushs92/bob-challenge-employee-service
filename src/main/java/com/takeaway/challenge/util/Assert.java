package com.takeaway.challenge.util;


/**
 * Contains some run of the mill static methods for pre-conditions validations.
 * @author Ankush Sharma
 */

public final class Assert {
	
	
	public static <T> void notNull(final T  t , final String errorMsg){
		if(t==null){
			throw new IllegalArgumentException(errorMsg);
		}
	}
	
	public static void notEmptyString(final String str , final String errorMsg){
		if(!Strings.hasText(str)){
			throw new IllegalArgumentException(errorMsg);
		}
	}
	
}
