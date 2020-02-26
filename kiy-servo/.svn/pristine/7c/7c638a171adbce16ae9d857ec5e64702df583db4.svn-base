package com.kiy.servo.recognize;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.kiy.common.Tool;

public class StringToPingyin {
	
	/**
	 * 替换相似读音
	 * @param strPinyin
	 * @return
	 */
	private static String replaceSimilarityString(String strPinyin){
        //声母替换
        String strReplaced = null;
        if(strPinyin.contains("zh")){
            strReplaced = strPinyin.replace("zh", "z");                         
        } else if(strPinyin.contains("ch")){
            strReplaced = strPinyin.replace("ch", "c");                         
        } else if(strPinyin.contains("sh")){
            strReplaced = strPinyin.replace("sh", "s");
        } else if(strPinyin.startsWith("l")){
            strReplaced = strPinyin.replace("l", "n");                          
        }
        // 韵母替换
        if (Tool.isEmpty(strReplaced)) {
        	 if (strPinyin.contains("ang")) {
                 strReplaced = strPinyin.replace("ang", "an");
             } else if (strPinyin.contains("eng")) {
                 strReplaced = strPinyin.replace("eng", "en");
             } else if (strPinyin.contains("ing")) {
                 strReplaced = strPinyin.replace("ing", "in");
             }
		}else{
			/**
			 * 声母已经替换，还要替换韵母
			 */
			strPinyin = strReplaced;
        	 if (strPinyin.contains("ang")) {
                 strReplaced = strPinyin.replace("ang", "an");
             } else if (strPinyin.contains("eng")) {
                 strReplaced = strPinyin.replace("eng", "en");
             } else if (strPinyin.contains("ing")) {
                 strReplaced = strPinyin.replace("ing", "in");
             }
		}
        if (Tool.isEmpty(strReplaced)) {
 			return strPinyin;
 		}
         return strReplaced;
       
    }
	
	public static String String2PingyingBySimilarity(String string){
		StringBuilder sBuilder = new StringBuilder();
		char[] charArray = string.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
	    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
	    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
	    for (int i = 0; i < charArray.length; i++) {  
            if (charArray[i] > 128) {  
                try {
                  String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
                  if (hanyuPinyinStringArray == null) 
					continue;
                  String str = hanyuPinyinStringArray[0];
                  String s = replaceSimilarityString(str);
                  sBuilder.append(s);
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    continue;
                }  
            }else{ 
            	sBuilder.append(charArray[i]);
            }  
        } 
	    return sBuilder.toString();
	}
	
   
}
