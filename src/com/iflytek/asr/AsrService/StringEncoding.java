package com.iflytek.asr.AsrService;

/**
 * 字符串编码格式
 * @author v_watershao
 *
 */
public class StringEncoding {

	/**
	 * 字符串编码集
	 */
	private static final String[] ENCODINGS = {"GB2312", "GBK", "UTF-8", "ISO-8859-1", 
											"UTF-16", "GB18030", "Unicode", "ASCII", "Big5"};
	/**
	 * 将字符串改为目标编码格式
	 * @param str
	 * @param targetEncode
	 * @return
	 */
	 public static String TransferEncode(String str,String targetEncode) {  
	        try {  
	            String strEncode = StringEncoding.getEncoding(str);  
	            String temp = new String(str.getBytes(strEncode), targetEncode);  
	            return temp;  
	        } catch (java.io.IOException ex) {  
	  
	            return null;  
	        }  
	    } 
	
	/** 
     * 判断字符串的编码 
     * 
     * @param str 
     * @return GB2312，ISO-8859-1，UTF-8，GBK，UTF-16,Unicode, ASCII, Big5，“ ”
     */  
    public static String getEncoding(String str) {  
    	
    	String encode = ""; 
    	for (int i = 0; i < ENCODINGS.length; i++) {
    		try {  
                if ((str!=null) && (str != "") && str.equals(new String(str.getBytes(ENCODINGS[i]), ENCODINGS[i]))) {  
                    encode = ENCODINGS[i];  
                    return encode;  
                }  
            } catch (Exception exception) {  
            	return encode;
            }  
		}
    	return encode;
    }  
}
