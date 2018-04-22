package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**this class defines utility methods for string matching*/
public class StringMatcher {
	
    /**
     * match pattern in a given string ignoring case
     * @param content
     * @param regx
     * @param quoteRegx
     * @return
     */
    public static boolean matchIgnoreCase(String content, String regx, boolean quoteRegx) {
        return getIndex(content, regx, true, quoteRegx) >= 0;

    }//end matchIgnoreCase

    /**
     * match pattern in a given string with case
     * @param content
     * @param regx
     * @param quoteRegx
     * @return
     */
    public static boolean match(String content, String regx, boolean quoteRegx) {
        return getIndex(content, regx, false, quoteRegx) >= 0;

    }//end matchIgnoreCase

    /**
     *
     * @param content
     * @param regx
     * @param ignoreCase
     * @param quoteRegx
     * @return first matching index of a given pattern
     */
    public static int getIndex(String content, String regx, boolean ignoreCase, boolean quoteRegx) {

        if (quoteRegx) {
            regx = Pattern.quote(regx);
        }

        Pattern pattern = Pattern.compile(regx, Pattern.UNICODE_CASE);

        if (ignoreCase) {
            pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        }

        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            if (matcher.start() != matcher.end()) {
                return matcher.start();
            }

        }//end while

        return -1;

    }//end getIndexIgnoreCase
    
    public static void main(String[] args)
    {
    	String content = "Go to Online Course";
    	String regex = "^((Go to)|(Run))";
    	//regex = "^(Go to)";
    	
    	System.out.println("rs= "+match(content,regex,false));
    		
    }

}
